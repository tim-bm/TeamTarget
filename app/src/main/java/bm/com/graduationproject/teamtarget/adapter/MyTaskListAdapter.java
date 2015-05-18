package bm.com.graduationproject.teamtarget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


import java.util.Calendar;
import java.util.List;

import bm.com.graduationproject.teamtarget.R;
import bm.com.graduationproject.teamtarget.model.Task;

/**
 * Created by bm on 2015/5/14.
 */
public class MyTaskListAdapter extends BaseAdapter {


    private static LayoutInflater inflater;
    private static Context mContext;


    private List<Task> tasks;
    private int[] comments;
    private String[] projects;


    public MyTaskListAdapter(Context context,List<Task> tasks,int[] comments,String[] projects){
        mContext=context;
        inflater=LayoutInflater.from(context);
        this.tasks=tasks;
        this.comments=comments;
        this.projects=projects;

    }


    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int i) {
        return tasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder=new ViewHolder();

        if(convertView==null){

            convertView=inflater.inflate(R.layout.array_my_task_item,null);
            holder.title=(TextView)convertView.findViewById(R.id.my_task_title);
            holder.deadline=(TextView)convertView.findViewById(R.id.my_task_deadline);
            holder.comment=(TextView)convertView.findViewById(R.id.my_task_comment);
            holder.id=(TextView)convertView.findViewById(R.id.my_task_id);
            holder.project=(TextView)convertView.findViewById(R.id.my_task_project);
            convertView.setTag(holder);

        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.title.setText(tasks.get(position).getName());
        holder.project.setText(projects[position]);
        holder.id.setText(""+tasks.get(position).getId());


        if(tasks.get(position).getDeadline()!=null){
            holder.deadline.setText(tasks.get(position).getDeadline());
            if(ifOutOfDate(tasks.get(position).getDeadline())){

                holder.deadline.setBackgroundResource(R.drawable.text_view_radius_border);
            }else{

                holder.deadline.setBackgroundResource(R.drawable.text_view_radius_border_green);
            }

        }


        holder.deadline.setText(tasks.get(position).getDeadline());
        if(comments[position]!=0){
            holder.comment.setText(""+comments[position]);
        }else{
            holder.comment.setText("0");
        }



        return convertView;
    }

    private boolean ifOutOfDate(String deadline){

        String results[];
        results=deadline.split("-");

        int year=Integer.parseInt(results[0]);
        int month=Integer.parseInt(results[1]);
        int day=Integer.parseInt(results[2]);

        Calendar cal=Calendar.getInstance();
        if(year<=(cal.get(Calendar.YEAR))&&month<=(cal.get(Calendar.MONTH)+1)
                &&(day)<=(cal.get(Calendar.DAY_OF_MONTH))){

            return false;
        }else{
            return true;
        }

    }


    private  class ViewHolder{

        TextView title;
        TextView deadline;
        TextView comment;
        TextView project;
        TextView id;
    }

}
