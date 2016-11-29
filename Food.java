package io.dionysource.motg.usrclss;
/**
 * Created by Admin on 2016. 11. 19..
 */

public class Food {
    String idcode; // 식별자코드
    String name; // 음식의 이름
    Flavor flavor; // 음식의 맛
    Food_Type food_type; // 음식의 종류
    int sale_cost; // 가격
    double eval_point; // 평점
    Restaurant restaurant; // 이 음식을 판매하는 음식점

    public Food(String idcode, String name, Flavor flavor, Food_Type food_type, int sale_cost, int eval_points, Restaurant restaurant)
    {
        this.idcode = idcode;
        this.name = name;
        this.flavor = flavor;
        this.food_type = food_type;
        this.sale_cost  = sale_cost;
        this.eval_point = 0 ;
        this.restaurant = restaurant;
    }

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
    public double get_eval_points(){
        return eval_point;
    }
    public void set_rstrnt(Restaurant rstrnt){
        restaurant = rstrnt;
    }
    public Restaurant get_rstrnt(){
        return restaurant;
    }
}
