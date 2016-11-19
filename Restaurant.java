package io.dionysource.motg.usrclss;
import java.util.ArrayList;

/**
 * Created by Admin on 2016. 11. 19..
 */


public class Restaurant {
    ArrayList<Food> food_list;
    int num_togo; // 적정 인원
    String address; // 가게의 주소
    String[] telephone; // 가게 전화번호 (배열인 이유는 전화번호가 여러 개일 수 있으므로
    String introduce; // 가게 소개
    ArrayList<Evaluation> eval_list; // 평가 리스트
    int eval_point; // 평점

}
