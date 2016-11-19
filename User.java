package io.dionysource.motg.usrclss;
import java.util.ArrayList;
/**
 * Created by Admin on 2016. 11. 19..
 */


class Users{
    String id; // 고유 식별자
    String nickname; // 닉네임
    Users next; // 다음 노드

    public:
}

public class Id_usr extends Users{
    //개인정보

    //개인상태

    //개인 취향
    ArrayList<Flavor> FavoriteFlavors;
    //음식 종류에 대한 취향
    ArrayList<Evaluation> eval_list;
    double distance; // 선호하는 거리
    double money; // 보유하고 있는 / 사용 가능한 돈(여유)
    //선호하는 분위기
    //선호하는 서비스?

    //방문기록
    ArrayList<Food> Foods_History;
    ArrayList<Restaurant> Rstrnt_History;

    //즐겨찾기
    ArrayList<Fav_Rstrnt> fav_rstrnts_list; //즐겨찾기 클래스 구현할 것
    ArrayList<Fav_Food> fav_foods_list; //
    ArrayList<Integer>  Achievement; // 업적의 id 를 저장하는 리스트
    public:

}

public class Guest_usr extends Users{

    public:


} // Local 에다가 식별키를 저장한다.
