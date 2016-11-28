package io.dionysource.motg.usrclss;

class Users{
    String id; // 고유 식별자
    String nickname; // 닉네임
    Users next; // 다음 노드

    Users(String id, String nickname)
    {
        this.id = id;
        this.nickname = nickname;
        next = null;
    }
}


