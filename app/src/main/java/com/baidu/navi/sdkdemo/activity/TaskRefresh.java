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

public class TaskRefresh {

    private static String START_TASK = "https://api.ostep.com.cn/ostep/ship/task/start?task_id=%s";//shiptask2019102000035827
    private static String COMPLETE_TASK = "https://api.ostep.com.cn/ostep/ship/task/complete?task_id=%s";//shiptask2019102000035827
    private static String START_ORDER = "https://api.ostep.com.cn/ostep/ship/order/start?ship_order_no=%s";//ship132019082917384512
    private static String COMPLETE_ORDER = "https://api.ostep.com.cn/ostep/ship/order/complete?ship_order_no=%s";//ship132019082917454151


   public boolean startTask(Object[] objects)
   {
       return getData(objects,START_TASK);
   }

    public boolean completeTask(Object[] objects)
    {
        return getData(objects,COMPLETE_TASK);
    }
    public boolean startShipOrder(Object[] objects)
    {
        return getData(objects,START_ORDER);
    }
    public boolean completeShipOrder(Object[] objects)
    {
        return getData(objects,COMPLETE_ORDER);
    }

    public boolean getData(Object[] objects ,String MODE)
    {
        String ship_order_no=objects[0].toString();
        String fullGetMyTaskUrl = String.format(MODE,ship_order_no);
//                Log.e("fullGetMyTaskUrl",fullGetMyTaskUrl);
//                return null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(fullGetMyTaskUrl).build();
        try {
            Response response = client.newCall(request).execute();
            String resBody = response.body().string();

            WebResponseBody webResponseBody = JSONObject.parseObject(resBody, WebResponseBody.class);
            if (webResponseBody.getResult_code() == 200) {
                //Log.e("DeliveryList", String.valueOf((List<Map<String, Object>>) webResponseBody.getData()));
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            Log.d("MODE", " " + e);
            return false;
        }
    }
}
