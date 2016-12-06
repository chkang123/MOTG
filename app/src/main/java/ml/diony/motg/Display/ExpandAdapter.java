package ml.diony.motg.Display;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ml.diony.motg.R;
/*
 * Created by KangChanghoon on 2016. 11. 26..
 */

//불러온 식당의 메뉴정보를 알맞은 형태로 ExpandableListView에 배치해주는 Class
public class ExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int groupLayout = 0;
    private int chlidLayout = 0;
    private ArrayList<MenuGroup> DataList;
    private LayoutInflater myinf = null;
    private String rname = "";

    //Class의 constructor
    public ExpandAdapter(Context context, int groupLay, int chlidLay, ArrayList<MenuGroup> DataList, String rname) {
        this.DataList = DataList;
        this.rname = rname;
        this.groupLayout = groupLay;
        this.chlidLayout = chlidLay;
        this.context = context;
        this.myinf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //ExpandableListView에서 Parent-row에 해당하는 부분을 설정해주는 메소드
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = myinf.inflate(this.groupLayout, parent, false);
        }
        //메뉴의 이름을 TextView의 Text로 set해준다.
        TextView groupName = (TextView) convertView.findViewById(R.id.groupName);
        groupName.setText(DataList.get(groupPosition).groupName);
        return convertView;
    }

    //ExpandableListView에서 Child-row에 해당하는 부분을 설정해주는 메소드
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = myinf.inflate(this.chlidLayout, parent, false);
        }
        //메뉴의 가격을 TextView의 Text로 set해준다.
        TextView childName = (TextView) convertView.findViewById(R.id.childName);
        childName.setText(DataList.get(groupPosition).child.get(childPosition));

        //평가하기 버튼을 누르면 평가하는 엑티비티인 RateActivity로 넘어가게한다.
        //이때 식당의 이름과 해당 메뉴의 이름도 같이 전달하여 평가를 하였을 때
        //평가가 해당 어떠한 식당과 메뉴에 대한 것인지 판단하여 저장한다.
        Button button = (Button) convertView.findViewById(R.id.button_rating);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RateActivity.class);
                intent.putExtra("RES-NAME", rname);
                intent.putExtra("MENU-NAME", DataList.get(groupPosition).groupName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition).child.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition).child.size();
    }

    @Override
    public MenuGroup getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return DataList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return DataList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }
}
