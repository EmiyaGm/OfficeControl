package com.example.OfficeControl.subfragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.OfficeControl.fragment.R;
import com.example.OfficeControl.vo.Gather;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by john on 6/13/2016.
 */
public class gatherAdapter extends BaseAdapter {
    private Context context;
    private List<Gather> data;

    class ViewHolder{
        TextView deviceTextView;
        TextView inTextView;
        TextView outTextView;
    }

    public gatherAdapter(Context context, List<Gather> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.gather_item,null);
            holder.deviceTextView = (TextView) convertView.findViewById(R.id.deviceTextView);
            holder.inTextView = (TextView) convertView.findViewById(R.id.inTextView);
            holder.outTextView = (TextView) convertView.findViewById(R.id.outTextView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.deviceTextView.setText(data.get(position).getName());
        holder.inTextView.setText(data.get(position).getInTemp());
        holder.outTextView.setText(data.get(position).getOutTemp());
        return convertView;
    }
}
