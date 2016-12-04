package ml.diony.motg.Display;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import ml.diony.motg.R;

public class KorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btn_kor_res1 = (Button) findViewById(R.id.kor_res1);
        btn_kor_res1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rid = 1111;
                Intent intent = new Intent(KorActivity.this, ResInfoActivity.class);
                intent.putExtra("rid", rid);
                startActivity(intent);
            }
        });

    }
}
