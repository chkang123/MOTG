package io.dionysource.motg.usrclss;
import java.util.ArrayList;
/**
 * Created by Admin on 2016. 11. 26..
 */

public class Mood {

    //분위기에 대한 클래스
    //시끌벅적하다든지 이런 것도 포함의 대상이 될 수 있을 것
    ArrayList<String> moodlist;

    public Mood()
    {
        moodlist=null;
    }
    public void add_mood(String mood)
    {
        int size = moodlist.size();
        moodlist.add(size, mood);
    }
}
