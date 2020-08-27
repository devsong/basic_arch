package com.ruoyi.common.utils.ip;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtil;
import com.ruoyi.common.utils.http.HttpUtil.Response;

import lombok.Builder;
import lombok.Data;

/**
 * 地址转换类，使用高德开放API平台提供接口
 * 
 * @author guanzhisong
 */
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "https://restapi.amap.com/v3/ip";

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (!RuoYiConfig.isAddressEnabled()) {
            return address;
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> paramMap = BeanMap.create(IpRequest.builder().key(RuoYiConfig.getAmapKey()).ip(ip).build());
        String url = HttpUtil.buildUrl(IP_URL, HttpUtil.convertMap2Pair(paramMap));
        try {
            Response<IpResult> resp = HttpUtil.doGet(url, IpResult.class);
            if (resp.success && resp.entity != null && resp.entity.isSuccess()) {
                IpResult result = resp.entity;
                if (result != null) {
                    address = result.getProvince() + "," + result.getCity();
                }
            }
        } catch (Exception e) {
            log.error("get ip {} addr error ", ip, e);
        }
        return address;
    }
}

@Data
@Builder()
class IpRequest {
    private String key;
    private String ip;
    @Builder.Default()
    private String output = "JSON";
}

@Data
class IpResult {
    private String INFOCODE_SUCCESS = "10000";
    private String status;
    private String info;
    private String infocode;
    private String province;
    private String city;
    private String adcode;
    private String rectangle;

    public boolean isSuccess() {
        return StringUtils.equalsIgnoreCase(getInfocode(), INFOCODE_SUCCESS) && StringUtils.equalsIgnoreCase(status, "1");
    }
}
