package com.step.operation.common;

public class UrlConstants {

    public static String CHECK_PWD_URL="https://api.ostep.com.cn/auth/op/check/pwd?phone=";

    public static String SEND_CODE_URL="https://api.ostep.com.cn/auth/send/code?type=1&receiver=%s";

    public static String verify_CODE_URL="https://api.ostep.com.cn/auth/verify/code?type=1&receiver=%s&code=%s";

    public static String set_pwd_URL="https://api.ostep.com.cn/auth/op/set/pwd";

    public static String OP_USER_SIGNIN ="https://api.ostep.com.cn/auth/op/signin?phone=%s&password=%s";

    //public static String MY_DELIVERY_TASKS="https://api.ostep.com.cn/ostep/ship/my/tasks?user_id=%s";

    public static String MY_DELIVERY_TASKS="https://api.ostep.com.cn/ostep/ship/my/tasks?user_id=%s&d_code=%s";

    public static String DELIVERY_TASKS_INFO="https://api.ostep.com.cn/ostep/ship/my/task/order?task_id=%s";

    //11.24 add
    public static String DELIVERY_SHIP_START = "https://api.ostep.com.cn/ostep/ship/order/start?ship_order_no=%s";

    public static String DELIVERY_SHIP_COMPLETE = "https://api.ostep.com.cn/ostep/ship/order/complete?ship_order_no=%s";

    public static String DELIVERY_TASK_START="https://api.ostep.com.cn/ostep/ship/task/start?task_id=%s";

    public static String DELIVERY_TASk_COMPLETE="https://api.ostep.com.cn/ostep/ship/task/complete?task_id=%s";
}
