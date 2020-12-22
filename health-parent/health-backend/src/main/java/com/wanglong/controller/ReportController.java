package com.wanglong.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wanglong.constant.MessageConstant;
import com.wanglong.entity.Result;
import com.wanglong.service.ReportService;
import com.wanglong.service.SetmealService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController  {

    @Reference
    private ReportService reportService;

    @Reference
    private SetmealService setmealService;

    /**
     * 统计一年之内的会员
     * @return
     */
    @GetMapping("/getMemberReport")
    public Map<String,Object>  getMemberReport(){

        Map<String,Object> map=new HashMap<>();

        //实例化为一个Calender对象
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH,-27);
        List<String> date=new ArrayList<>();
        for(int i=1;i<13;i++){
            calendar.add(Calendar.MONTH,1);
            date.add(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()));
        }

       List<Integer> counts= reportService.findMemberCountsByDate(date);






        map.put("months",date);
        map.put("memberCount",counts);
        return map;
    }


    @GetMapping("/getSetmealReport")
    public Result getSetmealReport(){

        try {
            Map<String,Object> map=new HashMap<>();
            List<Map<String,Object>> setmealCount=  setmealService.findSetmealCount();

            List<String> setmealNames=new ArrayList<>();

            for (Map<String, Object> stringObjectMap : setmealCount) {
                String name = (String) stringObjectMap.get("name");
                setmealNames.add(name);
            }


            map.put("setmealNames",setmealNames);
            map.put("setmealCount",setmealCount);
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL,null);
        }
    }


    /**
     * 获取运营报表数据
     * @return
     */
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData(){

        try {
            Map<String,Object> map=reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL,null);
        }
    }

    /**
     * 导出Excel报表数据
     * @return
     */
    @GetMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
        try {
            Map<String,Object> result=reportService.getBusinessReportData();
            //取出返回结果数据，准备将报表数据写入到Excel文件中    
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer)result.get("thisMonthVisitsNumber");

            String realPath = request.getSession().getServletContext().getRealPath("template" + File.separator + "report_template.xlsx");
            XSSFWorkbook xssfWorkbook=new XSSFWorkbook(new FileInputStream(new File(realPath)));
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);
            sheet.getRow(4).getCell(5).setCellValue(todayNewMember);
            sheet.getRow(4).getCell(7).setCellValue(totalMember);
            sheet.getRow(5).getCell(5).setCellValue(thisWeekNewMember);
            sheet.getRow(5).getCell(7).setCellValue(thisMonthNewMember);

            sheet.getRow(7).getCell(5).setCellValue(todayOrderNumber);
            sheet.getRow(7).getCell(7).setCellValue(todayVisitsNumber);

            sheet.getRow(8).getCell(5).setCellValue(thisWeekOrderNumber);
            sheet.getRow(8).getCell(7).setCellValue(thisWeekVisitsNumber);

            sheet.getRow(9).getCell(5).setCellValue(thisMonthOrderNumber);
            sheet.getRow(9).getCell(7).setCellValue(thisMonthVisitsNumber);

            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");
            int row1=12;
            for (Map map : hotSetmeal) {
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                sheet.getRow(row1).getCell(4).setCellValue(name);
                sheet.getRow(row1).getCell(5).setCellValue(setmeal_count);
                sheet.getRow(row1).getCell(6).setCellValue(proportion.doubleValue());
                row1++;
            }

            //用输出流将文件输出
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");


            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            xssfWorkbook.close();

            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }


    }



    /**
     * 导出PDF报表数据
     * @return
     */
    @GetMapping("/exportBusinessReportPDF")
    public Result exportBusinessReportPDF(HttpServletRequest request,HttpServletResponse response) throws Exception {


        try {
            Map<String, Object> result = reportService.getBusinessReportData();
            //热销套餐，需要在模板中遍历出来    
            List<Map<String,Object>> hotSetmeal = (List<Map<String, Object>>) result.get("hotSetmeal");
            //动态获取文件的位置
            String jrxmlPath=request.getSession().getServletContext()
                                .getRealPath("template"+File.separator+"health_business3.jrxml");
            String jasperPath=request.getSession().getServletContext()
                                .getRealPath("template"+File.separator+"health_business3.jasper");

            //编译模板
            JasperCompileManager.compileReportToFile(jrxmlPath,jasperPath);
            //填充数据，使用javabean的方式
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, result,
                    new JRBeanCollectionDataSource(hotSetmeal));

            //下载文件的两个头一个流
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setHeader("content-Disposition",
                                "attachment;filename=report.pdf");

            JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
            outputStream.flush();
            outputStream.close();
            return new Result(false,"导出PDF文件成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"导出PDF文件失败！请联系工作人员。");
        }

    }



}
