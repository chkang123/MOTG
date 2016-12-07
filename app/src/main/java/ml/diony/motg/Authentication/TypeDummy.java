package ml.diony.motg.Authentication;

import android.os.Bundle;
import android.util.Log;

//Copyright 2017 YUOA.

public class TypeDummy extends Base {

    //DUMMY는 모든 로그인 여부 함수에 대해 true로 일관한다.

    public boolean procLogin() {

        Log.i(TAG + "_dm", "Login Processed.");

        return true;

    }

    public boolean isLogined() {

        return true;

    }

    @Override
    public void onCreate(Bundle s) {

        super.onCreate(s);

    }

}
