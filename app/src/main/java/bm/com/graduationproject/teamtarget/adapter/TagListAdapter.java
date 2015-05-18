package bm.com.graduationproject.teamtarget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bm.com.graduationproject.teamtarget.R;
import bm.com.graduationproject.teamtarget.utils.Tag;

/**
 * Created by bm on 2015/5/13.
 */
public class TagListAdapter extends BaseAdapter{


    private static LayoutInflater inflater;
    private static Context mContext;


    private List<Tag> tags;

    private int selectedPosition=-1;


    public TagListAdapter(Context context,List<Tag> tags){

        mContext=context;
        inflater=LayoutInflater.from(context);
        this.tags=tags;

    }


    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public Object getItem(int i) {
        return tags.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        /*ViewHolder holder=new ViewHolder();

        if(convertView==null){

            convertView=inflater.inflate(R.layout.array_task_tag_item,null);
            holder.color=(TextView)convertView.findViewById(R.id.dialog_tag_color);
            holder.name=(TextView)convertView.findViewById(R.id.dialog_tag_name);
            holder.tick=(ImageView)convertView.findViewById(R.id.dialog_tag_tick);

            convertView.setTag(holder);

        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.color.setBackgroundResource(tags.get(position).getResource());
        holder.name.setText(tags.get(position).getName());
        if(selectedPosition==position) {
            holder.tick.setImageResource(R.drawable.new_item_tick);
        }*/


        convertView=inflater.inflate(R.layout.array_task_tag_item,null);

        TextView color=(TextView)convertView.findViewById(R.id.dialog_tag_color);
        TextView name=(TextView)convertView.findViewById(R.id.dialog_tag_name);
        ImageView tick=(ImageView)convertView.findViewById(R.id.dialog_tag_tick);

        color.setBackgroundResource(tags.get(position).getResource());
        name.setText(tags.get(position).getName());


        if(selectedPosition==position) {
            tick.setImageResource(R.drawable.new_item_tick);
        }

        return convertView ;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    private class ViewHolder{
        TextView color;
        TextView name;
        ImageView tick;

    }
}
