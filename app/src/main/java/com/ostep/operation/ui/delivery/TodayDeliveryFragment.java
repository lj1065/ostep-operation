package com.ostep.operation.ui.delivery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONObject;
//import com.ostep.operation.R;
import com.R;

import com.ostep.operstion.adapter.DeliveryTaskListViewAdapter;
import com.step.operation.common.UrlConstants;
import com.step.operation.common.WebResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhouwei on 17/4/23.
 */

public class TodayDeliveryFragment extends Fragment {

    private ListView listView;
    private String mFrom;
//    private Button startDeliver;
    public static TodayDeliveryFragment newInstance(String from){
        TodayDeliveryFragment fragment = new TodayDeliveryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from",from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mFrom = getArguments().getString("from");
        }

    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.home_fragment_layout,null);
//        TextView textView = (TextView) view.findViewById(R.id.title_from);
//        TextView content = (TextView) view.findViewById(R.id.fragment_content);
//        textView.setText(mFrom);
//        content.setText("current content");
//        return view;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.home_fragment_layout , container, false);
        listView = (ListView)view.findViewById(R.id.listview);
        List<Map<String, Object>> list=getData();
        listView.setAdapter(new DeliveryTaskListViewAdapter(getActivity(), list));
        return view;
    }

    public List<Map<String, Object>> getData(){
       // String fullGetMyTaskUrl = String.format(UrlConstants.MY_DELIVERY_TASKS,CustomTabActivity.userInfo.getUserId());
        String fullGetMyTaskUrl = String.format(UrlConstants.MY_DELIVERY_TASKS,CustomTabActivity.userInfo.getUserId(),CustomTabActivity.userInfo.getD_code());
//        String fullGetMyTaskUrl ="https://api.ostep.com.cn/ostep/ship/my/tasks?user_id=23&d_code=bazhong_001";
                OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(fullGetMyTaskUrl).build();
        try {
            Response response = client.newCall(request).execute();
            String resBody = response.body().string();
            Log.d("TodayDeliveryFragment", " task list: "+resBody);
            WebResponseBody webResponseBody = JSONObject.parseObject(resBody,WebResponseBody.class);
            if (webResponseBody.getResult_code()==200){
                Log.e("TodayDelivery", String.valueOf((List<Map<String, Object>>)webResponseBody.getData()));
                return (List<Map<String, Object>>)webResponseBody.getData();
            }else {
                return null;
            }
        }catch (IOException e){
            Log.d("TodayDeliveryFragment", "task list:"+e);
            return null;
        }
    }

}
