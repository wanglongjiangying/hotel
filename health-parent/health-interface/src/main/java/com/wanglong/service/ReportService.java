package com.wanglong.service;

import java.util.List;
import java.util.Map;

public interface ReportService {
    List<Integer> findMemberCountsByDate(List<String> date);
    Map<String,Object> getBusinessReportData() throws Exception;
}
