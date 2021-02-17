package com.zz.crow.constant;

public class CrowdConstant {

    /**
     * 项目redis前缀
     */
    public static final String REDIS_CODE_PREFIX = "crowd_redis:";


    /**
     * 用户注册名已被占用
     */
    public static final String USER_ACCOUNT_ALREADY_REGISTER = "user name already be registered";


    /**
     * 用户注册失败
     */
    public static final String USER_REGISTER_FAILED = "register fail";

    public static final String MESSAGE_STRING_NULL = "传入字符串为空";

    public static final String REGISTER_CODE_EXPIRE = "验证码已过期";

    public static final String REGISTER_SUCCESS = "注册成功";

    public static final String REGISTER_CODE_NOT_MATCH = "验证码错误";
    public static final String LOGIN_FAILED = "登录失败,用户不存在";
    public static final String LOGIN_PASSWD_ERROR = "密码错误，请重新登录";
    public static final String USER_NOT_EXIST = "用户不存在";
    public static final String MEMBER_SESSION_NAME = "memberLoginSession";
    public static final String ACCESS_FORBIDDEN_NOT_LOGIN = "您没有权限访问，请登录后访问";
    public static final String PICTURE_UPLOAD_FAILED = "照片上传失败";
    public static final String SESSION_TEMP_PROJECTVO = "temp_projectVO";
    public static final String HEAD_PICTURE_EMPTY = "头图不能为空";
    public static final String RETURN_PICTURE_EMPTY = "回报图片为空";
    public static final String SESSION_TEMP_PROJECTVO_NOT_EXIST = "不存在该项目，请重试";
}
