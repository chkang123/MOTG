package io.dionysource.motg.usrclss;

import java.util.ArrayList;

/**
 * Created by Admin on 2016. 11. 30..
 */

public class Property {

  // 사용자의 선호도, 사용자가 중요하게 여기는 정도를 속성별로 저장하기 위해 정의한 클래스
    double Taste; //  맛
    double Distance; //  거리
    double Cost; //  비용
    double Valuation; //  타인의 평가를 얼마나 중요하게 여기는가
    double Service; // 맛집의 서비스


    public Property()
    {
        Taste = 0;
        Distance = 0;
        Cost = 0;
        Valuation = 0;
        Service = 0;
        //0은 선호도로서는 선호도 상관 없음을 의미한다.
    }

    public void set_pref(double taste, double distance, double cost, double valuation, double service)
    {
        Taste = taste;
        Distance = distance;
        Cost = cost;
        Valuation = valuation;
        Service = service;
    }
    public double get(int i) {
        switch (i) {
            case 0:
                return Taste;
            case 1:
                return Distance;
            case 2:
                return Cost;
            case 3:
                return Valuation;
            case 4:
                return Service;
            default:
                return 0;
        }
    }
    public double Squaresum()
    {
        double squaresum = 0;
        for(int i = 0; i < 5; i++)
        {
            squaresum += get(i) * get(i);
        }
        return squaresum;
    }
}
