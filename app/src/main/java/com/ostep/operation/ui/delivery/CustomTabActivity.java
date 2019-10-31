package com.ostep.operation.ui.delivery;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.alibaba.fastjson.JSON;
//import com.ostep.operation.R;
import com.R;

import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.navi.sdkdemo.activity.location.MyLocationActivity;
import com.ostep.operation.ui.login.LoginActivity;
import com.step.operation.common.CommonConstants;
import com.step.operation.common.DataGenerator;
import com.step.operation.common.UrlConstants;
import com.step.operation.common.UserInfo;
import com.step.operation.common.WebResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author yanqiang
 */

public class CustomTabActivity extends AppCompatActivity implements CustomTabView.OnTabCheckListener{
    private CustomTabView mCustomTabView;
    private Fragment[]mFragmensts;
    public static UserInfo userInfo;
    private Button Deliver;
    public int choose_num = 0;
    private List<Map<String, Object>> data;
    private Object address;
//    public String[] task_ids  = new String[100];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String userInfoStr = intent.getStringExtra(CommonConstants.USER_INFO);
        userInfo = JSON.parseObject(userInfoStr, UserInfo.class);
        setContentView(R.layout.custom_tab_ac_layout);
        mFragmensts = DataGenerator.getFragments("");
        initView();

        Deliver = (Button) findViewById(R.id.Deliver);
        //Intent

    }
    //add
    public void Deliver(View v )
    {
        Button button = (Button) v;
        String str = "aaa" + v.getContentDescription();
//        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CustomTabActivity.this, DeliverListActivity.class);
        intent.putExtra("task_id",v.getContentDescription());
        startActivity(intent);
    }

    public void ChooseBox(View v)
    {
        CheckBox box = (CheckBox) v;
        String task_id = box.getContentDescription().toString();
        TextView textview = (TextView)  findViewById(R.id.box_num);
        Button Deliver_bt = (Button)  findViewById(R.id.Deliver_bt);


        TextView task_ids = (TextView)  findViewById(R.id.task_ids);
        String task_ids_string = task_ids.getText().toString();

        if(((CheckBox) v).isChecked())
        {
            choose_num = choose_num + 1;

            task_ids_string = task_ids_string + "," + task_id;
            task_ids.setText(task_ids_string);

            Deliver_bt.setContentDescription(task_ids_string);

            textview.setText(choose_num + "");

//            Log.e("task_ids.length",task_id[0] + "");
//            if(task_ids.length >= choose_num)
//            {
//                for (int j = 0; j < task_ids.length; j++) {
//                    if (task_ids[j].isEmpty()) {
//                        task_ids[j] = task_id;
//                    }
//                }
//            }else
//            {
//                for (int i = 0; i < choose_num; i++)
//                    task_ids[i] = task_id;
//            }
        }else{
            choose_num = choose_num - 1;
//            String replace1 = ","+task_id;
            task_ids_string = task_ids_string.replace( ","+task_id , "");

            Log.e("task_ids_string",task_ids_string);
            task_ids.setText(task_ids_string);

            Deliver_bt.setContentDescription(task_ids_string);

            textview.setText(choose_num + "");
//            for (int j = 0; j < task_ids.length; j++) {
//                if (task_ids[j].equals(task_id)) {
//                    task_ids[j] = null;
//                }
//            }

        }
    }


    public void goToDeliver(View v)
    {
        Button goToDeliverButton = (Button) v;
        Log.e("goToDeliver","122222222222222222");
        TextView task_ids = (TextView)  findViewById(R.id.task_ids);
        String task_ids_string = task_ids.getText().toString();
        TextView textview = (TextView)  findViewById(R.id.box_num);

        String[] i = task_ids_string.split(",");
//        Log.e("getdata",i[0] + "--"+i[1]);
        ProgressBar loading = (ProgressBar)findViewById(R.id.loading);

        for (int k =1;k < choose_num+1;k++)
        {
            Log.e("getdata",i[k]);
            if(i[k].isEmpty())
            {

            }else {
//                new SentInfoTask().execute(i[k]);
                loading.setVisibility(View.VISIBLE);
                goToDeliverButton.setEnabled(false);
                   while (getTaskData(i[k])){
                        int op =1;
                   }
                loading.setVisibility(View.GONE);
                     Log.e("getdata11",i[k]);
            }
        }


        //add
        SDKInitializer.initialize(getApplicationContext());//在Application的onCreate()不行，必须在activity的onCreate()中
        //add
        Intent intent = new Intent(CustomTabActivity.this, MyLocationActivity.class);

        String sent_phone = "18519233682";
        String sent_receiver = "颜强";

        TextView ship_no = (TextView)  findViewById(R.id.ship_no_string);;
        String ship_no_string = ship_no.getText().toString();

        TextView dest_position = (TextView)  findViewById(R.id.dest_position_string);;
        String dest_position_string = dest_position.getText().toString();

        TextView user_info = (TextView)  findViewById(R.id.user_info_string);;
        String user_info_string = user_info.getText().toString();

        Log.e("dest_position_string",dest_position_string);

        intent.putExtra("sent_location",dest_position_string);
        intent.putExtra("user_info",user_info_string);

//        Log.e("sent_location",sent_location);
        startActivity(intent);

    }
    //add
    private void initView() {
        mCustomTabView = (CustomTabView) findViewById(R.id.custom_tab_container);
        CustomTabView.Tab tabDis = new CustomTabView.Tab().setText("今日")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.drawable.ic_tab_strip_icon_category)
                .setPressedIcon(R.drawable.ic_tab_strip_icon_category_selected);
        mCustomTabView.addTab(tabDis);
        CustomTabView.Tab tabAttention = new CustomTabView.Tab().setText("历史")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.drawable.ic_tab_strip_icon_pgc)
                .setPressedIcon(R.drawable.ic_tab_strip_icon_pgc_selected);
        mCustomTabView.addTab(tabAttention);
        CustomTabView.Tab tabProfile = new CustomTabView.Tab().setText("我的")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.drawable.ic_tab_strip_icon_profile)
                .setPressedIcon(R.drawable.ic_tab_strip_icon_profile_selected);
        mCustomTabView.addTab(tabProfile);

        mCustomTabView.setOnTabCheckListener(this);

        mCustomTabView.setCurrentItem(0);

    }

    @Override
    public void onTabSelected(View v, int position) {
        Log.e("CustomTabActivity","position:"+position);
        onTabItemSelected(position);
    }

    private void onTabItemSelected(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = mFragmensts[0];
                break;
            case 1:
                fragment = mFragmensts[1];
                break;

            case 2:
                fragment = mFragmensts[2];
                break;
            case 3:
                fragment = mFragmensts[3];
                break;
        }
        if(fragment!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
        }
    }

