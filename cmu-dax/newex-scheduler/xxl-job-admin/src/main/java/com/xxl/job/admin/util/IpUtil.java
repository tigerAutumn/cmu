package com.xxl.job.admin.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author minhan
 * @Date: 2018/7/19 14:21
 * @Description:
 */
public class IpUtil {
    public static String getIP(){
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return null;
        }
        //获得本机IP
        return addr.getHostAddress().toString();
    }
}
