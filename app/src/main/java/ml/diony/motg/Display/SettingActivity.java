package ml.diony.motg.Display;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;

import ml.diony.motg.Communication.Sync;
import ml.diony.motg.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button X = (Button) findViewById(R.id.button_set);
        final EditText ET1 = (EditText) findViewById(R.id.editText1);
        final EditText ET2 = (EditText) findViewById(R.id.editText5);
        final EditText ET3 = (EditText) findViewById(R.id.editText4);
        final EditText ET4 = (EditText) findViewById(R.id.editText3);
        final EditText ET5 = (EditText) findViewById(R.id.editText2);

        JSONArray A = (new Sync()).getU_S();

        if(A != null) {

            Log.i("SETTING", "RECORD EXISTS!");

            try {

                ET1.setText((String) A.get(0));
                ET2.setText((String) A.get(1));
                ET3.setText((String) A.get(2));
                ET4.setText((String) A.get(3));
                ET5.setText((String) A.get(4));


            } catch(Exception E) {}

        }

        X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray JA = new JSONArray();

                JA.put(ET1.getText());
                JA.put(ET2.getText());
                JA.put(ET3.getText());
                JA.put(ET4.getText());
                JA.put(ET5.getText());

                (new Sync()).saveU_S(JA);

                (new Sync()).uploadUS(JA);

                finish();

            }
        });

    }
}
