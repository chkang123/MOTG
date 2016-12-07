package ml.diony.motg.Classes;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Admin on 2016. 11. 20..
 */


public class Users {

    String id; // 고유 식별자
    String nickname; // 닉네임
    boolean identified; // 식별되었는가

    //개인 취향

    ArrayList<Food_Type> FavoriteFoodTypes;//음식 종류에 대한 취향
    EvaluationPacket eval_list;
    Gson gson;
    //개인 취향 weight 저장 행렬
    Preference pref;
    FavoriteRstrntPacket fav_rstrnts_list;
    //방문기록
/*
 *   ArrayList<Food> Foods_History;
 *   ArrayList<Restaurant> Rstrnt_History;
 */
    FavoriteFoodPacket fav_foods_list; //

    public Users(String id, String nickname) {

        set_info(id, nickname);

    }

    public void set_info(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
        this.identified = false;
    }

    public String get_idcode() {
        return id;
    }
    // ArrayList<Integer>  Achievement; // 업적의 id 를 저장하는 리스트

    public void add_food_type(Food_Type food_type) {
        int size = FavoriteFoodTypes.size();
        FavoriteFoodTypes.add(size, food_type);

    }

    public ArrayList<Food_Type> get_food_type() {
        return (FavoriteFoodTypes);
    }

    public int get_food_type(Food_Type food_type) {
        int idx = 0;
        for (int i = 0; i < FavoriteFoodTypes.size(); i++) {
            if (FavoriteFoodTypes.get(i) == food_type) {
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

    public Preference set_preference() {
        pref = new Preference();
        return pref;
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
    public ArrayList<Fav_Rstrnts> get_fav_rstrnts() {
        return fav_rstrnts_list.getlist();
    }//즐겨찾기 클래스 구현할 것

    public ArrayList<Fav_Foods> get_fav_foods() {
        return fav_foods_list.getlist();
    }

    public void favfoodtoJSON() {
        String json = gson.toJson(fav_foods_list);
        try {
            FileWriter file = new FileWriter("./FavFoodList.json");
            file.write(json);
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Jsontofavfood() {
        try {
            JsonReader reader = new JsonReader(new FileReader("./FavFoodList.json"));
            eval_list = gson.fromJson(reader, FavoriteFoodPacket.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void favrstrnttoJSON() {
        String json = gson.toJson(fav_rstrnts_list);
        try {
            FileWriter file = new FileWriter("./FavFoodList.json");
            file.write(json);
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Jsontofavrstrnt() {
        try {
            JsonReader reader = new JsonReader(new FileReader("./FavRstrntList.json"));
            eval_list = gson.fromJson(reader, FavoriteRstrntPacket.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void evaltoJSON() {
        String json = gson.toJson(eval_list);
        try {
            FileWriter file = new FileWriter("./EvalList.json");
            file.write(json);
            file.flush();
            file.close();

        } catch (IOException e) {
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

    public class EvaluationPacket {
        int eval_version;
        ArrayList<String> eval_list;

        public EvaluationPacket() {
        }

        public void add_version() {
            eval_version++;
        }

        public void set_version(int version) {
            eval_version = version;
        }

        public int size() {
            return eval_list.size();
        }

        public void add(Evaluation evaluation) {
            eval_list.add(evaluation.get_idcode());
        }

        public void add(int size, Evaluation evaluation) {
            eval_list.add(size, evaluation.get_idcode());
        }

        public String get(int i) {
            return eval_list.get(i);
        }
    }

    //즐겨찾기
    public class FavoriteRstrntPacket {
        int _version;
        ArrayList<Fav_Rstrnts> fav_rstrnts_list;

        public FavoriteRstrntPacket() {
        }

        public void add_version() {
            _version++;
        }

        public void set_version(int version) {
            _version = version;
        }

        public int size() {
            return fav_rstrnts_list.size();
        }

        public void add(Fav_Rstrnts fav_rstrnts) {
            fav_rstrnts_list.add(fav_rstrnts);
        }

        public void add(int size, Fav_Rstrnts fav_rstrnts) {
            fav_rstrnts_list.add(size, fav_rstrnts);
        }

        public Fav_Rstrnts get(int i) {
            return fav_rstrnts_list.get(i);
        }

        public ArrayList<Fav_Rstrnts> getlist() {
            return fav_rstrnts_list;
        }
    }//즐겨찾기 클래스 구현할 것

    public class FavoriteFoodPacket {
        int _version;
        ArrayList<Fav_Foods> fav_foods_list;

        public FavoriteFoodPacket() {
        }

        public void add_version() {
            _version++;
        }

        public void set_version(int version) {
            _version = version;
        }

        public int size() {
            return fav_foods_list.size();
        }

        public void add(Fav_Foods fav_food) {
            fav_foods_list.add(fav_food);
        }

        public void add(int size, Fav_Foods fav_food) {
            fav_foods_list.add(size, fav_food);
        }

        public Fav_Foods get(int i) {
            return fav_foods_list.get(i);
        }

        public ArrayList<Fav_Foods> getlist() {
            return fav_foods_list;
        }
    }

}