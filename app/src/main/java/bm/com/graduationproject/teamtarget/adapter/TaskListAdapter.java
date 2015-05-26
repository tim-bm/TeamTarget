package bm.com.graduationproject.teamtarget.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bm.com.graduationproject.teamtarget.R;
import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.CommentDBService;
import bm.com.graduationproject.teamtarget.model.Task;

/**
 * Created by bm on 2015/5/4.
 */
public class TaskListAdapter extends BaseAdapter {


    private static LayoutInflater inflater;
    private static Context mContext;


    private List<Task> tasks;

    public TaskListAdapter(Context context,List<Task> tasks){

        mContext=context;
        inflater= LayoutInflater.from(context);
        this.tasks=tasks;

    }

    public TaskListAdapter(Context context){

        mContext=context;
        inflater= LayoutInflater.from(context);


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

            convertView=inflater.inflate(R.layout.array_task_list,null);
            holder.title=(TextView)convertView.findViewById(R.id.task_title_in_list);
            holder.checkList=(TextView)convertView.findViewById(R.id.task_checklist_in_list);
            holder.comment=(TextView)convertView.findViewById(R.id.task_comment_in_list);
            holder.id=(TextView)convertView.findViewById(R.id.task_id);
            convertView.setTag(holder);

        }else{
            holder=(ViewHolder)convertView.getTag();
        }


        //set information of the view

        holder.title.setText(tasks.get(position).getName());
        if(tasks.get(position).getComment().equals("1")){
            holder.title.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.id.setText(String.valueOf(tasks.get(position).getId()));

        //get numbers of comment inside adapter
        CommentDBService commentDBService=new CommentDBService(DBManager.getInstance(convertView.getContext()));
        int counts=commentDBService.getCommentCountByTaskId(tasks.get(position).getId());

        holder.comment.setText(String.valueOf(counts));

        return convertView;





    }

    private  class ViewHolder{
        TextView title;
        TextView checkList;
        TextView comment;
        TextView id;

    }
}
