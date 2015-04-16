package bm.com.graduationproject.teamtarget;

//import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bm on 2015/4/6.
 */
public class ProjectFragment extends Fragment {

    private ArrayList<HashMap<String,Object>> commonList;
    private ArrayList<HashMap<String,Object>> participatedList;
    private SimpleAdapter commonListAdapter;
    private SimpleAdapter participatedListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
             View rootView=inflater.inflate(R.layout.project,null);


        ListView commonListView=(ListView)rootView.findViewById(R.id.common_project);
        ListView participatedListView=(ListView)rootView.findViewById(R.id.participated_project);

        commonList=new ArrayList<HashMap<String, Object>>(3);
        participatedList=new ArrayList<HashMap<String, Object>>(3);

        for(int i=0;i<3;i++){

            HashMap<String,Object> map=new HashMap<String, Object>();
            map.put("project_item_title","这是常用项目"+i);
            map.put("project_item_image",R.drawable.project_item_computer);
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
                        new String[]{"project_item_title","project_item_image"},
                        new int[]{R.id.project_item_title,R.id.project_item_image});


        participatedListAdapter=new SimpleAdapter
                (rootView.getContext(),participatedList,R.layout.array_project_item,
                        new String[]{"project_item_title","project_item_image"},
                        new int[]{R.id.project_item_title,R.id.project_item_image});


        commonListView.setAdapter(commonListAdapter);
        participatedListView.setAdapter(participatedListAdapter);
//        ListView commonList=(ListView)rootView.findViewById(R.id.common_project);
//        ListView participatedList=(ListView)rootView.findViewById(R.id.participated_project);
//
//
//        String[] arr1={"aaa","bbb","ccc"};
//        ArrayAdapter<String> adapter1= new ArrayAdapter<String>
//                (rootView.getContext(),R.layout.array_project_item,arr1);
//       commonList.setAdapter(adapter1);
//
//        String[] arr2={"ddd","eee","fff"};
//
//        ArrayAdapter<String> adapter2= new ArrayAdapter<String>
//                (rootView.getContext(),R.layout.array_project_item,arr2);
//        participatedList.setAdapter(adapter2);

        return rootView;
    }

    private  void  initListView(View view,int resourceId){



    }


}
