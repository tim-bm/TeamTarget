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
import bm.com.graduationproject.teamtarget.model.Project;

/**
 * Created by bm on 2015/5/21.
 */
public class ProjectListAdapter extends BaseAdapter {

    private List<Project> projects;

    private static LayoutInflater inflater;
    private static Context mContext;

    private int selected=-1;


    public ProjectListAdapter(Context context,List<Project> projects){

        mContext=context;
        inflater=LayoutInflater.from(context);
        this.projects=projects;

    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int i) {
        return projects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {


        convertView=inflater.inflate(R.layout.array_common_item,null);

        ImageView projectImage=(ImageView)convertView.findViewById(R.id.array_common_project_image);
        TextView projectTitle=(TextView)convertView.findViewById(R.id.array_common_project_name);
        ImageView tick=(ImageView)convertView.findViewById(R.id.array_common_tick);

        projectImage.setImageResource(R.drawable.project_item_computer);
        projectTitle.setText(projects.get(position).getName());

        if(selected==position){
            tick.setImageResource(R.drawable.new_item_tick);
        }

        return convertView;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
