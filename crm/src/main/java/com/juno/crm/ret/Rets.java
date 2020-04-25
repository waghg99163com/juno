package com.juno.crm.ret;

/**
 * 返回结果统一处理
 */
public class Rets {

    public static final Integer SUCCESS = 200;
    public static final Integer FAILURE = 500;
    public static  final Integer TOKEN_EXPIRE=50014;

    public static Ret success(Object data) {
        return new Ret(Rets.SUCCESS, "成功", data);
    }

    public static Ret failure(String msg) {
        return new Ret(Rets.FAILURE, msg, null);
    }

    public static Ret success() {
        return new Ret(Rets.SUCCESS, "成功", null);
    }
    public static Ret expire(){
        return new Ret(Rets.TOKEN_EXPIRE,"token 过期",null);
    }
}
