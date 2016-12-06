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

        rname = getIntent().getExtras().getString("RES-NAME");
        mname = getIntent().getExtras().getString("MENU-NAME");

        rating = (RatingBar) findViewById(R.id.ratingBar);
        rating2 = (RatingBar) findViewById(R.id.ratingBar2);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv4 = (TextView) findViewById(R.id.tv4);
        tvx = (EditText) findViewById(R.id.date_text);

        rating.setStepSize((float) 0.5);
        rating.setRating((float) 0.0);
        rating.setIsIndicator(false);
        rating2.setStepSize((float) 0.5);
        rating2.setRating((float) 0.0);
        rating2.setIsIndicator(false);

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

        Button button = (Button) findViewById(R.id.button_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //데이터 저장
                //Toast 띄워서 저장 또는 오류 출력

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
