package com.cjy.sso.util;

public class ResultVoUtil {

    /**
     * 私有化构造器
     */
    private ResultVoUtil(){

    }

    private int code;

    private String msg;

    private Object obj;



    public ResultVoUtil(int code, String msg, Object obj) {
        super();
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public ResultVoUtil(Object obj) {
        super();
        this.obj = obj;
    }

    public static ResultVoUtil buildIsOk(Object obj){
        return new ResultVoUtil(200,"ok",obj);
    }

    public static ResultVoUtil buildIsFail(Object obj){
        return new ResultVoUtil(-1,"fail",obj);
    }

    public static ResultVoUtil build(int code, String msg, Object obj){
        return new ResultVoUtil(code,msg,obj);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
