package ml.diony.motg.Display;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONObject;

import ml.diony.motg.Communication.Sync;
import ml.diony.motg.R;

public class RateActivity extends AppCompatActivity {

    RatingBar rating;
    RatingBar rating2;
    Context CONTEXT = this;
    Activity ACTIVITY = this;
    TextView tv2;
    TextView tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rating = (RatingBar) findViewById(R.id.ratingBar);
        rating2 = (RatingBar) findViewById(R.id.ratingBar2);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv4 = (TextView) findViewById(R.id.tv4);

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

                Sync SY = (new Sync(CONTEXT, ACTIVITY));
                JSONObject JS = new JSONObject();

                try {

                    JS.put("REV", SY.getREVINFO() + 1);

                    JS.put("DATE", ((EditText) view.findViewById(R.id.date_text)).getText());
                    JS.put("DELI", ((RatingBar) view.findViewById(R.id.ratingBar)).getRating());
                    JS.put("SERV", ((RatingBar) view.findViewById(R.id.ratingBar2)).getRating());

                } catch (Exception E) {}
                SY.saveHISTORY(SY.getREVINFO() + 1, JS);
                SY.saveREVINFO(SY.getREVINFO() + 1);

                SY.historySync();
            }
        });

    }
}
