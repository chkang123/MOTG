package io.dionysource.motg.usrclss;

import java.util.ArrayList;

/**
 * Created by Admin on 2016. 11. 30..
 */

public class Property {
    double Taste;
    double Distance;
    double Cost;
    double Valuation;
    double Service;

    public Property()
    {
        Taste = 0;
        Distance = 0;
        Cost = 0;
        Valuation = 0;
        Service = 0;

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
