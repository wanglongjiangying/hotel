package com.wanglong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wanglong.dao.CheckGroupDao;
import com.wanglong.entity.PageResult;
import com.wanglong.entity.QueryPageBean;
import com.wanglong.pojo.CheckGroup;
import com.wanglong.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] itemIds)throws Exception {
        //插入检查组数据,并且要回显id
         checkGroupDao.addCheckGroup(checkGroup);
         //向中间表插入数据
        this.addZhongJianBiao(checkGroup.getId(),itemIds);

    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean)throws Exception {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
      Page<CheckGroup> page= checkGroupDao.findByPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer groupId)throws Exception {
        List<Integer> ids= checkGroupDao.findCheckItemIdsByCheckGroupId(groupId);
        return ids;
    }

    /**
     * 删除检查项数据
     * @param id
     * @throws Exception
     */
    @Override
    public void deleteById(Integer id)throws Exception {
        //先删除中间表数据
        deleteZhongJianBiao(id);
        //再删除检查项数据
        checkGroupDao.deleteById(id);

    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] itemIds) throws Exception {
        //先删除中间表数据再插入中间表数据
        this.deleteZhongJianBiao(checkGroup.getId());
        this.addZhongJianBiao(checkGroup.getId(),itemIds);

        //更新检查项表
        checkGroupDao.edit(checkGroup);

    }

    @Override
    public List<CheckGroup> findAll()throws Exception {

        return checkGroupDao.findAll();
    }

    /**
     * 根据检查组id删除中间表数据
     * @param groupId
     */
    public void deleteZhongJianBiao(Integer groupId)throws Exception{
        checkGroupDao.deleteZhongJianBiao(groupId);
    }

    /**
     * 添加中间表数据
     * @param groupId
     * @param itemIds
     */
    public void addZhongJianBiao(Integer groupId, Integer[] itemIds) throws Exception {

        if(itemIds!=null&&itemIds.length>0){
            for (Integer itemId : itemIds) {
                Map<String,Integer> map=new HashMap<>();
                map.put("checkGroupId",groupId);
                map.put("checkItemId",itemId);
                checkGroupDao.addZhongJianBiao(map);
            }
        }




    }
}
