package bm.com.graduationproject.teamtarget;

//import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.ProjectDBService;
import bm.com.graduationproject.teamtarget.model.Project;

/**
 * Created by bm on 2015/4/6.
 */
public class ProjectFragment extends Fragment {

    private ArrayList<HashMap<String,Object>> commonList;
    private ArrayList<HashMap<String,Object>> participatedList;
    private SimpleAdapter commonListAdapter;
    private SimpleAdapter participatedListAdapter;

    //rootView
    private View rootView;

    //data
    private DBManager dbManager;
    private ProjectDBService projectDBService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
              rootView=inflater.inflate(R.layout.project,null);


        ListView commonListView=(ListView)rootView.findViewById(R.id.common_project);
        ListView participatedListView=(ListView)rootView.findViewById(R.id.participated_project);

        commonList=new ArrayList<HashMap<String, Object>>(3);
        participatedList=new ArrayList<HashMap<String, Object>>(3);

        //initial database connection
        projectDBService=new ProjectDBService(DBManager.getInstance(rootView.getContext()));
        List<Project>projectList=projectDBService.getAllProject();

        for(int i=0;i<projectList.size();i++){

            HashMap<String,Object> map=new HashMap<String, Object>();
            map.put("project_item_title",projectList.get(i).getName());
            map.put("project_item_image",R.drawable.project_item_computer);
            map.put("project_item_id",projectList.get(i).getId());
            commonList.add(map);
        }

        for(int i=0;i<2;i++){

            HashMap<String,Object> map=new HashMap<String, Object>();
            map.put("project_item_title","这是参与项目"+i);
            map.put("project_item_image",R.drawable.project_item_document);
            participatedList.add(map);
        }

        commonListAdapter=new SimpleAdapter
                (rootView.getContext(),commonList,R.layout.array_project_item,
                        new String[]{"project_item_title","project_item_image","project_item_id"},
                        new int[]{R.id.project_item_title,R.id.project_item_image,R.id.project_item_id});


        participatedListAdapter=new SimpleAdapter
                (rootView.getContext(),participatedList,R.layout.array_project_item,
                        new String[]{"project_item_title","project_item_image"},
                        new int[]{R.id.project_item_title,R.id.project_item_image});


        commonListView.setAdapter(commonListAdapter);
        participatedListView.setAdapter(participatedListAdapter);

        //set on item click listener

        commonListView.setOnItemClickListener(new ProjectItemClickListener());


        return rootView;
    }

    class ProjectItemClickListener implements AdapterView.OnItemClickListener{


        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            TextView idTextView=(TextView)view.findViewById(R.id.project_item_id);
            TextView titleTextView=(TextView)view.findViewById(R.id.project_item_title);

            String projectId=idTextView.getText().toString();
            String projectName=titleTextView.getText().toString();

            Intent intent =new Intent(rootView.getContext(),TaskListActivity.class);
            intent.putExtra("projectId",Integer.parseInt(projectId));
            intent.putExtra("projectName",projectName);

            startActivity(intent);



        }
    }




}
