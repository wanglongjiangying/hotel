package com.wanglong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wanglong.dao.MemberDao;
import com.wanglong.dao.OrderDao;
import com.wanglong.dao.ReportDao;
import com.wanglong.dao.SetmealDao;
import com.wanglong.service.ReportService;
import com.wanglong.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private  OrderDao orderDao;

    @Autowired
    private SetmealDao setmealDao;
    @Override
    public List<Integer> findMemberCountsByDate(List<String> dates) {

        List<Integer> counts=new ArrayList<>();
        for (String date : dates) {
            date=date+".31";
          Integer count=  reportDao.findMemberCountsByDate(date);
          counts.add(count);
        }

        return counts;
    }

    /**
     * 获取运营详情报表数据
     * @return
     */
    @Override
    public Map<String, Object> getBusinessReportData() throws Exception{
       /* data:{
            reportData:{
                reportDate:null,
                        todayNewMember :0,
                        totalMember :0,
                        thisWeekNewMember :0,
                        thisMonthNewMember :0,
                        todayOrderNumber :0,
                        todayVisitsNumber :0,
                        thisWeekOrderNumber :0,
                        thisWeekVisitsNumber :0,
                        thisMonthOrderNumber :0,
                        thisMonthVisitsNumber :0,
                        hotSetmeal :[
                          {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
                        {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222}
                    ]
            }
        },*/
        String today = DateUtils.parseDate2String(new Date());
        String zhouyi = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        String yueFirstDay = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        //获取今日新增会员
        Integer  todayNewMember= memberDao.todayNewMember(today);
        //获取总会员数量
        Integer  totalMember= memberDao.totalMember(today);
        //获取本周新增会员(从周一到今天) 数据库SQL语句用>=来查询
        Integer thisWeekNewMember= memberDao.thisWeekNewMember(zhouyi);
        //获取本月新增会员
        Integer thisMonthNewMember= memberDao.thisMonthNewMember(yueFirstDay);
        //获取今日预约数量
        Integer todayOrderNumber= orderDao.todayOrderNumber(today);
        //获取今日到诊数量
        Integer todayVisitsNumber= orderDao.todayVisitsNumber(today);
        //获取本周预约数量
        Integer thisWeekOrderNumber= orderDao.thisWeekOrderNumber(zhouyi);
        //获取本周到诊数量
        Integer thisWeekVisitsNumber= orderDao.thisWeekVisitsNumber(zhouyi);
        //获取本月预约数量
        Integer thisMonthOrderNumber= orderDao.thisMonthOrderNumber(yueFirstDay);
        //获取本月到诊数量
        Integer thisMonthVisitsNumber= orderDao.thisMonthVisitsNumber(yueFirstDay);
        //获取热销套餐（取前面三个）
        List<Map<String,Object>> hotSetmeal=setmealDao.hotSetmeal();
        Map<String,Object> result = new HashMap<>();
        result.put("reportDate",today);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);
        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);
        result.put("todayOrderNumber",todayOrderNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        result.put("hotSetmeal",hotSetmeal);
        return result;
    }


}
