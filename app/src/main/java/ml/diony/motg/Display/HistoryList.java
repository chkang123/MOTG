package ml.diony.motg.Display;

import java.util.ArrayList;

/**
 * Created by KangChanghoon on 2016. 12. 3..
 */

public class HistoryList {
    public ArrayList<String> child;
    public int year, month, day;

    public HistoryList(int y, int m, int d) {
        year = y;
        month = m;
        day = d;
        child = new ArrayList<String>();
    }
    public HistoryList(String X) {

        year = Integer.parseInt(X.substring(0,4));
        month = Integer.parseInt(X.substring(4,6));
        day = Integer.parseInt(X.substring(6,8));
        child = new ArrayList<String>();

    }
}
