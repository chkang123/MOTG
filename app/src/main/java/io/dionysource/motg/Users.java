package io.dionysource.motg.usrclss;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;


/**
 * Created by Admin on 2016. 11. 20..
 */


public class Users {
    //개인정보
    //개인상태

    //개인 취향
    ArrayList<String> FavoriteFlavors;
    ArrayList<Food_Type> FavoriteFoodTypes;//음식 종류에 대한 취향
    ArrayList<String> eval_list;
   @JsonIgnore
    int num_togo; // 같이 가는 사람
    double distance; // 선호하는 거리
    @JsonIgnore
    double money; // 보유하고 있는 / 사용 가능한 돈(여유)


    //개인 취향 weight 저장 행렬
    Preference[] weight;

    //방문기록
/*
 *   ArrayList<Food> Foods_History;
 *   ArrayList<Restaurant> Rstrnt_History;
 */

    //즐겨찾기
    ArrayList<Fav_Rstrnts> fav_rstrnts_list; //즐겨찾기 클래스 구현할 것
    ArrayList<Fav_Foods> fav_foods_list; //
    // ArrayList<Integer>  Achievement; // 업적의 id 를 저장하는 리스트


    String id; // 고유 식별자
    String nickname; // 닉네임
    boolean identified; // 식별되었는가

    public Users(String id, String nickname) {

        set_info(id, nickname);

    }

    public void set_info(String id, String nickname)
    {
        this.id = id;
        this.nickname = nickname;
        this.identified = false;
    }

    public String get_idcode() {return id;}

    public void add_flavor(String flavor) {
        int size = FavoriteFlavors.size();
        FavoriteFlavors.add(size, flavor);
    }

    public ArrayList<String> get_flavor() {
        return (FavoriteFlavors);
    }

    public int get_flavor(String flavor) {
        int idx = FavoriteFlavors.indexOf(flavor);
        return idx;
    }

    public void add_food_type(Food_Type food_type) {
        int size = FavoriteFoodTypes.size();
        FavoriteFoodTypes.add(size, food_type);

    }

    public ArrayList<Food_Type> get_food_type() {
        return (FavoriteFoodTypes);
    }

    public int get_food_type(String food_type) {
        int idx = 0;
        for (int i = 0; i < FavoriteFoodTypes.size(); i++) {
            if (FavoriteFoodTypes.get(i).get_type() == food_type) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    public void add_eval(Evaluation eval) {

        int size = eval_list.size();
        eval_list.add(size, eval.get_idcode());
    }
    public void set_preference(Preference[] weight){
        this.weight = weight;
    }
/*
 *   public ArrayList<Food> get_foods_history()
 *   {
 *       return Foods_History;
 *   }

    public ArrayList<Restaurant> get_rstrnt_history() {


    return Rstrnt_History;

    }
 */
    public ArrayList<Fav_Rstrnts> get_fav_rstrnts()
    {
        return fav_rstrnts_list;
    }//즐겨찾기 클래스 구현할 것
    public ArrayList<Fav_Foods> get_fav_foods()
    {
        return fav_foods_list;
    }

}