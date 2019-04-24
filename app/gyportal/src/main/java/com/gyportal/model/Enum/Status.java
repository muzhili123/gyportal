package com.gyportal.model.Enum;

/**
 * create by lihuan at 18/11/2 18:02
 */
public interface Status {
    //正常
    String OK = "200";
    //程序异常
    String ERROR = "500";
    //操作失败
    String FAIL = "400";
    //权限错误
    String AUTH = "401";


//    OK("200"), FAIL("500"), ERROR("404"), AUTH("000");
//
//    private final String name;
//
//    Status(String name)
//    {
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }

}
