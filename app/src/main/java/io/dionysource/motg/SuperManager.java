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
            FileWriter file = new FileWriter("./EvalList.json");
            file.write(json);
            file.flush();
            file.close();

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void Jsontoeval() {
        try {
            JsonReader reader = new JsonReader(new FileReader("./EvalList.json"));
            eval_list = gson.fromJson(reader, EvaluationPacket.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WRITEJSON(){

        usr.evaltoJSON();
        usr.favfoodtoJSON();
        usr.favrstrnttoJSON();
    }

    public void READJSON(){
        usr.Jsontoeval();
        usr.Jsontofavfood();
        usr.Jsontofavrstrnt();
    }

    //
    public double getWeight(double w)
    {
        if(0 < w && w <= 6)
        {   return 6 - w;}
        else
        {
            return 0;
        }
    }

    public double defaultScore(int i, Food food)
    {

        switch(i)
        {
            case 1:
                return 0;
            case 2:
                return 0;
            default:
                return 0;
        }
        //Evaulation에 넣어야 함.
    }

    public double meanScore(int j, Food food)
    {
        int size=food.get_eval_size();
        double meanscore = 0;
        for(int i=0; i<size ;i++)
        {
            meanscore += find_eval(food.get_eval(i)).get_weight(j);
        }
        meanscore = meanscore/size;
        return meanscore;
        //Food에 넣으면 안된다.
    }
    //score는 사용자가 입력한 전반적인 점수이다.
    //w는 사용자의 선호도에 대한 정보이다.
    //사용자가 전반적인 점수만 넣었을 때, 이를 항목별 점수로 반환하는 함수
    //식을 계산해보면 전반적인 점수가 s점일 때, j번째 항목의 반환 점수는
    //(s * w_j)/(\Sum w_i^2)이다.

    //최종 점수를 계산해주는 함수이다.
    //이 함수로 계산된 점수에 맞게 음식들을 정렬하여 추천하도록 한다.
    //num은 현재까지 들어온 평가의 갯수이고
    //weight는 선호도의 list이며
    //meanScore는 항목별 평균 평가 점수의 list이며(평균은 db나 평가에서 계산하도록 한다.)
    //defaultScore는 평가가 부족할 때를 대비하여 미리 넣어놓은 값의 list이다.
    //현재까지의 평가가 5개 이하일 때는, default와 적절한 비율로 점수가 계산되어야 하며, 5개 이상일 때는 평가만으로 점수를 내도 된다고 생각한다.
    //따라서 5개 이하의 평가가 있을 때는 \Sum w_i * ((5-t)*d_i + t*m_i)/5로 계산하도록 하며,
    //5개 이상의 평가가 있을 때는 \Sum w_i * m_i로 계산하도록 한다.
    public double getFinalScore(int num, Food food){
        double result = 0;
        if(num <= 5)
        {
            for(int i = 0; i < 5; i++)
            {
                result += getWeight(usr.get_pref(i)) * ((5-num) * defaultScore(i, food)+ num * meanScore(i, food) / 5);
            }
        }
        else
        {
            for(int i = 0; i < 5; i++)
            {
                result += getWeight(usr.get_pref(i)) * meanScore(i, food);
            }
        }
        return result;
    }
}
