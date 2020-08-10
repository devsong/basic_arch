package com.ruoyi.common.utils.ip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtil;
import com.ruoyi.common.utils.http.HttpUtil.Response;

import lombok.Data;

/**
 * 获取地址类
 * 
 * @author guanzhisong
 */
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://ip-api.com/json/%s?lang=zh-CN";

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";

        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (!RuoYiConfig.isAddressEnabled()) {
            return address;
        }

        try {
            String url = String.format(IP_URL, ip);
            Response<IpResult> resp = HttpUtil.doGet(url, IpResult.class);
            if (resp.success && StringUtils.equalsIgnoreCase(IpResult.OK, resp.entity.getStatus())) {
                IpResult result = resp.entity;
                if (result != null) {
                    address = result.getRegionName() + ' ' + result.getCity();
                }
            }
        } catch (Exception e) {
            log.error("get ip {} addr error ", ip, e);
        }
        return address;
    }
}

@Data
class IpResult {
    public static String OK = "success";
    private String status;
    private String country;
    private String countryCode;
    private String region;
    private String regionName;
    private String city;
    private String zip;
    private String lat;
    private String lon;
    private String timezone;
    private String isp;
    private String org;
    private String as;
    private String query;
}
