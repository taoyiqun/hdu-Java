package config;

import java.util.ArrayList;

public class GCON {
    //数据库连接信息
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static final String URL = "jdbc:mysql://localhost:3306/new?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true";

    public static  String USERNAME = "root";

    public static  String PASSWORD = "qyf%iF?aP98r";

    public static final String SYSTEMUSERNAME = "root";

    public static final String SYSTEMPASSWORD = "qyf%iF?aP98r";

    public static final String HOTELUSERNAME = "root";

    public static final String HOTELPASSWORD = "qyf%iF?aP98r";

    public static int status = 1 ; //1是系统管理员  0 是宾馆管理员;
    //订单
    //查询所有订单信息
    public static final String SQL_ALL_ORDERS = "SELECT * FROM orders";
    //查询管理员
    public static final String SQL_ALL_ADMINS = "SELECT * FROM systemAdministrator";

    //查询所有房间
    public static final String SQL_ALL_ROOMS ="SELECT * FROM roomtypeandprice" ;

    public static final String SQL_ALL_WAITERS ="SELECT * FROM waiter" ;

    //查询所有续费订单
    public static final String SQL_ALL_TIME_EXTENSION_ORDERS = "SELECT * FROM timeextension";


    //----------------------jsp-----------------------//
    //客房管理
    public static final String SEARCH_ROOM = "1";

    public static final String ADD_ROOM = "2";

    public static final String EDIT_ROOM = "3";

    public static final String DELETE_ROOM = "4";

}
