package com.ostep.operstion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.ostep.operation.R;

import java.util.List;
import java.util.Map;

public class DeliveryTaskListViewAdapter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public DeliveryTaskListViewAdapter(Context context,List<Map<String, Object>> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }
    /**
     * 组件集合，对应list.xml中的控件
     * @author Administrator
     */
    public final class TaskVew{
        public ImageView image;
        public TextView desc;
        public TextView status;
        public Button startDeliver;

    }
    @Override
    public int getCount() {
        return data.size();
    }
    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskVew zujian=null;
        if(convertView==null){
            zujian=new TaskVew();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.listview_delivery_task, null);

            zujian.image=(ImageView)convertView.findViewById(R.id.image);
            zujian.desc=(TextView)convertView.findViewById(R.id.desc);
            zujian.status=(TextView)convertView.findViewById(R.id.status);
            zujian.startDeliver=(Button)convertView. findViewById(R.id.startDeliver);
            convertView.setTag(zujian);
        }else{
            zujian=(TaskVew)convertView.getTag();
        }
        //绑定数据

        if ((Integer) data.get(position).get("order_type")!=null
                && (Integer) data.get(position).get("order_type")==0){
            zujian.image.setBackgroundResource(R.drawable.tab_attention_selector);
        }else{
            zujian.image.setBackgroundResource(R.drawable.tab_discovery_selector);
        }
        JSONArray orderNos = (JSONArray)data.get(position).get("ship_order_nos");
        if (orderNos!=null){
            zujian.desc.setText(orderNos.size()+"单");
        }else {
            zujian.desc.setText("0单");
        }

        Integer statusInt = (Integer)data.get(position).get("status");
        String status=null;
        if (statusInt!=null){
            if (statusInt==0){
                status ="未确认";
            }else if (statusInt==1){
                status ="已确认";
            }else if (statusInt==2){
                status ="拒绝";
            }else if (statusInt==3){
                status ="配送中";
            }else if (statusInt==4){
                status ="配送完成";
            }else {
                status ="未知";
            }
        }
        zujian.status.setText(status);
        return convertView;
    }
}