//    class SentInfoTask extends AsyncTask {
//
//        @Override
//        protected Object doInBackground(Object[] objects) {
//
//        }
    public boolean getTaskData(String objects) {
        String task_id_num = objects;      //请求路径
//
//                task_id_num = "shiptask2019102022381750";
        String fullGetMyTaskUrl = String.format(UrlConstants.DELIVERY_TASKS_INFO, task_id_num);
//                Log.e("fullGetMyTaskUrl",fullGetMyTaskUrl);
//                return null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(fullGetMyTaskUrl).build();
        try {
            Response response = client.newCall(request).execute();
            String resBody = response.body().string();
            Log.d("DeliveryListFragment", " task list: " + resBody);
            WebResponseBody webResponseBody = JSONObject.parseObject(resBody, WebResponseBody.class);
            if (webResponseBody.getResult_code() == 200) {
                Log.e("DeliveryList", String.valueOf((List<Map<String, Object>>) webResponseBody.getData()));
//                    return (List<Map<String, Object>>) webResponseBody.getData();
                /////////////////
//                data = (List<Map<String, Object>>) o;
                data = (List<Map<String, Object>>) webResponseBody.getData();

                TextView ship_no = (TextView) findViewById(R.id.ship_no_string);
                ;
                String ship_no_string = ship_no.getText().toString();

                TextView dest_position = (TextView) findViewById(R.id.dest_position_string);
                ;
                String dest_position_string = dest_position.getText().toString();

                TextView user_info = (TextView) findViewById(R.id.user_info_string);

                String user_info_string = user_info.getText().toString();

                if (data.isEmpty()) {
                    String s2 = "无订单信息！";
//                    task_id.setText(data.get(0).get("ship_no").toString());
                    Toast.makeText(CustomTabActivity.this, s2, Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("ship_no", data.get(0).get("ship_no").toString());
                    for (int s = 0; s < data.size(); s++) {

                        ship_no_string = ship_no_string + "," + data.get(s).get("ship_no").toString();
                        dest_position_string = dest_position_string + "," + data.get(s).get("dest_position").toString();

//                        data.get(s).get("dest_address").get('address');
//                        address = data.get(s).get("dest_address");
                        JSONObject jsonObject= JSON.parseObject(data.get(s).get("dest_address").toString());
                        String address_String = jsonObject.getString("address");
                        String phone_String = jsonObject.getString("phone");
                        String receiver_String = jsonObject.getString("receiver");

//                        JSONObject jsondata= JSON.parseObject(data);
//                        String token = jsondata.getString("access_token");


                        user_info_string = user_info_string + "," + address_String + "+" + phone_String + "+" +receiver_String;

                        Log.e("user_info_string", user_info_string);

                    }
                }
                ship_no.setText(ship_no_string);
                dest_position.setText(dest_position_string);
                user_info.setText(user_info_string);

                return false;
                ///////////////
            } else {
                return false;
            }
        } catch (IOException e) {
            Log.d("DeliveryListFragment", "task list:" + e);
//            return null;
            return false;
        }
    }


//        }

//        @Override
//        protected void onPostExecute(Object o) {
//
//            super.onPostExecute(o);
//            //           String s= (String) o;
//        }
//    }

}
