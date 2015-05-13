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
import bm.com.graduationproject.teamtarget.model.Comment;

/**
 * Created by bm on 2015/5/12.
 */
public class CommentListAdapter extends BaseAdapter {


    private List<Comment> comments;
    private static LayoutInflater inflater;
    private static Context mContext;


    public CommentListAdapter(Context context,List<Comment> comments){
        mContext=context;
        inflater= LayoutInflater.from(context);
        this.comments=comments;

    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {


        ViewHolder holder=new ViewHolder();

        if(convertView==null){

            convertView=inflater.inflate(R.layout.array_comment,null);

            holder.userIcon=(ImageView)convertView.findViewById(R.id.comment_user);
            holder.userName=(TextView)convertView.findViewById(R.id.comment_name);
            holder.date=(TextView)convertView.findViewById(R.id.comment_date);
            holder.content=(TextView)convertView.findViewById(R.id.comment_content);

            convertView.setTag(holder);

        }else{
            holder=(ViewHolder)convertView.getTag();
        }


        holder.userIcon.setImageResource(R.drawable.ic_user);
        holder.userName.setText(String.valueOf(comments.get(position).getUserId()));
        holder.date.setText(comments.get(position).getDate());
        holder.content.setText(comments.get(position).getContent());

        return convertView;
    }

    private  class ViewHolder{
        ImageView userIcon;
        TextView userName;
        TextView date;
        TextView content;


    }


}
