package com.ostep.operation.ui.delivery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//import com.ostep.operation.R;
import com.R;
import com.ostep.operstion.adapter.DeliveryTaskListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  @author yanqiang
 */

public class HistoryDeliveryFragment extends Fragment {

    private ListView listView;
    private String mFrom;
    //add
    private SimpleAdapter adapter;

    public static HistoryDeliveryFragment newInstance(String from){
        HistoryDeliveryFragment fragment = new HistoryDeliveryFragment();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.home_fragment_layout , container, false);
        listView = (ListView)view.findViewById(R.id.listview);
        List<Map<String, Object>> list = getData();
        listView.setAdapter(new DeliveryTaskListViewAdapter(getActivity(), list));
//        adapter = new SimpleAdapter(DeliveryTaskListViewAdapter,list,R.layout.home_fragment_layout
//                ,new String[]{"image","task_id","desc"}
//                ,new int[]{R.id.image,R.id.task_id,R.id.desc});
        return view;
    }

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", R.drawable.tab_attention_selector);
            map.put("task_id", "这是一个标题"+i);
            map.put("desc", "这是一个详细信息" + i);
            list.add(map);
        }

        return list;
    }

}
