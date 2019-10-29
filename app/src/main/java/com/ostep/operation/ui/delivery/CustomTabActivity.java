package com.ostep.operation.ui.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
//import com.ostep.operation.R;
import com.R;

import com.baidu.navi.sdkdemo.activity.location.MyLocationActivity;
import com.ostep.operation.ui.login.LoginActivity;
import com.step.operation.common.CommonConstants;
import com.step.operation.common.DataGenerator;
import com.step.operation.common.UserInfo;

/**
 * @author yanqiang
 */

public class CustomTabActivity extends AppCompatActivity implements CustomTabView.OnTabCheckListener{
    private CustomTabView mCustomTabView;
    private Fragment[]mFragmensts;
    public static UserInfo userInfo;
    private Button Deliver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String userInfoStr = intent.getStringExtra(CommonConstants.USER_INFO);
        userInfo = JSON.parseObject(userInfoStr, UserInfo.class);
        setContentView(R.layout.custom_tab_ac_layout);
        mFragmensts = DataGenerator.getFragments("CustomTabView Tab");
        initView();

        Deliver = (Button) findViewById(R.id.Deliver);
        //Intent

    }
    //add
    public void Deliver(View v )
    {
        Button button = (Button) v;


        String str = "aaa" + v.getContentDescription();
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(CustomTabActivity.this, DeliverListActivity.class);
        intent.putExtra("task_id",v.getContentDescription());
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
}
