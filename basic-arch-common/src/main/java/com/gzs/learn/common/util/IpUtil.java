package com.gzs.learn.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class IpUtil {
    private static String serverIp;
    private static String serverName;

    static {
        try {
            serverIp = java.net.InetAddress.getLocalHost().getHostAddress().toString();
            serverName = java.net.InetAddress.getLocalHost().getHostName().toString();
        } catch (Throwable e) {
            serverIp = "";
            serverName = "";
        }
    }

    public static String getServerIp() {
        return serverIp;
    }

    public static String getServerName() {
        return serverName;
    }

    public static String getIpAdrress(HttpServletRequest request) {
        String xRealIp = request.getHeader("X-Real-IP");
        String xForwardIp = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(xForwardIp) && !"unKnown".equalsIgnoreCase(xForwardIp)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xForwardIp.indexOf(",");
            if (index != -1) {
                return xForwardIp.substring(0, index);
            } else {
                return xForwardIp;
            }
        }
        xForwardIp = xRealIp;
        if (StringUtils.isNotEmpty(xForwardIp) && !"unKnown".equalsIgnoreCase(xForwardIp)) {
            return xForwardIp;
        }
        if (StringUtils.isBlank(xForwardIp) || "unknown".equalsIgnoreCase(xForwardIp)) {
            xForwardIp = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xForwardIp) || "unknown".equalsIgnoreCase(xForwardIp)) {
            xForwardIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xForwardIp) || "unknown".equalsIgnoreCase(xForwardIp)) {
            xForwardIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(xForwardIp) || "unknown".equalsIgnoreCase(xForwardIp)) {
            xForwardIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(xForwardIp) || "unknown".equalsIgnoreCase(xForwardIp)) {
            xForwardIp = request.getRemoteAddr();
        }
        return xForwardIp;
    }

    /**
     * 获取本机ip地址
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getLocalIp() {
        try {
            InetAddress candidateAddress = null;
            for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (inetAddr.isLoopbackAddress()) {
                        continue;
                    }
                    if (inetAddr.isSiteLocalAddress()) {
                        return inetAddr.getHostAddress();
                    } else if (candidateAddress == null) {
                        candidateAddress = inetAddr;
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress.getHostAddress();
            }
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            return jdkSuppliedAddress.getHostAddress();
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            e.printStackTrace();
            return "";
        }
    }
}
