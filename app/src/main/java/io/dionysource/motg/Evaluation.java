package io.dionysource.motg.usrclss;

/**
 * Created by Admin on 2016. 11. 19..
 */

public class Evaluation {
    double rstrnt_eval_point; // 레스토랑 평점
    double food_eval_point; // 음식 평점
    Id_usr id_usr; // 평가 작성한 유저
    Restaurant restaurant; //레스토랑
    Food food; // 음식
    String eval_critic; // 평가

    public Evaluation(int rstrnt_eval_point1, int food_eval_point1, Id_usr id_usr1, Restaurant restaurant1, Food food1, String eval_critic1) {
        rstrnt_eval_point = rstrnt_eval_point1;
        food_eval_point = food_eval_point1;
        id_usr = id_usr1;
        restaurant = restaurant1;
        food = food1;
        eval_critic = eval_critic1;
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
    public Id_usr get_id_usr()
    {
        return id_usr;
    }// 평가 작성한 유저
    public Restaurant get_restaurant()
    {
        return restaurant;
    }//레스토랑
    public Food get_food()
    {
        return food;
    }
    public String get_eval_critic()
    {
        return eval_critic;
    }// 평가
}


