package com.wanglong.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wanglong.constant.RedisConstant;
import com.wanglong.dao.SetmealDao;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.pojo.Setmeal;
import com.wanglong.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl  implements SetmealService{

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


    private String outPath="E:/mycode/health-parent/health-mobile/src/main/webapp/pages/";

    @Override
    public void add(Setmeal setmeal, Integer[] groupIds) throws Exception{
        //向套餐表插入数据
        try {
            setmealDao.add(setmeal);
            this.addSetmealAndGroup(setmeal.getId(),groupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());

            //添加套餐成功后应该重新生成静态页面
            generateMobileStaticHtml();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //生成静态页面有两个套餐列表和详情
    public void generateMobileStaticHtml(){

        //查询出所有的套餐列表
        List<Setmeal> setmeals = setmealDao.findAll();

        //生成套餐列表页面
        generateMobileSetmealListHtml(setmeals);
        //生成套餐详情页面
        generateMobileSetmealDetailHtml(setmeals);

    }
    //生成套餐列表页面 static_setmeal
    public void  generateMobileSetmealListHtml(List<Setmeal> setmeals){


        Map<String ,Object> map=new HashMap<>();
        map.put("setmealList",setmeals);
        generateHtml(map,"mobile_setmeal.ftl","static_setmeal.html");

    }
    //生成套餐详情页面(多个) setmeal_detail_${setmeal.id}.html
    public void  generateMobileSetmealDetailHtml(List<Setmeal> setmeals){
        //遍历套餐列表生成每一个套餐列表的套餐详情页面
        for (Setmeal setmeal : setmeals) {
            Setmeal setmeal1 = setmealDao.findbyId(setmeal.getId());
            Map<String ,Object> map=new HashMap<>();
            map.put("setmeal",setmeal1);
            generateHtml(map,"mobile_setmeal_detail.ftl","setmeal_detail_"+setmeal1.getId()+".html");
        }

    }

    //通用的生成静态页面的方法
    public void  generateHtml(Map map,String templateName,String htmlName) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer writer=null;
        try {
            //加载静态模板
            Template template = configuration.getTemplate(templateName);
            //生成数据
            File docFile=new File("E:\\mycode\\health-parent\\health-mobile\\src\\main\\webapp\\pages\\"+htmlName);
            writer=new FileWriter(docFile);
            template.process(map,writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 分页查询套餐
     * @param queryPageBean
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) throws Exception{
         PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
         Page<Setmeal> page= setmealDao.findByPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteByImg(String imgName,Integer id) throws Exception{
        //先删除中间表的数据
        this.deleteSetmealAndGroup(id);
        //根据图片名称删除数据库中的记录
        setmealDao.deleteByImg(imgName);
        //把这个图片名称存储到redis中的deleteResources的set集合中（待清理图片的文件名）
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DELETE_RESOURCES,imgName);

        //重新加载静态数据
        generateMobileStaticHtml();
    }

    /**
     * 通过套餐id查询套餐下面的所有检查组
     * @param setmealId
     * @return
     * @throws Exception
     */
    @Override
    public  List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId)throws Exception {
        return setmealDao.findCheckGroupIdsBySetmealId(setmealId);
    }

    /**
     * 修改套餐数据
     * @param checkgroupIds 选中的检查组
     * @param imgName  修改之前的图片名称
     * @param setmeal
     */
    @Override
    public void edit(Integer[] checkgroupIds, String imgName, Setmeal setmeal) throws Exception {
        if(!StringUtils.isBlank(imgName)){

            if(StringUtils.isEquals(imgName,setmeal.getImg())){
                //两个图片相同，没有改变
                //2.更新数据库中信息
                //先把中间表的数据删除。再插入
                this.deleteSetmealAndGroup(setmeal.getId());
                this.addSetmealAndGroup(setmeal.getId(),checkgroupIds);
                //更新套餐数据
                setmealDao.edit(setmeal);
            }else{
                //1.图片更新了
                //把旧图片存储到，redis的待删除区域
                jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DELETE_RESOURCES,imgName);
                jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
                //2.更新数据库中信息
                 //先把中间表的数据删除。再插入
                   this.deleteSetmealAndGroup(setmeal.getId());
                   this.addSetmealAndGroup(setmeal.getId(),checkgroupIds);
                   //更新套餐数据
                   setmealDao.edit(setmeal);
            }

            //重新加载静态数据
            generateMobileStaticHtml();
        }
    }

    /**
     * 查询所有套餐列表
     * @return
     * @throws Exception
     */
    @Override
    public List<Setmeal> findAll() throws Exception{
        return setmealDao.findAll();
    }

    /**
     * 根据套餐id查询套餐详情，包括检查组和检查项信息
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id)throws Exception {
        return setmealDao.findbyId(id);
    }

    /**
     * 查询各个套餐的人数以及套餐名称，用于生成报表
     * @return
     */
    @Override
    public List<Map<String, Object>> findSetmealCount()throws Exception {

        List<Map<String, Object>>  mapList=setmealDao.findSetmealCount();
        return mapList;
    }

    /**
     * 根据套餐id删除套餐和检查组的中间表数据
     * @param id
     */
    public void deleteSetmealAndGroup(Integer id)throws Exception{
      setmealDao.deleteSetmealAndGroup(id);
    }

    public void addSetmealAndGroup(Integer sid,Integer[] groupIds)throws Exception{
        if(groupIds!=null&&groupIds.length>0){

            for (Integer groupId : groupIds) {
                Map<String,Integer> map=new HashMap<>();
                map.put("setmealId",sid);
                map.put("groupId",groupId);
                setmealDao.addSetmealAndGroup(map);
            }
        }
    }
}
