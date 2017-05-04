package com.example.wolf.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wolf.myapp.R;
import com.example.wolf.myapp.model.Item;

import java.util.ArrayList;

/**
 * Created by Wolf on 5/2/2017.
 */

public class GridViewAdapter extends BaseAdapter {
    private ArrayList<Item> myList;
    private Context context;
    private LayoutInflater inflater;

    public GridViewAdapter(ArrayList<Item> myList, Context context) {
        this.myList = myList;
        this.context = context;
        inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder= new ViewHolder();
            convertView=inflater.inflate(R.layout.grid_item,null);
            viewHolder.img=(ImageView)convertView.findViewById(R.id.imgItem);
            viewHolder.tvName=(TextView)convertView.findViewById(R.id.tvItemName);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
            viewHolder.tvName.setText(myList.get(position).getName());

        return convertView;
    }

    private class ViewHolder{
        ImageView img;
        TextView tvName;
    }
}
