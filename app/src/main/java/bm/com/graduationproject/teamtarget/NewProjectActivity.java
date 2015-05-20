package bm.com.graduationproject.teamtarget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bm.com.graduationproject.teamtarget.adapter.SingleChoiceListAdapter;
import bm.com.graduationproject.teamtarget.dbHelper.DBManager;
import bm.com.graduationproject.teamtarget.dbService.ProjectDBService;
import bm.com.graduationproject.teamtarget.model.Project;
import bm.com.graduationproject.teamtarget.utils.AppContext;


public class NewProjectActivity extends Activity {

    private ArrayList<HashMap<String,Object>> newItemList;
    private SimpleAdapter newItemListAdapter;

    private Project newProject;

    private Dialog choiceDialog;
    //costume adapter for single choice dialog
    private SingleChoiceListAdapter newItemDialogAdapter;

    //change view

    private TextView viewChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        newProject=new Project();
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));


        ListView newItemListView=(ListView)findViewById(R.id.new_list_item_selected);

        String[] strKeys={"icon","hint","selected"};
        int[] ids={R.id.array_new_icon,R.id.array_hint,R.id.array_selected};
        newItemListAdapter=new SimpleAdapter(this,getList(),R.layout.array_new_item,strKeys,ids);

        newItemListView.setAdapter(newItemListAdapter);


        newItemListView.setOnItemClickListener(new ItemClickListener());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;

        }

        if(id==R.id.new_project_tick){


            ProjectDBService projectDBService=new ProjectDBService(DBManager.getInstance(this));
            setNewProject();
            projectDBService.addProject(newProject);

            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

           //animation
        if(keyCode==KeyEvent.KEYCODE_BACK){

            this.finish();
            overridePendingTransition(R.anim.stand_still, R.anim.pull_down_out);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private List<HashMap<String,Object>> getList(){

        ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String, Object>>(2);
        HashMap<String,Object> map=null;

//        for(int i=0;i<2;i++){
            map = new HashMap<String, Object>();
            map.put("icon",R.drawable.icon_bruch);
            map.put("hint",this.getResources().getString(R.string.owner));
            map.put("selected","个人项目");


            list.add(map);

            map= new HashMap<String, Object>();
            map.put("icon",R.drawable.ic_lock);
            map.put("hint",this.getResources().getString(R.string.publicity));
            map.put("selected","私有项目");


            list.add(map);

//        }

        return list;
    }

    class ItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            //test
         /*
              TextView t =(TextView)view.findViewById(R.id.array_selected);
               t.setText("点击了");*/


           // SimpleAdapter adapter=(SimpleAdapter)adapterView.getAdapter();

            TextView title=(TextView)view.findViewById(R.id.array_hint);
            TextView selection=(TextView)view.findViewById(R.id.array_selected);


          //  SimpleAdapter builderListSimpleAdapter;


            String[] strKeys={"icon","selection","tick","ifSelected"};

            if(NewProjectActivity.this.getResources().getString(R.string.owner).equals(title.getText())){

               newItemDialogAdapter=new SingleChoiceListAdapter(NewProjectActivity.this,
                       getOwnerList(selection.getText().toString()),R.layout.arrray_dialog_new_item,strKeys);

                //set default selection
                defaultSelectionForOwnerList(selection.getText().toString());

                NewProjectActivity.this.showNewDialog(newItemDialogAdapter,title.getText().toString());

            }else if(NewProjectActivity.this.getResources().getString(R.string.publicity).equals(title.getText())){


                newItemDialogAdapter=new SingleChoiceListAdapter(NewProjectActivity.this,getPublicityList(selection.getText().toString()),
                        R.layout.arrray_dialog_new_item,strKeys);
               // newItemDialogAdapter.setDataList(getPublicityList(selection.getText().toString()));
                //newItemDialogAdapter.notifyDataSetChanged();

                defaultSelectionForPublicityList(selection.getText().toString());
                NewProjectActivity.this.showNewDialog(newItemDialogAdapter,title.getText().toString());

            }

                viewChange=(TextView)view.findViewById(R.id.array_selected);


        }

        private List<HashMap<String,Object>> getOwnerList(String selection){

            ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String, Object>>(2);
            HashMap<String,Object> map=null;


            map = new HashMap<String, Object>();
            map.put("icon", R.drawable.new_item_person);
            map.put("selection",NewProjectActivity.this.getResources().getString(R.string.personal_project));
//            if(selection.equals(NewProjectActivity.this.getResources().getString(R.string.personal_project)))
            map.put("tick", R.drawable.new_item_tick);

            list.add(map);


            return list;
        }

        private List<HashMap<String,Object>> getPublicityList(String selection){

            ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String, Object>>(2);

            HashMap<String,Object> map=null;

            map = new HashMap<String, Object>();
            //two item for publicity
            map.put("icon",R.drawable.new_item_person);
            map.put("selection", NewProjectActivity.this.getResources().getString(R.string.private_project));


            list.add(map);

            map = new HashMap<String, Object>();

            map.put("icon",R.drawable.new_item_person);
            map.put("selection", NewProjectActivity.this.getResources().getString(R.string.public_project));


            list.add(map);

            return list;
        }


        private void defaultSelectionForPublicityList(String selection){

            if(selection.equals(NewProjectActivity.this.getResources().getString(R.string.private_project))){
                newItemDialogAdapter.setSelectedPosition(0);
            }else if(selection.equals(NewProjectActivity.this.getResources().getString(R.string.public_project))){
                newItemDialogAdapter.setSelectedPosition(1);
            }else{

            }

           /* if(selection.equals(NewProjectActivity.this.getResources().getString(R.string.public_project))){
                //map.put("ifSelected",true);
                newItemDialogAdapter.setSelectedPosition(1);
            }else{
                //map.put("ifSelected",false);
            }*/


        }
        private void defaultSelectionForOwnerList(String selection){
            if(selection.equals(NewProjectActivity.this.getResources().getString(R.string.personal_project))){
                newItemDialogAdapter.setSelectedPosition(0);
            }
        }
    }


    private void showNewDialog(SingleChoiceListAdapter listAdapter,String title){

        LinearLayout dialogLayout= (LinearLayout)getLayoutInflater().inflate(R.layout.dialog_new_item,null);

        ListView dialogListView=(ListView)dialogLayout.findViewById(R.id.dialog_List);

        //set list
        dialogListView.setAdapter(listAdapter);

        //set list item listener
        dialogListView.setOnItemClickListener(new DialogListItemClickListener());

        //set cancel textView listener
        TextView cancel=(TextView)dialogLayout.findViewById(R.id.dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceDialog.dismiss();
            }
        });

        //set title
        TextView titleTextView=(TextView)dialogLayout.findViewById(R.id.dialog_title);
        titleTextView.setText(title);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(dialogLayout);


//        builder.create();
//        builder.show();

        choiceDialog= builder.create();
        choiceDialog.show();

    }

    class DialogListItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            newItemDialogAdapter.setSelectedPosition(i);
            newItemDialogAdapter.notifyDataSetChanged();


           TextView text= (TextView)view.findViewById(R.id.array_dialog_selection);
           String  userSelection=text.getText().toString();

           viewChange.setText(userSelection);
           choiceDialog.dismiss();

        }
    }

    private void setNewProject(){
        EditText editText=(EditText)findViewById(R.id.edit_new_project);
        newProject.setName(editText.getText().toString());

        //fixed data because only support for personal project
        newProject.setOwnership("个人项目");

        if(viewChange.getText().toString()!=null){
            newProject.setPublicity(viewChange.getText().toString());
        }else{
            newProject.setPublicity("私有项目");
        }


        AppContext appContext=AppContext.getInstance();
        newProject.setCreator(appContext.getUserId());

        newProject.setFinishStatus(0);
    }

}
