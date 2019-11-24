package com.ostep.operation.ui.delivery;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.step.operation.common.UrlConstants;
import com.step.operation.common.WebResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskFinish {


    public boolean SentStatus(String order_no,String order_status){

        String fullGetMyTaskUrl = "";
        if(order_status.equals("DELIVERY_SHIP_START")){
            fullGetMyTaskUrl = String.format(UrlConstants.DELIVERY_SHIP_START, order_no);
        }else if (order_status.equals("DELIVERY_SHIP_COMPLETE")){
            fullGetMyTaskUrl = String.format(UrlConstants.DELIVERY_SHIP_COMPLETE, order_no);
        }else if(order_status.equals("DELIVERY_TASK_START")){
            fullGetMyTaskUrl = String.format(UrlConstants.DELIVERY_TASK_START, order_no);
        }else if (order_status.equals("DELIVERY_TASk_COMPLETE")){
            fullGetMyTaskUrl = String.format(UrlConstants.DELIVERY_TASk_COMPLETE, order_no);
        }

        if(fullGetMyTaskUrl.isEmpty()){

        }else {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(fullGetMyTaskUrl).build();
            try
            {
                Response response = client.newCall(request).execute();
                String resBody = response.body().string();
                Log.d("DeliveryListFragment", " task list: " + resBody);
                WebResponseBody webResponseBody = JSONObject.parseObject(resBody, WebResponseBody.class);
                if (webResponseBody.getResult_code() == 200) {

                    return true;
                    ///////////////
                } else {
                    return false;
                }
            } catch (IOException e) {
                Log.d("order_no", "erroe" + e);
//            return null;
                return false;
            }
        }
        return false;
    }
}
