package bm.com.graduationproject.teamtarget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bm.com.graduationproject.teamtarget.R;

/**
 * Created by bm on 2015/4/23.
 */
public class SingleChoiceListAdapter  extends BaseAdapter {

    private static LayoutInflater inflater;
    private static Context mContext;
    //can be set to be a object list
    private List<HashMap<String,Object>> dataList;
    private int itemLayout;

    private String[] strKeys;
    private int selectedPosition=-1;

    public void setDataList(List<HashMap<String, Object>> dataList) {
        this.dataList = dataList;
    }

    public List<HashMap<String, Object>> getDataList() {
        return dataList;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public SingleChoiceListAdapter(Context context, List<HashMap<String,Object>> data,int itemLayout,
                                   String[] strKeys){

        mContext=context;
        inflater= LayoutInflater.from(context);
        dataList=data;
        this.strKeys=strKeys;

        this.itemLayout=itemLayout;




    }

    public SingleChoiceListAdapter(Context context, int itemLayout,
                                   String[] strKeys){

        mContext=context;
        inflater= LayoutInflater.from(context);
        this.strKeys=strKeys;

        this.itemLayout=itemLayout;
        selectedPosition=-1;

    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {


        convertView = inflater.inflate(itemLayout,null);

        ImageView icon=(ImageView)convertView.findViewById(R.id.array_dialog_selection_icon);
        icon.setImageResource(((Integer)dataList.get(position).get(strKeys[0])).intValue());

        TextView selection=(TextView)convertView.findViewById(R.id.array_dialog_selection);
        selection.setText((String)dataList.get(position).get(strKeys[1]));

        ImageView tick=(ImageView)convertView.findViewById(R.id.array_dialog_tick);

//        if(dataList.get(position).get(strKeys[3]).equals(true)){
//
//            selectedPosition=position;
//            tick.setImageResource(R.drawable.new_item_tick);
//        }

        if(selectedPosition==position){
            tick.setImageResource(R.drawable.new_item_tick);
        }


        return convertView;

    }
}
