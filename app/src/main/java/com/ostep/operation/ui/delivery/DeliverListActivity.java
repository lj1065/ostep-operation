package com.ostep.operation.ui.delivery;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.R;
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
    private TextView dest_position;
    private TextView dest_address;
    private String task_id_num;
    private List<Map<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_list);

        //取值
        Intent intent = getIntent();
        //获取Intent中的数据
//        LightInfo = intent.getStringExtra("LightInfo" );//int
        task_id_num = intent.getStringExtra("task_id" );//int
        task_id_num = task_id_num.replace("\"", "");
        task_id_num = task_id_num.replace("[", "");
        task_id_num = task_id_num.replace("]", "");
        Toast.makeText(this, task_id_num, Toast.LENGTH_SHORT).show();
//        getSubmit();
          new SentInfoTask().execute(task_id_num);

        //获取控件
        task_id = (TextView) findViewById(R.id.task_id);
        dest_position = (TextView) findViewById(R.id.dest_position);
        dest_address = (TextView) findViewById(R.id.dest_address);
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
                task_id_num = "shiptask2019102022381750";
                String fullGetMyTaskUrl = String.format(UrlConstants.DELIVERY_TASKS_INFO,task_id_num);
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

                Log.e("ship_no",data.get(0).get("ship_no").toString());
//            JSONArray orderNos = (JSONArray)data.get(0).get("dest_position");
//            Log.e("orderNos",s);
            for (int s = 0; s < data.size();s++ ) {

                task_id.setText(data.get(s).get("ship_no").toString());
                dest_position.setText(data.get(s).get("dest_position").toString());
                dest_address.setText(data.get(s).get("dest_address").toString());
//                JSONArray orderNos = (JSONArray) data.get(s).get("ship_order_nos");
//                Log.e("DELIVERY_TASKS_INFO",orderNos+"");
            }
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
//         intent.putExtra("location",dest_position.getText().toString());
         startActivity(intent);

     }
}
