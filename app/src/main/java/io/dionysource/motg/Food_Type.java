package io.dionysource.motg.usrclss;
import java.util.ArrayList;
/**
 * Created by Admin on 2016. 11. 20..
 */

public class Food_Type {

    String type;
    ArrayList<Food_subtype> food_sub;
    private Food_Type()
    {

    }


    public Food_Type(String type)
    {
        this.type = type;
        food_sub = null;
    }
    public void add_subtype(String subtype) {
        int size = food_sub.size();
        Food_subtype sub = new Food_subtype(subtype);
        food_sub.add(size, sub);
    }
    public void add_subsubtype(String subtype, String subsubtype)
    {
        int idx = get_subtype(subtype);
        food_sub.get(idx).add_subsubtype(subsubtype);

    }
    public String get_type()
    {
        return type;
    }
    public int get_subtype(String subtype)
    {
        int idx = 0;
        for(int i = 0 ; i<food_sub.size(); i++)
        {
            if(food_sub.get(i).get_subtype() == subtype)
            {
                idx = i;
                break;
            }
        }
        return idx;
    }
}
class Food_subtype
{
    String subtype;
    ArrayList<String> sub_sub;

    private Food_subtype()
    {

    }

    Food_subtype(String subtype)
    {
        this.subtype = subtype;
        sub_sub = null;
    }
    void add_subsubtype(String subsubtype) {
        int size = sub_sub.size();
        sub_sub.add(size, subsubtype);
    }
    public String get_subtype()
    {
        return subtype;
    }
}