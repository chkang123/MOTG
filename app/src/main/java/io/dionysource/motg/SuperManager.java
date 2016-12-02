package io.dionysource.motg.usrclss;

import java.util.ArrayList;

/**
 * Created by Admin on 2016. 12. 2..
 */

public class SuperManager {

    ArrayList<Evaluation> eval_list;
    ArrayList<Restaurant> rstrnt_list;
    ArrayList<Food> food_list;
    Users usr;
    Flavor flavor;
    ArrayList<Food_Type> food_type;

    public SuperManager()
    {
        eval_list = null;
        rstrnt_list = null;
        food_list = null;
        usr = null;
    }

    public void add_eval(Evaluation evaluation){

        eval_list.add(evaluation);
        usr.add_eval(evaluation);
        Restaurant temp = find_restaurant(evaluation.get_restaurant());
        temp.add_eval(evaluation);
    }

    public void add_food(Food food){

        find_restaurant(food.get_rstrnt()).add_food(food.get_idcode());
        food_list.add(food);
    }

    public void add_rstrnt(Restaurant rstrnt){
        rstrnt_list.add(rstrnt);
    }

    public Restaurant find_restaurant(String idcode)
    {
        boolean flag = false;
        int size = rstrnt_list.size();
        Restaurant temp = new Restaurant();
        for(int i =0; i<size; i++)
        {
            if(rstrnt_list.get(i).get_idcode() == idcode)
            {
                temp = rstrnt_list.get(i);
                flag = true;
                break;
            }
        }
        if(flag)
        {
            return temp;
        }
        else
        {
            return null;
        }


    }
    public Food find_food(String idcode)
    {
        boolean flag = false;
        int size = food_list.size();
        Food temp = new Food();
        for(int i =0; i<size; i++)
        {
            if(food_list.get(i).get_idcode() == idcode)
            {
                temp = food_list.get(i);
                flag = true;
                break;
            }
        }
        if(flag)
        {
            return temp;
        }
        else
        {
            return null;
        }


    }
    public Evaluation find_eval(String idcode)
    {
        boolean flag = false;
        int size = eval_list.size();
        Evaluation temp = new Evaluation();
        for(int i =0; i<size; i++)
        {
            if(eval_list.get(i).get_idcode() == idcode)
            {
                temp = eval_list.get(i);
                flag = true;
                break;
            }
        }
        if(flag)
        {
            return temp;
        }
        else
        {
            return null;
        }


    }

}
