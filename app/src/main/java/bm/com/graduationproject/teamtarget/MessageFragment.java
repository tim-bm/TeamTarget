package bm.com.graduationproject.teamtarget;

//import android.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by bm on 2015/4/6.
 */
public class MessageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView= inflater.inflate(R.layout.message,null);

        Button getMessage=(Button)rootView.findViewById(R.id.getMessage);




            return rootView;
    }
}
