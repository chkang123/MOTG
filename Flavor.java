package io.dionysource.motg.usrclss;
import java.util.ArrayList;

/**
 * Created by Admin on 2016. 11. 19..
 */

public class Flavor {
 /*
  Bitter // 쓴 맛
  ,Spicy // 매운 맛
  ,Sour // 신 맛
  ,Sweet // 단 맛
  ,Salty // 짠 맛
  ,Gamchill //감칠맛
  ,Greasy // 기름진 맛
  ,Fishy // 해산물 느낌
 */
 ArrayList<String> flavorlist;

    public Flavor()
    {
        flavorlist=null;
    }

    public void add_flavor(String flavor)
    {
        int size = flavorlist.size();
        flavorlist.add(size,flavor);
    }

    public int get_flavor(String flavor)
    {
        int idx = flavorlist.indexOf(flavor);
        return idx;
    }
}