package ml.diony.motg.Display;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import ml.diony.motg.Communication.Sync;
import ml.diony.motg.R;

//사용자가 방문기록을 메뉴에 대한 평점, 식당 서비스에 대한 평점,
//방문날짜와 함께 기록할 수 있는 화면을 제공하는 Class
public class RateActivity extends AppCompatActivity {

    RatingBar rating;
    RatingBar rating2;
    Context CONTEXT = this;
    Activity ACTIVITY = this;
    TextView tv2;
    EditText tvx;
    TextView tv4;
    String rname, mname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        //어떤 식당의 어느 메뉴에 대한 평가인지 알기 위해 식당이름과 메뉴이름을 받아온다.
        rname = getIntent().getExtras().getString("RES-NAME");
        mname = getIntent().getExtras().getString("MENU-NAME");

        rating = (RatingBar) findViewById(R.id.ratingBar);
        rating2 = (RatingBar) findViewById(R.id.ratingBar2);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv4 = (TextView) findViewById(R.id.tv4);
        tvx = (EditText) findViewById(R.id.date_text);

        //별점의 기본 단위가 0.5칸이 되도록 설정한다.
        rating.setStepSize((float) 0.5);
        //초기 화면에 출력되는 별점이 0이도록 설정한다.
        rating.setRating((float) 0.0);
        //사용자가 별점을 수정할 수 있도록 설정한다.
        rating.setIsIndicator(false);
        rating2.setStepSize((float) 0.5);
        rating2.setRating((float) 0.0);
        rating2.setIsIndicator(false);

        //별점 칸의 수가 바뀔때마다 현재 칸 수에 해당하는 평점은 얼마인지 보여준다.
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                tv2.setText("평점: " + v);
            }
        });

        rating2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                tv4.setText("평점: " + v);
            }
        });

        //모든 정보를 입력하고 등록하기 버튼을 누른 경우 입력받은 데이터를 저장하면서
        //간단한 성공 혹은 에러 메시지를 띄워준다.
        Button button = (Button) findViewById(R.id.button_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tvx.getText().length() != 8) {


                    Toast.makeText(CONTEXT, "날짜를 제대로 입력해주세요.", Toast.LENGTH_SHORT).show();

                    return;
                }

                Sync SY = (new Sync(CONTEXT, ACTIVITY));
                JSONObject JS = new JSONObject();

                try {

                    Log.i("TETTT", "으음..: " + JS.toString());

                    JS.put("REV", SY.getREVINFO() + 1);

                    Log.i("TETTT", "으음..: " + JS.toString());
                    JS.put("DELI", rating.getRating());

                    Log.i("TETTT", "으음..: " + JS.toString());
                    JS.put("SERV", rating2.getRating());

                    Log.i("TETTT", "으음..: " + JS.toString());
                    JS.put("DATE", tvx.getText());

                    JS.put("RES", rname);
                    JS.put("MENU", mname);

                    Log.i("TETTT", "으음..: " + JS.toString());

                } catch (Exception E) {}
                SY.saveHISTORY(SY.getREVINFO() + 1, JS);
                SY.saveREVINFO(SY.getREVINFO() + 1);

                SY.historySync();

                Toast.makeText(CONTEXT, "저장되었습니다.", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }
}
