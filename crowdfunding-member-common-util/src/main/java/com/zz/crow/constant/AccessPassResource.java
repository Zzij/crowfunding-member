package com.zz.crow.constant;

import java.util.HashSet;
import java.util.Set;

public class AccessPassResource {

    /**
     * 不需要验证的地址
     */
    public final static Set<String> PASS_RESOURCE = new HashSet<>();


    /**
     * 静态资源地址
     */
    public final static Set<String> STATIC_PASS_RESOURCE = new HashSet<>();


    static {
        PASS_RESOURCE.add("/");
        PASS_RESOURCE.add("/auth/member/register/send/message");
        PASS_RESOURCE.add("/auth/member/register");
        PASS_RESOURCE.add("/auth/member/login/page");
        PASS_RESOURCE.add("/auth/member/login");
        PASS_RESOURCE.add("/auth/member/register/page");

        STATIC_PASS_RESOURCE.add("bootstrap");
        STATIC_PASS_RESOURCE.add("css");
        STATIC_PASS_RESOURCE.add("fonts");
        STATIC_PASS_RESOURCE.add("img");
        STATIC_PASS_RESOURCE.add("jquery");
        STATIC_PASS_RESOURCE.add("layer");
        STATIC_PASS_RESOURCE.add("script");
        STATIC_PASS_RESOURCE.add("ztree");
    }

    /**
     * 判断servlet路径 是否是静态资源
     * 形如 /aa/bb/cc
     * @param servletPath  请求路径
     * @return
     */
    public static boolean judgeServletPathIsStaticResource(String servletPath){

        if(servletPath == null || "".equals(servletPath)){
            return false;
        }
        String[] split = servletPath.split("/");

        //   /aa/bb/cc    ["", "aa", "bb", "cc"]
        return STATIC_PASS_RESOURCE.contains(split[1]);

    }

}
