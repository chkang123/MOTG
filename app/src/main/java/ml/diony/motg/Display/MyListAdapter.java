package ml.diony.motg.Display;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ml.diony.motg.Communication.Interaction;
import ml.diony.motg.R;

/**
 * Created by KangChanghoon on 2016. 12. 3..
 */

//어떠한 항목의 해당하는 식당들의 리스트를 출력하고자 할때
//이를 특정한 형식으로 내보내기 위해 사용하는 클래스이다.
public class MyListAdapter extends BaseAdapter {
    Context maincon;
    LayoutInflater inflater;
    ArrayList<ResList> arSrc;
    int layout;

    //Class의 Constructor
    public MyListAdapter(Context context, int alayout, ArrayList<ResList> aarSrc) {
        maincon = context;
        arSrc = aarSrc;
        layout = alayout;
        //ListView에서 사용한 View를 정의한 xml 를 읽어오기 위해
        //LayoutInfalater 객체를 생성
        inflater = LayoutInflater.from(maincon);
    }

    public int getCount() {
        return arSrc.size();
    }

    public Object getItem(int position) {
        return arSrc.get(position).ResName;
    }

    public long getItemId(int position) {
        return position;
    }

    // 각 항목의 뷰 생성
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        TextView txt = (TextView) convertView.findViewById(R.id.res_name);
        txt.setText(arSrc.get(pos).ResName);

        //각 식당 항목별로 옆의 버튼을 눌렀을 때 올바른 식당의 정보화면을 나타내기 위해
        //아래와 같이 버튼을 설정해준다.
        Button btn = (Button) convertView.findViewById(R.id.open_resinfo);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(maincon, ResInfoActivity.class);
                intent.putExtra("name", arSrc.get(pos).ResName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                (new Interaction((Activity) maincon, maincon)).getSpecified("ALL", "NAME", arSrc.get(pos).ResName, intent);
            }
        });
        return convertView;
    }
}
