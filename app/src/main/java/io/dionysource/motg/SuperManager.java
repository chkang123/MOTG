package io.dionysource.motg.usrclss;

import java.text.ParseException;
import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class SuperManager {

    public class EvaluationPacket {
        int eval_version;
        ArrayList<Evaluation> eval_list;
        public EvaluationPacket()
        {
        }
        public void add_version()
        {
            eval_version++;
        }
        public void set_version(int version)
        {
            eval_version=version;
        }
        public int size()
        {
            return eval_list.size();
        }
        public void add(Evaluation evaluation)
        {
            eval_list.add(evaluation);
        }
        public Evaluation get(int i)
        {
            return eval_list.get(i);
        }
    }
    EvaluationPacket eval_list;
    ArrayList<Restaurant> rstrnt_list;
    ArrayList<Food> food_list;
    Users usr;
    Gson gson;

    public SuperManager()
    {
        eval_list = new EvaluationPacket();
        rstrnt_list = null;
        food_list = null;
        usr = null;
        gson = new GsonBuilder().create();
    }

    public void add_eval(Evaluation evaluation){

        eval_list.add(evaluation);
        usr.add_eval(evaluation);
        Restaurant temp = find_restaurant(evaluation.get_restaurant());
        Food ft = find_food(evaluation.get_food());
        temp.add_eval(evaluation);
        ft.add_eval(evaluation);
        eval_list.add_version();

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

    public void evaltoJSON()
    {
        String json = gson.toJson(eval_list);
        try{
            FileWriter file = new FileWriter("./EvalList.json")
            file.write(json);
            file.flush();
            file.close();

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void Jsontoeval()
    {
        try {
            JsonReader reader = new JsonReader(new FileReader("./EvalList.json"));
            eval_list = gson.fromJson(reader, EvaluationPacket.class);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

}
