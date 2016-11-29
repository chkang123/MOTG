package io.dionysource.motg.usrclss;
import java.util.ArrayList;

/**
 * Created by Admin on 2016. 11. 19..
 */


public class Restaurant {
    ArrayList<Food> food_list;
    int num_togo; // 적정 인원
    String address; // 가게의 주소
    String telephone;
    String introduce; // 가게 소개
    ArrayList<Evaluation> eval_list; // 평가 리스트
    double eval_point; // 평점


    public Restaurant(int num_togo, String address, String[] telephone, String introduce)
    {
        this.num_togo = num_togo;
        this.address = address;
        System.arraycopy(telephone, 0, this.telephone, 0, telephone.length);
        this.introduce = introduce;
    }

    public void add_food(Food food)
    {
        food_list.add(food);
    }

    public void set_num_togo(int num_togo)
    {
        this.num_togo = num_togo;
    }
    public int get_num_togo()
    {
        return num_togo;
    }

    public void set_address(String address)
    {
        this.address = address;
    }

    public String get_address()
    {
        return address;
    }
    public void set_telephone(String telephone){
        this.telephone = telephone;
    }
    public String get_telephone(){

        return telephone;
    }
    public void set_introduce(String introduce){
        this.introduce = introduce;
    }
    public String get_introduce(){
        return introduce;
    }
    public void add_eval(Evaluation eval) {

        int size = eval_list.size();
        eval_list.add(size, eval);
        set_eval_point();
    }
    public ArrayList<Evaluation> get_eval()
    {
        return eval_list;
    }

    public void set_eval_point(){
        int size = eval_list.size();
        double eval_sum = 0;
        for(int i=0; i<size; i++)
        {
            eval_sum += eval_list.get(i).get_rstrnt_eval_point();
        }
        eval_point = eval_sum/size;
    }
    public double get_eval_point(){
            return eval_point;
    }
}
