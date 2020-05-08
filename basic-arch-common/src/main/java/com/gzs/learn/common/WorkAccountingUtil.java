package com.gzs.learn.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

import java.util.List;

public class WorkAccountingUtil {
    public static void calAverageWorkAccounting(String json) {
        WorkAccountResponse response = JSON.parseObject(json, WorkAccountResponse.class);
        int effectiveDays = 0;
        int totalMinute = 0;
        for (ReturnValueBo returnValueBo : response.getList()) {
            if (returnValueBo.getAccountingWork() != null && !returnValueBo.getAccountingWork().equals("")
                    && returnValueBo.getLeaveTheData().indexOf("假") == -1) {
                effectiveDays++;
                String timeStr = returnValueBo.getAccountingWork().split("：")[1];
                String hourStr = timeStr.split("小时")[0];
                totalMinute += Integer.valueOf(hourStr) * 60;
                if (timeStr.split("小时").length > 1) {
                    String minuteStr = timeStr.split("小时")[1].split("分钟")[0];
                    totalMinute += Integer.valueOf(minuteStr);
                }

            }
        }

        int averageMinute = totalMinute / effectiveDays;
        int hours = (int) Math.floor(averageMinute / 60);
        int minute = averageMinute % 60;
        System.out.println("总分钟数：" + totalMinute);
        System.out.println("总天数：" + effectiveDays);
        System.out.println("平均分钟数：" + averageMinute);
        if (minute < 36) {
            System.out.println("未达标");
        }
        System.out.println("平均工时：" + hours + "小时" + minute + "分钟");

    }

    public static void main(String[] args) {
        String json_01 = "{\"Success\":true,\"Message\":\"\\u003cspan\\u003e【2020-01-20至2020-01-22，已出勤工时 \\u003cspan style = \\u0027color:#ff7800;font-weight:bold;\\u0027\\u003e0\\u003c/span\\u003e 小时，距离满勤还有 \\u003cspan style = \\u0027color:#ff7800;font-weight:bold;\\u0027\\u003e27\\u003c/span\\u003e 小时】\\u003c/span\\u003e\",\"Code\":3,\"Total\":0,\"ReturnValue\":[{\"Day\":\"\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":null,\"Today\":false},{\"Day\":\"\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":null,\"Today\":false},{\"Day\":\"\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":null,\"Today\":false},{\"Day\":\"1日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"2日\",\"CreditCardData\":\"08:37-08:55-11:54-18:42-19:28-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时52分钟\",\"Today\":false},{\"Day\":\"3日\",\"CreditCardData\":\"08:48-09:07-11:58-18:17-19:26-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时42分钟\",\"Today\":false},{\"Day\":\"4日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"5日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"6日\",\"CreditCardData\":\"08:39-08:53-12:04-18:16-18:58-18:59-19:01\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时22分钟\",\"Today\":false},{\"Day\":\"7日\",\"CreditCardData\":\"08:37-08:55-12:00-18:49-19:27-19:29-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时52分钟\",\"Today\":false},{\"Day\":\"8日\",\"CreditCardData\":\"08:44-09:02-11:57-18:19-19:26-19:31\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时46分钟\",\"Today\":false},{\"Day\":\"9日\",\"CreditCardData\":\"08:36-08:36-08:55-11:56-18:25-19:28-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时54分钟\",\"Today\":false},{\"Day\":\"10日\",\"CreditCardData\":\"08:58-09:16-18:41-19:27-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时31分钟\",\"Today\":false},{\"Day\":\"11日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"12日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"13日\",\"CreditCardData\":\"08:38-08:55-18:21-19:26-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时52分钟\",\"Today\":false},{\"Day\":\"14日\",\"CreditCardData\":\"08:36-08:55-14:12-18:53-19:00\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时24分钟\",\"Today\":false},{\"Day\":\"15日\",\"CreditCardData\":\"08:37-08:54-11:49-18:17-19:29-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时52分钟\",\"Today\":false},{\"Day\":\"16日\",\"CreditCardData\":\"08:40-08:49-08:58-11:56-18:35-19:03-19:58-20:00\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：11小时19分钟\",\"Today\":false},{\"Day\":\"17日\",\"CreditCardData\":\"08:37-08:53-11:57-18:10-18:54-18:57\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时19分钟\",\"Today\":false},{\"Day\":\"18日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"19日\",\"CreditCardData\":\"08:27-08:44-18:13-18:55-19:01\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时34分钟\",\"Today\":false},{\"Day\":\"20日\",\"CreditCardData\":\"08:37-08:38-08:49-11:54\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":true},{\"Day\":\"21日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"22日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"年假：09:00-18:00\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"23日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"24日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"25日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"26日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"27日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"28日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"29日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"30日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"31日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"年假：09:00-18:00\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false}]}";
        WorkAccountingUtil.calAverageWorkAccounting(json_01);
        System.out.println();
    }

}

@Data
class WorkAccountResponse {

    @JSONField(name = "ReturnValue")
    List<ReturnValueBo> list;
}

@Data
class ReturnValueBo {

    @JSONField(name = "AccountingWork")
    private String accountingWork;

    @JSONField(name = "Day")
    private String day;

    @JSONField(name = "LeaveTheData")
    private String leaveTheData;
}
