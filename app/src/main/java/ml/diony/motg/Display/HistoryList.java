package ml.diony.motg.Display;

import java.util.ArrayList;

/**
 * Created by KangChanghoon on 2016. 12. 3..
 */

//방문한 기록을 불러와 저장하고 출력하기 위해 잠시 데이터를 저장하는 Class이다.
//방문한 날짜, 메뉴와 가게이름을 저장하는 멤버변수를 갖고 있다.
//가게이름과 날짜는 하나의 String데이터로 child에 저장된다.
public class HistoryList {
    public ArrayList<String> child;
    public int year, month, day;

    public HistoryList(String X) {

        year = Integer.parseInt(X.substring(0, 4));
        month = Integer.parseInt(X.substring(4, 6));
        day = Integer.parseInt(X.substring(6, 8));
        child = new ArrayList<String>();

    }
}
