package com.ostep.operstion.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
//import com.ostep.operation.R;

import com.R;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class DeliveryTaskListViewAdapter extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private TextView childView1;

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
        public TextView task_id;
        public TextView status;
        public Button Deliver;

        public RelativeLayout line;
//        public LinearLayout line;
        public CheckBox box;
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
            zujian.task_id=(TextView)convertView.findViewById(R.id.task_id);
            zujian.status=(TextView)convertView.findViewById(R.id.status);
            zujian.Deliver=(Button)convertView. findViewById(R.id.Deliver);
            zujian.line=(RelativeLayout)convertView. findViewById(R.id.line);
//            zujian.line=(LinearLayout)convertView. findViewById(R.id.line);
            zujian.box=(CheckBox)convertView. findViewById(R.id.box);
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
        //add
//        assert orderNos != null;
//        Log.e("orderNos",orderNos.toString());
        //add
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
                //add
                String shipOrders = (String)data.get(position).get("task_id");
//                Log.e("orderNos1",orderNos.toString());
                String s = shipOrders +"";
//                s = s.replace("\"", "");
//                s = s.replace("[", "");
//                s = s.replace("]", "");
                zujian.Deliver.setContentDescription(s);
                zujian.task_id.setText(s);
                zujian.box.setContentDescription(s);
//                zujian.line.

//                childView1 = (TextView) LayoutInflater.from(DeliveryTaskListViewAdapter.this).inflate( R.layout.listview_delivery_task,null, false);
//                Button BT;
//                BT = new Button(this);
//                childView1 = (TextView)  layoutInflater.inflate(R.layout.listview_delivery_task, null);
//                childView1.setText("详细");
//                childView1.setId(position);
//
//                zujian.line.addView(childView1);

            }else if (statusInt==1){
                status ="已确认";
                //add
                String shipOrders = (String)data.get(position).get("task_id");
//                Log.e("orderNos1",orderNos.toString());
                String s = shipOrders +"";
//                s = s.replace("\"", "");
//                s = s.replace("[", "");
//                s = s.replace("]", "");
                zujian.Deliver.setContentDescription(s);
                zujian.task_id.setText(s);
                zujian.box.setContentDescription(s);
            }else if (statusInt==2){
                status ="拒绝";
                //add
                String shipOrders = (String)data.get(position).get("task_id");
//                Log.e("orderNos1",orderNos.toString());
                String s = shipOrders +"";
//                s = s.replace("\"", "");
//                s = s.replace("[", "");
//                s = s.replace("]", "");
                zujian.Deliver.setContentDescription(s);
                zujian.task_id.setText(s);
                zujian.box.setContentDescription(s);
            }else if (statusInt==3){
                status ="配送中";
                //add
                String shipOrders = (String)data.get(position).get("task_id");
//                Log.e("orderNos1",orderNos.toString());
                String s = shipOrders +"";
//                s = s.replace("\"", "");
//                s = s.replace("[", "");
//                s = s.replace("]", "");
                zujian.Deliver.setContentDescription(s);
                zujian.task_id.setText(s);
                zujian.box.setContentDescription(s);
            }else if (statusInt==4){
                status ="配送完成";
                //add
                String shipOrders = (String)data.get(position).get("task_id");
//                Log.e("orderNos1",orderNos.toString());
                String s = shipOrders +"";
//                s = s.replace("\"", "");
//                s = s.replace("[", "");
//                s = s.replace("]", "");
                zujian.Deliver.setContentDescription(s);
                zujian.task_id.setText(s);
                zujian.box.setContentDescription(s);
            }else {
                status ="未知";
                //add
                String shipOrders = (String)data.get(position).get("task_id");
//                Log.e("orderNos1",orderNos.toString());
                String s = shipOrders +"";
//                s = s.replace("\"", "");
//                s = s.replace("[", "");
//                s = s.replace("]", "");
                zujian.Deliver.setContentDescription(s);
                zujian.task_id.setText(s);
                zujian.box.setContentDescription(s);
            }
        }
        zujian.status.setText(status);
        return convertView;
    }

//    public static int getResId(String variableName, Class<?> c) {
//        try {
//            Field idField = c.getDeclaredField(variableName);
//            return idField.getInt(idField);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 1;
//        }
//    }
}
