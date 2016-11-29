package io.dionysource.motg.usrclss;

import org.json.JSONObject;

/**
 * Created by Admin on 2016. 11. 19..
 */

public class Evaluation {
    String idcode;
    double rstrnt_eval_point; // 레스토랑 평점
    double food_eval_point; // 음식 평점
    String id_usr; // 평가 작성한 유저
    String restaurant; //레스토랑
    String food; // 음식
    String eval_critic; // 평가

    //시간
    int year;
    int month;
    int day;
    int hour;
    int time_interval;

    public Evaluation(String idcode, int rstrnt_eval_point1, int food_eval_point1, String id_usr1, String restaurant1, String food1, String eval_critic1, int year, int month, int day, int hour) {
        this.idcode = idcode;
        rstrnt_eval_point = rstrnt_eval_point1;
        food_eval_point = food_eval_point1;
        id_usr = id_usr1;
        restaurant = restaurant1;
        food = food1;
        eval_critic = eval_critic1;
        this.year=year;
        this.month=month;
        this.day=day;
        this.hour=hour;
        time_interval = 0;
    }
    public void set_rstrnt_eval_point(double eval_point)
    {
        rstrnt_eval_point = eval_point;
    }
    public double get_rstrnt_eval_point()
    {
        return rstrnt_eval_point;
    }
    public void set_food_eval_point(double eval_point)
    {
        food_eval_point = eval_point;
    }
    public double get_food_eval_point()
    {
        return food_eval_point;
    }
    public String get_id_usr()
    {
        return id_usr;
    }// 평가 작성한 유저
    public String get_restaurant()
    {
        return restaurant;
    }//레스토랑
    public String get_food()
    {
        return food;
    }
    public String get_eval_critic()
    {
        return eval_critic;
    }// 평가

}


