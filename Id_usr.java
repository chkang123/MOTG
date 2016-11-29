package io.dionysource.motg.usrclss;

import java.util.ArrayList;


/**
 * Created by Admin on 2016. 11. 20..
 */


public class Id_usr extends Users {
    //개인정보
    //개인상태

    //개인 취향
    ArrayList<Flavor> FavoriteFlavors;
    ArrayList<Food_Type> FavoriteFoodTypes;//음식 종류에 대한 취향
    ArrayList<Evaluation> eval_list;
    double distance; // 선호하는 거리
    double money; // 보유하고 있는 / 사용 가능한 돈(여유)
    ArrayList<Mood> mood_list;//선호하는 분위기

    //방문기록
/*
 *   ArrayList<Food> Foods_History;
 *   ArrayList<Restaurant> Rstrnt_History;
 */

    //즐겨찾기
    ArrayList<Fav_Rstrnts> fav_rstrnts_list; //즐겨찾기 클래스 구현할 것
    ArrayList<Fav_Foods> fav_foods_list; //
    // ArrayList<Integer>  Achievement; // 업적의 id 를 저장하는 리스트


    public Id_usr(String id, String nickname) {
        super(id, nickname);
        next = null;
    }

    public void add_flavor(Flavor flavor) {
        int size = FavoriteFlavors.size();
        FavoriteFlavors.add(size, flavor);
    }

    public ArrayList<Flavor> get_flavor() {
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
        eval_list.add(size, eval);
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