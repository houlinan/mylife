package cn.houlinan.mylife.constant;

public enum UserTypeEnum {

    USER_TYPE_DEFAULT (0 , "普通用户")  ,
    USER_TYPE_TEAMADMIN (1 ,"小组管理员"),
    USER_TYPE_ADMIN (9  ,"超级管理员");

    private final int value ;
    private final String name ;

    UserTypeEnum(int value , String name ){
        this.value = value ;
        this.name = name ;
    }
    public String getName() {return name ;}
    public int getValue(){ return value ;}
}
