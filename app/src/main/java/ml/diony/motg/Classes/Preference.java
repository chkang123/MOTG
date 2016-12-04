package ml.diony.motg.Classes;

/**
 * Created by Admin on 2016. 11. 30..
 */

public class Preference {
    int Taste;
    int Distance;
    int Cost;
    int Valuation;
    int Service;

    public Preference() {
        Taste = 0;
        Distance = 0;
        Cost = 0;
        Valuation = 0;
        Service = 0;

    }

    public void set_pref(int taste, int distance, int cost, int valuation, int service) {
        Taste = taste;
        Distance = distance;
        Cost = cost;
        Valuation = valuation;
        Service = service;
    }
}
