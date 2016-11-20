package io.dionysource.motg.usrclss;

/**
 * Created by Admin on 2016. 11. 19..
 */

public class Evaluation {
    int rstrnt_eval_point; // 레스토랑 평점
    int food_eval_point; // 음식 평점
    Id_usr id_usr; // 평가 작성한
    Restaurant restaurant;
    Food food;
    String eval_critic; // 평가
    //Evaluation next;

    public Evaluation(int rstrnt_eval_point1, int food_eval_point1, Id_usr id_usr1, Restaurant restaurant1, Food food1)
    {
        rstrnt_eval_point = rstrnt_eval_point1;
        food_eval_point = food_eval_point1;
        id_usr = id_usr1;
        restaurant = restaurant1;
        food = food1;
    }
}


