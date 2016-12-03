package io.dionysource.motg.usrclss;

import java.util.ArrayList;


public class Food {
    String idcode; // 식별자코드
    String name; // 음식의 이름
    Flavor flavor; // 음식의 맛
    Food_Type food_type; // 음식의 종류
    int sale_cost; // 가격
    double eval_point; // 평점
    String restaurant; // 이 음식을 판매하는 음식점
    ArrayList<String> eval_list; //

    public Food()
    {

    }
    public Food(String idcode, String name, Flavor flavor, Food_Type food_type, int sale_cost, String restaurant)
    {
        this.idcode = idcode;
        this.name = name;
        this.flavor = flavor;
        this.food_type = food_type;
        this.sale_cost  = sale_cost;
        this.eval_point = 0 ;
        this.restaurant = restaurant;
        eval_list = null;
    }
    public String get_idcode() {return idcode;}
    public void set_flavor(Flavor flavor)
    {
        this.flavor = flavor;
    }
    public Flavor get_flavor() {
        return flavor;
    }
    public void set_food_type(Food_Type food_type){
        this.food_type = food_type;
    }
    public Food_Type get_food_type(){

        return food_type;

    }
    public void set_sale_cost(int sale_cost) {
        this.sale_cost = sale_cost;
    }
    public int get_sale_cost(){
        return sale_cost;
    }
    public void set_eval_points(double eval_point){

        this.eval_point = eval_point;
    }

    public void add_eval(Evaluation eval) {

        int size = eval_list.size();
        eval_list.add(size, eval.get_idcode());
    }
    public double get_eval_points(){
        return eval_point;
    }
    public void set_rstrnt(String rstrnt){
        restaurant = rstrnt;
    }
    public String get_rstrnt(){
        return restaurant;
    }
}
