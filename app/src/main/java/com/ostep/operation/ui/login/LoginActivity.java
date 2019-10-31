package com.ostep.operation.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapclient.liteapp.LiteActivity;
//import com.ostep.operation.R;
import com.R;
import com.baidu.navi.sdkdemo.activity.DemoMainActivity;
import com.baidu.navi.sdkdemo.activity.location.MyLocation;
import com.baidu.navi.sdkdemo.activity.location.MyLocationActivity;
import com.ostep.operation.ui.account.InitAccountActivity;
import com.ostep.operation.ui.delivery.CustomTabActivity;
import com.step.operation.common.CommonConstants;
import com.step.operation.common.UrlConstants;
import com.step.operation.common.WebResponseBody;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        //add
        SDKInitializer.initialize(getApplicationContext());//在Application的onCreate()不行，必须在activity的onCreate()中
        //add
        setContentView(R.layout.activity_login);

        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.activity_login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
//add
        String userInfo = "{\"userId\":23,\"token\":\"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxODUxOTIzMzY4MiIsImF1dGgiOlt7ImF1dGhvcml0eSI6IkFkbWluIn1dLCJpYXQiOjE1NzIyNjY2ODAsImV4cCI6MTU3NDg1ODY4MH0.bBz5M6ew0N3lqZeJAYy7EEc_Dum8UJOlcDR6M9q0wXQ\",\"role\":10,\"d_code\":\"bazhong_001\",\"opUserPhone\":\"18519233682\"}";
//        {"userId":23,"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxODUxOTIzMzY4MiIsImF1dGgiOlt7ImF1dGhvcml0eSI6IkFkbWluIn1dLCJpYXQiOjE1NzIzNTQ4MzIsImV4cCI6MTU3NDk0NjgzMn0.vBziQsVnHzXFUUYi_W4Fw9lrqr_4NDzDzd9RDSIiREs","role":10,"d_code":"bazhong_001","opUserPhone":"18519233682"}

        Intent intent = new Intent(LoginActivity.this, CustomTabActivity.class);
        intent.putExtra(CommonConstants.USER_INFO,userInfo);
        startActivity(intent);

     //add
        //
            loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

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
                String phone = usernameEditText.getText().toString();
                String pwd = passwordEditText.getText().toString();
                if (phone!=null && phone.length()==11 && pwd!=null && pwd.length()>=8){
                    loginButton.setEnabled(true);
                }
            }
        };
        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String phone = usernameEditText.getText().toString();
                if (phone!=null && phone.length()==11){
                    if (!checkSetPwd(usernameEditText.getText().toString())){
                        Toast.makeText(LoginActivity.this, "没设置密码，跳转到设置密码页 ",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, InitAccountActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    login(usernameEditText.getText().toString(),
//                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                if (checkSetPwd(usernameEditText.getText().toString())){
                    String userInfo=login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                    if (userInfo!=null){
                        //TODO 存储token，跳转到任务也没
                        Log.e("userInfo",userInfo);
                        Toast.makeText(LoginActivity.this, "登录成功 ",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, CustomTabActivity.class);
                        intent.putExtra(CommonConstants.USER_INFO,userInfo);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "登录失败，请重试 ",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Log.d("LoginActivity", "没设置密码，跳转到设置密码页 ");
                    Intent intent = new Intent(LoginActivity.this, InitAccountActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    //add
//    public void open1(View v){
//        String s = "open";
//        Toast.makeText( LoginActivity.this,s, Toast.LENGTH_LONG).show();
////        Log.e("sdfs","1111111111111111111111111111111111111111");
//
//        Intent intent = new Intent(LoginActivity.this, MyLocationActivity.class);
//        startActivity(intent);
//
////        Log.e("sdfs","22222222222222222222222222222222222222222");
//    }
//    public void open(View v){
//        String s = "open";
//        Toast.makeText( LoginActivity.this,s, Toast.LENGTH_LONG).show();
////        Log.e("sdfs","1111111111111111111111111111111111111111");
//
//        //直接进DemoMainActivity，不然Lite进不去
//        Intent intent = new Intent(LoginActivity.this, DemoMainActivity.class);
//        startActivity(intent);
//
////        Log.e("sdfs","22222222222222222222222222222222222222222");
//    }

        public void lostPwd(View v){
            Log.d("LoginActivity", "找回密码，跳转到设置密码页 ");
            Intent intent = new Intent(LoginActivity.this, InitAccountActivity.class);
            startActivity(intent);
    }
    //add

    private boolean checkSetPwd(String username) {
        String fullCheckPwdUrl = UrlConstants.CHECK_PWD_URL+username;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(fullCheckPwdUrl).build();
        try {
            Response response = client.newCall(request).execute();
            String resBody = response.body().string();
            Log.d("LoginViewModel", "checkSetPwd: "+resBody);
            WebResponseBody webResponseBody = JSONObject.parseObject(resBody,WebResponseBody.class);
            if (webResponseBody.getResult_code()==200){
                return true;
            }else {
                return false;
            }
        }catch (IOException e){
            Log.d("LoginViewModel", "checkSetPwd:"+e);
            return false;
        }
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private String login(String username, String password){
        String fullUrl = String.format(UrlConstants.OP_USER_SIGNIN, username,password);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(fullUrl)
                .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), "{}"))
                .build();
        try {
            Response response = client.newCall(request).execute();
            String resBody = response.body().string();
            Log.d("login", " result: "+resBody);
            WebResponseBody webResponseBody = JSONObject.parseObject(resBody,WebResponseBody.class);
            if (webResponseBody.getResult_code()==200){
                return JSONObject.toJSONString(webResponseBody.getData());
            }else {
                return null;
            }
        }catch (IOException e){
            Log.d("LoginActivity", "login:"+e);
            return null;
        }
    }
}
