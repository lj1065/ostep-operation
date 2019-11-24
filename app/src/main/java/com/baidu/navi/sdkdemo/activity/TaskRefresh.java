package com.baidu.navi.sdkdemo.activity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.step.operation.common.UrlConstants;
import com.step.operation.common.WebResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.step.operation.common.UrlConstants;

public class TaskRefresh {

//    private static String START_TASK = "https://api.ostep.com.cn/ostep/ship/task/start?task_id=%s";//shiptask2019102000035827
//    private static String COMPLETE_TASK = "https://api.ostep.com.cn/ostep/ship/task/complete?task_id=%s";//shiptask2019102000035827
//    private static String START_ORDER = "https://api.ostep.com.cn/ostep/ship/order/start?ship_order_no=%s";//ship132019082917384512
//    private static String COMPLETE_ORDER = "https://api.ostep.com.cn/ostep/ship/order/complete?ship_order_no=%s";//ship132019082917454151


   public static boolean startTask(String objects)
   {
       return getData(objects,UrlConstants.DELIVERY_TASk_COMPLETE);
   }

    public static boolean completeTask(String objects)
    {
        return getData(objects,UrlConstants.DELIVERY_TASK_START);
    }
    public static boolean startShipOrder(String objects)
    {
        return getData(objects,UrlConstants.DELIVERY_SHIP_START);
    }
    public static boolean completeShipOrder(String objects)
    {
        return getData(objects,UrlConstants.DELIVERY_SHIP_COMPLETE);
    }

    public static boolean getData(String objects, String MODE)
    {
//        String ship_order_no=objects[0].toString();
        String ship_order_no = objects;
        String fullGetMyTaskUrl = String.format(MODE,ship_order_no);
                Log.e("fullGetMyTaskUrl",fullGetMyTaskUrl);
//                return null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(fullGetMyTaskUrl).build();
        try {
            Response response = client.newCall(request).execute();
            String resBody = response.body().string();

            WebResponseBody webResponseBody = JSONObject.parseObject(resBody, WebResponseBody.class);
            if (webResponseBody.getResult_code() == 200) {
                Log.e("getDatastatus", "true");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.d("MODEMODE", " " + e);
            return false;
        }
    }
}
