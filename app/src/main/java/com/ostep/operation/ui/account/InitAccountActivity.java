package com.ostep.operation.ui.account;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.alibaba.fastjson.JSONObject;
import com.ostep.operation.R;
import com.ostep.operation.ui.login.LoginActivity;
import com.step.operation.common.UrlConstants;
import com.step.operation.common.WebResponseBody;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InitAccountActivity extends AppCompatActivity {


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText phoneEditText = findViewById(R.id.phone);
        final Button sendCodeButton = findViewById(R.id.send_code);

        final EditText verifyCodeEditText = findViewById(R.id.verify_code);
        final EditText passwordEditText = findViewById(R.id.newpassword);
        final Button confirmButton = findViewById(R.id.set_confirm);


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = phoneEditText.getText().toString();
                if (!(phone==null || phone.isEmpty())){
                    if (phone.trim().length()==11){
                        sendCodeButton.setEnabled(true);
                    }
                }
            }
        };

        TextWatcher afterCodePwdChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = phoneEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String code = verifyCodeEditText.getText().toString();
                boolean phoneChecked = false;
                if (!(phone==null || phone.isEmpty())){
                    if (phone.trim().length()==11){
                        phoneChecked=true;
                    }
                }

                boolean codeChecked = false;
                if (code!=null && code.length()==4){
                    codeChecked = true;
                }

                boolean pwdChecked = password != null && password.trim().length() > 5;

                if (phoneChecked && codeChecked && pwdChecked){
                    confirmButton.setEnabled(true);
                }else {
                    confirmButton.setEnabled(false);
                }
            }
        };

        phoneEditText.addTextChangedListener(afterTextChangedListener);
        verifyCodeEditText.addTextChangedListener(afterCodePwdChangedListener);
        passwordEditText.addTextChangedListener(afterCodePwdChangedListener);

        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = phoneEditText.getText().toString();
                if (!(phone==null || phone.isEmpty())){
                    if (phone.trim().length()==11){
                        /**
                         * 调用发送验证码接口
                         * POST
                         * https://api.ostep.com.cn/auth/send/code?type=1&receiver=18519233682
                         */
                        sendCode(phone);
                    }
                }
            }
        });



        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = phoneEditText.getText().toString();
                String code = verifyCodeEditText.getText().toString();
                String pwd = passwordEditText.getText().toString();
//                boolean verifyResult = verifyCode(phone,code);
//                if (verifyResult){
                    /**
                     * 调用设置密码逻辑
                     * POST
                     */
                boolean rs = setPwd(phone, pwd,code);
                if (rs){
                    Toast.makeText(InitAccountActivity.this, "密码设置成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InitAccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
//                }
            }
        });
    }

    private boolean setPwd(String phone, String pwd,String code) {
        String fullSetPwdUrl = UrlConstants.set_pwd_URL;
        JSONObject  formBodyBuilder = new JSONObject();
        formBodyBuilder.put("phone",phone);
        formBodyBuilder.put("code", code);
        formBodyBuilder.put("password",pwd);
        RequestBody body = RequestBody.create(JSON, formBodyBuilder.toJSONString());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(fullSetPwdUrl)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String resBody = response.body().string();
            Log.d("InitAccountActivity", "setPwd: "+resBody);
            WebResponseBody webResponseBody = JSONObject.parseObject(resBody,WebResponseBody.class);
            if (webResponseBody.getResult_code()==200){
                return true;
            }else {
                return false;
            }
        }catch (IOException e){
            Log.d("InitAccountActivity", "setPwd:"+e);
            return false;
        }
    }


    private boolean verifyCode(String mobile, String code) {
        String fullVerifyCodeUrl = String.format(UrlConstants.verify_CODE_URL,mobile,code);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(fullVerifyCodeUrl)
                .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), "{}"))
                .build();
        try {
            Response response = client.newCall(request).execute();
            String resBody = response.body().string();
            Log.d("InitAccountActivity", "verifyCode: "+resBody);
            WebResponseBody webResponseBody = JSONObject.parseObject(resBody,WebResponseBody.class);
            if (webResponseBody.getResult_code()==200){
                return true;
            }else {
                return false;
            }
        }catch (IOException e){
            Log.d("InitAccountActivity", "verifyCode:"+e);
            return false;
        }
    }


    private boolean sendCode(String mobile) {
        String fullSendCodeUrl = String.format(UrlConstants.SEND_CODE_URL,mobile);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(fullSendCodeUrl)
                .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), "{}"))
                .build();
        try {
            Response response = client.newCall(request).execute();
            String resBody = response.body().string();
            Log.d("InitAccountActivity", "sendCode: "+resBody);
            WebResponseBody webResponseBody = JSONObject.parseObject(resBody,WebResponseBody.class);
            if (webResponseBody.getResult_code()==200){
                return true;
            }else {
                return false;
            }
        }catch (IOException e){
            Log.d("InitAccountActivity", "sendCode:"+e);
            return false;
        }
    }

}
