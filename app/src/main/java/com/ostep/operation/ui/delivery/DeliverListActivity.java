package com.ostep.operation.ui.delivery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.navi.sdkdemo.activity.DemoMainActivity;
import com.baidu.navi.sdkdemo.activity.location.MyLocationActivity;
import com.ostep.operation.ui.login.LoginActivity;
import com.step.operation.common.UrlConstants;
import com.step.operation.common.WebResponseBody;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DeliverListActivity extends AppCompatActivity {

    private TextView task_id;
    private TextView ship_no;
    private TextView dest_position;
    private TextView dest_address;
    private TextView dest_address_show;
    private String task_id_num;
    private List<Map<String, Object>> data;
    private static final String[] authBaseArr = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int authBaseRequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_list);

        //取值
        Intent intent = getIntent();
        //获取Intent中的数据
//        LightInfo = intent.getStringExtra("LightInfo" );//int
        task_id_num = intent.getStringExtra("task_id" );//int
//        Toast.makeText(this, task_id_num, Toast.LENGTH_SHORT).show();
//        getSubmit();
          new SentInfoTask().execute(task_id_num);

        //获取控件
        task_id = (TextView) findViewById(R.id.task_id);
        ship_no =(TextView) findViewById(R.id.ship_no);
        dest_position = (TextView) findViewById(R.id.dest_position);
        dest_address = (TextView) findViewById(R.id.dest_address);
        dest_address_show = (TextView) findViewById(R.id.dest_address_show);
        task_id.setText(task_id_num);
        initNavi();
    }
    private void initNavi() {
        // 申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }

//        if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited()) {
//            return;
//        }
//
//
    }
    private boolean hasBasePhoneAuth() {
        PackageManager pm = this.getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager
                    .PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    //post方式提交数据去服务器验证
//    public void getSubmit() {
//        //获取输入框的name、PumpNum和WorkstationID
////        String name=et_name.getText().toString();
////        String PumpNum=et_PumpNum.getText().toString();
////        String WorkstationID=et_WorkstationID.getText().toString();
//        //服务器验证路径
//        String path="https://api.ostep.com.cn/ostep/ship/my/task/order?task_id=ship112019090100263915";
//        new SentInfoTask().execute(path);
//    }

    class SentInfoTask extends AsyncTask {
        //private HttpURLConnection httpURLConnection;
//        @Override
//        protected Object doInBackground(Object[] objects) {
//            //获取传过来的参数
//
////            String name=objects[0].toString();    //name
////            String PumpNum=objects[1].toString();    //PumpNum
////            String WorkstationID = objects[2].toString();//WorkstationID
//            String path=objects[0].toString();      //请求路径
//            //打印参数
//            Log.e("path",path);
//
//            try{
//                //String path = "http://ucas.lj1065.com/project/index.php";
//                URL url = new URL(path);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setConnectTimeout(5000);
//                conn.setRequestMethod("GET");
//                //准备数据，设置post的参数
////                String data = "name="+name+"&PumpNum="+PumpNum+"&WorkstationID="+WorkstationID;
//
////                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
////                conn.setRequestProperty("Content-Length", data.length()+"");
//                //可以写入数据
////                conn.setDoOutput(true);
////                OutputStream os = conn.getOutputStream();
////                os.write(data.getBytes());
//
//                int code = conn.getResponseCode();
//
//                //如果发送出成功
//                if(code == 200){
//                    ////add
//                    Response response = client.newCall(request).execute();
//                    String resBody = response.body().string();
//                    Log.d("TodayDeliveryFragment", " task list: "+resBody);
//                    WebResponseBody webResponseBody = JSONObject.parseObject(resBody,WebResponseBody.class);
//                    ////add
//                    InputStream is = conn.getInputStream();
////                    BufferedReader br=new BufferedReader(new InputStreamReader(is));
////                    String s=br.readLine();
//                    //add
//                    byte[] b=new byte[1024*512]; //定义一个byte数组读取输入流
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream(); //定义缓存流来保存输入流的数据
//                    int len=0;
//                    while((len=is.read(b))>-1){  //每次读的len>-1 说明是是有数据的
//                        baos.write(b,0,len);  //三个参数  输入流byte数组   读取起始位置  读取终止位置
//                    }
//                    String msg=baos.toString();
//
//                    Log.e("ok",msg);
////                    return  s;
//                    return msg;
//                }else {
//                    return null;
//                }
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//            return null;
//            //return loginByPost(name,PumpNum,WorkstationID);
            @Override
             protected Object doInBackground(Object[] objects) {
                String task_id_num=objects[0].toString();      //请求路径
//
//                task_id_num = "shiptask2019102022381750";
                String fullGetMyTaskUrl = String.format(UrlConstants.DELIVERY_TASKS_INFO,task_id_num);
//                Log.e("fullGetMyTaskUrl",fullGetMyTaskUrl);
//                return null;
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(fullGetMyTaskUrl).build();
                try {
                    Response response = client.newCall(request).execute();
                    String resBody = response.body().string();
//                    Log.d("DeliveryListFragment", " task list: " + resBody);
                    Log.e("DeliveryListFragment", resBody);
                    Log.e("DeliveryListFragment", "ssssssssssssssssssssss");
                    /****
                     * {"result_code":200,"msg":"success","timestamp":1573358069666,"data":[{"ship_no":"ship112019102902302071","order_no":"OSTEP112019102902300980","order_type":0,"ship_price":0,"ship_status":1,"d_code":"bazhong_001","start_position":null,"dest_position":"106.7659450000,31.8389660000","start_address":null,"dest_address":"{\"address\":\"四川省巴中市巴州区将军大道477号\",\"clientName\":\"土家十大碗\",\"createTime\":1569942580000,\"id\":33,\"lat\":31.8389660000,\"lng\":106.7659450000,\"phone\":\"18519233682\",\"receiver\":\"颜强\",\"updateTime\":1569942580000,\"userId\":11}","ship_time":null,"create_time":"2019-10-28T18:30:20.000+0000","start_time":"2019-10-30T00:00:00.000+0000","end_time":"2019-10-30T01:00:00.000+0000","tips":null,"update_time":"2019-10-28T18:30:20.000+0000","deliver_info":null,"delivery_time":"8:00-9:00","order_info":null,"client_name":null}]}
                     */
                    String String1 = "{\"result_code\":200,\"msg\":\"success\",\"timestamp\":1573358069666,\"data\":[{\"ship_no\":\"ship112019102902302071\",\"order_no\":\"OSTEP112019102902300980\",\"order_type\":0,\"ship_price\":0,\"ship_status\":1,\"d_code\":\"bazhong_001\",\"start_position\":null,\"dest_position\":\"109.4915232961,18.2121602950\",\"start_address\":null,\"dest_address\":\"{\\\"address\\\":\\\"四川省巴中市巴州区将军大道477号\\\",\\\"clientName\\\":\\\"土家十大碗\\\",\\\"createTime\\\":1569942580000,\\\"id\\\":33,\\\"lat\\\":18.2121602950,,\\\"lng\\\":109.4915232961,\\\"phone\\\":\\\"18519233682\\\",\\\"receiver\\\":\\\"颜强\\\",\\\"updateTime\\\":1569942580000,\\\"userId\\\":11}\",\"ship_time\":null,\"create_time\":\"2019-10-28T18:30:20.000+0000\",\"start_time\":\"2019-10-30T00:00:00.000+0000\",\"end_time\":\"2019-10-30T01:00:00.000+0000\",\"tips\":null,\"update_time\":\"2019-10-28T18:30:20.000+0000\",\"deliver_info\":null,\"delivery_time\":\"8:00-9:00\",\"order_info\":null,\"client_name\":null},{\"ship_no\":\"ship112019102902302071\",\"order_no\":\"OSTEP112019102902300980\",\"order_type\":0,\"ship_price\":0,\"ship_status\":1,\"d_code\":\"bazhong_001\",\"start_position\":null,\"dest_position\":\"109.4915122961,18.2121502950\",\"start_address\":null,\"dest_address\":\"{\\\"address\\\":\\\"四川省巴中市巴州区将军大道477号\\\",\\\"clientName\\\":\\\"土家十大碗\\\",\\\"createTime\\\":1569942580000,\\\"id\\\":33,\\\"lat\\\":18.2121502950,,\\\"lng\\\":109.4915122961,\\\"phone\\\":\\\"18519233682\\\",\\\"receiver\\\":\\\"颜强\\\",\\\"updateTime\\\":1569942580000,\\\"userId\\\":11}\",\"ship_time\":null,\"create_time\":\"2019-10-28T18:30:20.000+0000\",\"start_time\":\"2019-10-30T00:00:00.000+0000\",\"end_time\":\"2019-10-30T01:00:00.000+0000\",\"tips\":null,\"update_time\":\"2019-10-28T18:30:20.000+0000\",\"deliver_info\":null,\"delivery_time\":\"8:00-9:00\",\"order_info\":null,\"client_name\":null}]}";
//                    WebResponseBody webResponseBody = JSONObject.parseObject(resBody, WebResponseBody.class);
                    WebResponseBody webResponseBody = JSONObject.parseObject(String1, WebResponseBody.class);

                    if (webResponseBody.getResult_code() == 200) {

                        Log.e("DeliveryList", String.valueOf((List<Map<String, Object>>) webResponseBody.getData()));

                        return (List<Map<String, Object>>) webResponseBody.getData();
                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    Log.d("DeliveryListFragment", "task list:" + e);
                    return null;
                }
            }

//        }

        @Override
        protected void onPostExecute(Object o) {

                super.onPostExecute(o);
                //           String s= (String) o;
                data = (List<Map<String, Object>>)o;
                Log.e("datadata",data.toString());
            /**
             * [{"delivery_time":"9:00-10:00","d_code":"bazhong_001","dest_position":"106.7659450000,31.8389660000","order_no":"OSTEP11201910261730187","update_time":"2019-10-26T19:52:22.000+0000","dest_address":"{\"address\":\"四川省巴中市巴州区将军大道477号\",\"createTime\":1569942580000,\"id\":33,\"lat\":31.8389660000,\"lng\":106.7659450000,\"phone\":\"18519233682\",\"receiver\":\"颜强\",\"updateTime\":1569942580000,\"userId\":11}","ship_status":1,"end_time":"2019-09-06T01:00:00.000+0000","ship_price":0,"create_time":"2019-10-26T09:30:30.000+0000","start_time":"2019-09-06T01:00:00.000+0000","ship_no":"ship112019102617303011","order_type":0},{"delivery_time":"9:00-10:00","d_code":"bazhong_001","dest_position":"106.7659450000,31.8389660000","order_no":"OSTEP11201910261730187","update_time":"2019-10-26T19:52:22.000+0000","dest_address":"{\"address\":\"三亚\",\"createTime\":1569942580000,\"id\":33,\"lat\":31.8389660000,\"lng\":106.7659450000,\"phone\":\"18519233682\",\"receiver\":\"冷静\",\"updateTime\":1569942580000,\"userId\":11}","ship_status":1,"end_time":"2019-09-06T01:00:00.000+0000","ship_price":0,"create_time":"2019-10-26T09:30:30.000+0000","start_time":"2019-09-06T01:00:00.000+0000","ship_no":"ship112019102617303011","order_type":0}]
             **/

//            String String1 = "{\"result_code\":200,\"msg\":\"success\",\"timestamp\":1573358069666,\"data\":[{\"delivery_time\":\"9:00-10:00\",\"d_code\":\"bazhong_001\",\"dest_position\":\"106.7659450000,31.8389660000\",\"order_no\":\"OSTEP11201910261730187\",\"update_time\":\"2019-10-26T19:52:22.000+0000\",\"dest_address\":\"{\"address\":\"四川省巴中市巴州区将军大道477号\",\"createTime\":1569942580000,\"id\":33,\"lat\":18.2121602950,\"lng\":109.4915112961,\"phone\":\"18519233682\",\"receiver\":\"颜强\",\"updateTime\":1569942580000,\"userId\":11}\",\"ship_status\":1,\"end_time\":\"2019-09-06T01:00:00.000+0000\",\"ship_price\":0,\"create_time\":\"2019-10-26T09:30:30.000+0000\",\"start_time\":\"2019-09-06T01:00:00.000+0000\",\"ship_no\":\"ship112019102617303011\",\"order_type\":0},{\"delivery_time\":\"9:00-10:00\",\"d_code\":\"bazhong_001\",\"dest_position\":\"106.7659450000,31.8389660000\",\"order_no\":\"OSTEP11201910261730187\",\"update_time\":\"2019-10-26T19:52:22.000+0000\",\"dest_address\":\"{\"address\":\"四川省巴中市巴州区将军大道477号\",\"createTime\":1569942580000,\"id\":33,\"lat\":18.2121611950,\"lng\":109.4915232961,\"phone\":\"18519233682\",\"receiver\":\"颜强\",\"updateTime\":1569942580000,\"userId\":11}\",\"ship_status\":1,\"end_time\":\"2019-09-06T01:00:00.000+0000\",\"ship_price\":0,\"create_time\":\"2019-10-26T09:30:30.000+0000\",\"start_time\":\"2019-09-06T01:00:00.000+0000\",\"ship_no\":\"ship112019102617303011\",\"order_type\":0}]]}";
//
//            WebResponseBody data1 = JSONObject.parseObject(String1, WebResponseBody.class);
//
////            JSONObject data1 =JSONObject.parseObject(String1);
//            data = (List<Map<String, Object>>) data1.getData();

            if(data.isEmpty())
                {
                    String s2 = "无订单信息！";
//                    task_id.setText(data.get(0).get("ship_no").toString());
                    Toast.makeText(DeliverListActivity.this,s2, Toast.LENGTH_SHORT).show();
                }else {
                        String adressString = "";
//                        String taskIdString = "";
                        String positionString = "";
                        String ship_noString = "";
                        Log.e("ship_no",data.get(0).get("ship_no").toString());
                    for (int s = 0; s < data.size();s++ ) {

//                        taskIdString = taskIdString + "," + data.get(s).get("ship_no").toString();

                        ship_noString = ship_noString + "," + data.get(s).get("ship_no").toString();

                        JSONObject jsonObject= JSON.parseObject(data.get(s).get("dest_address").toString());
                        String address_String = jsonObject.getString("address");
                        String phone_String = jsonObject.getString("phone");
                        String receiver_String = jsonObject.getString("receiver");
                        adressString = adressString + "," + address_String + "+"+ phone_String + "+"+receiver_String + "\n";

                        positionString = positionString + "," + data.get(s).get("dest_position").toString();



//                JSONArray orderNos = (JSONArray) data.get(s).get("ship_order_nos");
//                Log.e("DELIVERY_TASKS_INFO",orderNos+"");
                    }

                ship_no.setText(ship_noString);
                dest_position.setText(positionString);
//                String show_data = data.get(s).get("dest_address").toString();
//                show_data = show_data.replace("{","");
//                show_data = show_data.replace("}","");
//                show_data = show_data.replace("\"","");
//                show_data = show_data.replace(",","\n");

                dest_address.setText(adressString);
                dest_address_show.setText(adressString);

                }

//            JSONArray orderNos = (JSONArray)data.get(0).get("dest_position");
//            Log.e("orderNos",s);

//            String s= (String) o;
//            //打印参数
//            Log.e("Toast","Toast");
//            //Toast.makeText(SentInfoActivity.this,s, Toast.LENGTH_SHORT).show();
//
//            et_name.setText(null);
//            et_PumpNum.setText(null);
//            et_WorkstationID.setText(null);
//
//            TextView status = (TextView) findViewById(R.id.status);
//            status.setText(s);
//
//
//            //Toast.makeText(ReadTextActivity.this,s, Toast.LENGTH_SHORT).show();
//            ///////AlertDialog
//
//            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(status.getContext());
//            //AlertDialog alertDialog2 = new AlertDialog.Builder(this);
//            alertDialog2.setTitle("Notice");
//            alertDialog2.setMessage("服务器返回：" + s);
//            alertDialog2.setIcon(R.mipmap.ic_launcher);
//            alertDialog2.setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    //  Toast.makeText(ReadTextActivity.this, "success", Toast.LENGTH_SHORT).show();
//                }
//            });
//            alertDialog2.create();
//            //AlertDialog dialog = alertDialog2.create();
//            alertDialog2.show();



            ////////////AlertDialog
        }
     }

     public void goDeliver(View v)
     {
         //add
         SDKInitializer.initialize(getApplicationContext());//在Application的onCreate()不行，必须在activity的onCreate()中
         //add
         Intent intent = new Intent(DeliverListActivity.this, MyLocationActivity.class);





//         String sent_location = ;

         String dest_address_String = dest_address.getText().toString();
//delete the /n
         dest_address_String = dest_address_String.replace("\n","");
//         String sent_receiver = dest_receiver.getText().toString();


//         JSONObject jsonObject= JSON.parseObject(dest_address_String);
//         String address_String = jsonObject.getString("address");
//         String phone_String = jsonObject.getString("phone");
//         String receiver_String = jsonObject.getString("receiver");
//
//         String user_info_string =  address_String + "+" + phone_String + "+" +receiver_String;
//         String sent_phone = "18519233682";
//         String sent_receiver = "颜强";

         intent.putExtra("ship_no", ship_no.getText().toString());
         intent.putExtra("task_id",  task_id.getText().toString());
         intent.putExtra("sent_location",dest_position.getText().toString());
         intent.putExtra("user_info",dest_address_String);

         Log.e("sent_location",dest_position.getText().toString());
         startActivity(intent);

     }



}
