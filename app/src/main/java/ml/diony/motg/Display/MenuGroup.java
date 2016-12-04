package ml.diony.motg.Display;

import java.util.ArrayList;

public class MenuGroup {
    public ArrayList<String> child;
    public String groupName;

    MenuGroup(String name) {
        groupName = name;
        child = new ArrayList<String>();
    }
}