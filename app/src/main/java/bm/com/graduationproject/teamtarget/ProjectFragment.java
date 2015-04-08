package bm.com.graduationproject.teamtarget;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by bm on 2015/4/6.
 */
public class ProjectFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
             View rootView=inflater.inflate(R.layout.project,null);

        ListView commonList=(ListView)rootView.findViewById(R.id.common_project);
        ListView participatedList=(ListView)rootView.findViewById(R.id.participated_project);

        String[] arr1={"aaa","bbb","ccc"};
        ArrayAdapter<String> adapter1= new ArrayAdapter<String>
                (rootView.getContext(),R.layout.array_project_item,arr1);
       commonList.setAdapter(adapter1);


        String[] arr2={"ddd","eee","fff"};

        ArrayAdapter<String> adapter2= new ArrayAdapter<String>
                (rootView.getContext(),R.layout.array_project_item,arr2);
        participatedList.setAdapter(adapter2);

        return rootView;
    }
}
