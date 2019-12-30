package com.gzs.learn.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

import java.util.List;

/**
 * 工时计算器
 */
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
        String json_12 ="{\"Success\":true,\"Message\":\"\\u003cspan\\u003e【2019-12-30至2020-01-03，已出勤工时 \\u003cspan style = \\u0027color:#ff7800;font-weight:bold;\\u0027\\u003e0\\u003c/span\\u003e 小时，距离满勤还有 \\u003cspan style = \\u0027color:#ff7800;font-weight:bold;\\u0027\\u003e36\\u003c/span\\u003e 小时】\\u003c/span\\u003e\",\"Code\":3,\"Total\":0,\"ReturnValue\":[{\"Day\":\"1日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"2日\",\"CreditCardData\":\"08:36-08:54-11:52-18:25-19:26-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时54分钟\",\"Today\":false},{\"Day\":\"3日\",\"CreditCardData\":\"08:58-08:58-09:17-09:31-11:53-18:47\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：9小时49分钟\",\"Today\":false},{\"Day\":\"4日\",\"CreditCardData\":\"08:37-08:56-12:00-18:50-19:54-20:01\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：11小时24分钟\",\"Today\":false},{\"Day\":\"5日\",\"CreditCardData\":\"08:57-09:13-12:07-18:37-19:27-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时33分钟\",\"Today\":false},{\"Day\":\"6日\",\"CreditCardData\":\"08:36-08:54-12:07-18:19-18:54-18:59\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时22分钟\",\"Today\":false},{\"Day\":\"7日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"8日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"9日\",\"CreditCardData\":\"08:36-08:53-11:54-18:27-19:27-19:31-19:37\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：11小时1分钟\",\"Today\":false},{\"Day\":\"10日\",\"CreditCardData\":\"08:37-08:53-11:56-19:02-19:27-19:28\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时51分钟\",\"Today\":false},{\"Day\":\"11日\",\"CreditCardData\":\"08:37-08:54-11:56-19:01-19:54-19:55-20:00\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：11小时22分钟\",\"Today\":false},{\"Day\":\"12日\",\"CreditCardData\":\"08:37-08:56-11:53-12:02-19:02-19:26-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时52分钟\",\"Today\":false},{\"Day\":\"13日\",\"CreditCardData\":\"08:37-08:56-10:56-18:59-19:26-19:31\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时54分钟\",\"Today\":false},{\"Day\":\"14日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"15日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"16日\",\"CreditCardData\":\"08:38-08:56-11:51-18:44-19:55-19:59\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：11小时21分钟\",\"Today\":false},{\"Day\":\"17日\",\"CreditCardData\":\"08:38-08:51-12:04-18:34-19:54-20:00\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：11小时22分钟\",\"Today\":false},{\"Day\":\"18日\",\"CreditCardData\":\"08:37-08:54-11:55-19:03-19:27-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时52分钟\",\"Today\":false},{\"Day\":\"19日\",\"CreditCardData\":\"08:37-08:55-12:04-16:59\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：8小时22分钟\",\"Today\":false},{\"Day\":\"20日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"年假：09:00-18:00\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：9小时\",\"Today\":false},{\"Day\":\"21日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"22日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"23日\",\"CreditCardData\":\"08:57-09:11-11:55-18:27-19:26-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时33分钟\",\"Today\":false},{\"Day\":\"24日\",\"CreditCardData\":\"08:39-09:01-09:29-11:56-18:26-19:28-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时51分钟\",\"Today\":false},{\"Day\":\"25日\",\"CreditCardData\":\"08:36-08:52-12:05-12:15-18:34-19:27-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时54分钟\",\"Today\":false},{\"Day\":\"26日\",\"CreditCardData\":\"08:36-08:56-11:57-17:25\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：8小时49分钟\",\"Today\":false},{\"Day\":\"27日\",\"CreditCardData\":\"08:48-09:02-12:00-12:03-18:52-19:28-19:31\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时43分钟\",\"Today\":false},{\"Day\":\"28日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"29日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"30日\",\"CreditCardData\":\"08:37-08:53-11:11\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":true},{\"Day\":\"31日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false}]}";
        String json_11 = "{\"Success\":true,\"Message\":\"\\u003cspan\\u003e【2019-12-02至2019-12-06，已出勤工时 \\u003cspan style = \\u0027color:#ff7800;font-weight:bold;\\u0027\\u003e0\\u003c/span\\u003e 小时，距离满勤还有 \\u003cspan style = \\u0027color:#ff7800;font-weight:bold;\\u0027\\u003e45\\u003c/span\\u003e 小时 】\\u003c/span\\u003e\",\"Code\":3,\"Total\":0,\"ReturnValue\":[{\"Day\":\"\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":null,\"Today\":false},{\"Day\":\"\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":null,\"Today\":false},{\"Day\":\"\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":null,\"Today\":false},{\"Day\":\"\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":null,\"Today\":false},{\"Day\":\"\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":null,\"Today\":false},{\"Day\":\"1日\",\"CreditCardData\":\"08:37-08:56-11:59-16:59\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：8小时22分钟\",\"Today\":false},{\"Day\":\"2日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"3日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"4日\",\"CreditCardData\":\"08:38-08:55-11:55-18:04-18:07\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：9小时28分钟\",\"Today\":false},{\"Day\":\"5日\",\"CreditCardData\":\"08:37-08:56-11:55-12:03-18:46-19:28-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时52分钟\",\"Today\":false},{\"Day\":\"6日\",\"CreditCardData\":\"08:57-09:13-19:16-19:57-20:00\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：11小时3分钟\",\"Today\":false},{\"Day\":\"7日\",\"CreditCardData\":\"08:37-08:56-11:59-18:55-19:29-19:31\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时54分钟\",\"Today\":false},{\"Day\":\"8日\",\"CreditCardData\":\"08:36-08:54-12:04-18:27-18:50-18:57-19:01\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时25分钟\",\"Today\":false},{\"Day\":\"9日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"10日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"11日\",\"CreditCardData\":\"08:58-09:18-11:58-11:59-18:47-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时31分钟\",\"Today\":false},{\"Day\":\"12日\",\"CreditCardData\":\"08:38-08:57-11:58-18:46-19:27-19:31\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时52分钟\",\"Today\":false},{\"Day\":\"13日\",\"CreditCardData\":\"08:37-08:49-11:59-18:54-19:58-20:00\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：11小时22分钟\",\"Today\":false},{\"Day\":\"14日\",\"CreditCardData\":\"08:38-08:55-11:52-11:58-18:56-19:29-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时52分钟\",\"Today\":false},{\"Day\":\"15日\",\"CreditCardData\":\"08:37-08:57-11:58-18:35-18:57\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时19分钟\",\"Today\":false},{\"Day\":\"16日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"17日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"18日\",\"CreditCardData\":\"08:38-08:59-18:55-19:57-20:01\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：11小时22分钟\",\"Today\":false},{\"Day\":\"19日\",\"CreditCardData\":\"08:36-08:54-11:57-18:46-18:59-18:59-19:00\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时24分钟\",\"Today\":false},{\"Day\":\"20日\",\"CreditCardData\":\"08:37-08:54-12:00-19:01-19:59-20:00\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：11小时22分钟\",\"Today\":false},{\"Day\":\"21日\",\"CreditCardData\":\"08:59-09:16-12:01-18:51-18:55-19:00\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时1分钟\",\"Today\":false},{\"Day\":\"22日\",\"CreditCardData\":\"08:47-09:05-11:55-18:53-19:00\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时13分钟\",\"Today\":false},{\"Day\":\"23日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"24日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false},{\"Day\":\"25日\",\"CreditCardData\":\"08:40-08:52-11:55-19:57-20:00\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：11小时19分钟\",\"Today\":false},{\"Day\":\"26日\",\"CreditCardData\":\"08:59-09:14-18:18-19:27-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时31分钟\",\"Today\":false},{\"Day\":\"27日\",\"CreditCardData\":\"08:37-08:38-08:57-12:00-18:55-19:26-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时52分钟\",\"Today\":false},{\"Day\":\"28日\",\"CreditCardData\":\"08:37-08:55-13:40-18:31-19:26-19:30\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时52分钟\",\"Today\":false},{\"Day\":\"29日\",\"CreditCardData\":\"08:36-08:54-12:01-18:40-18:59-18:59-19:01\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"出勤工时：10小时25分钟\",\"Today\":false},{\"Day\":\"30日\",\"CreditCardData\":\"\",\"OvertimeData\":\"\",\"AttendanceData\":\"\",\"LeaveTheData\":\"\",\"TravelTheData\":\"\",\"AttendancelType\":\"\",\"WorkingSystemText\":null,\"AccountingWork\":\"\",\"Today\":false}]}";
        WorkAccountingUtil.calAverageWorkAccounting(json_12);
        System.out.println();
        WorkAccountingUtil.calAverageWorkAccounting(json_11);
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
