package ml.diony.motg.Display;

import java.util.ArrayList;

//식당정보 화면에서 Menu와 가격을 ExpandableListView에서 출력하기전
//데이터를 잠시 저장하고 있을 Class
//groupName은 메뉴이름을 가지며 Parent-row에 출력된다.
//child는 해당메뉴의 가격정보를 갖고 있다.
public class MenuGroup {
    public ArrayList<String> child;
    public String groupName;

    MenuGroup(String name) {
        groupName = name;
        child = new ArrayList<String>();
    }
}