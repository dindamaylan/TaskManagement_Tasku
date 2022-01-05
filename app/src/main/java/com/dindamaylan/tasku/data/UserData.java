package com.dindamaylan.tasku.data;

import com.google.firebase.firestore.DocumentId;

public class UserData {
    public String name, username, password, bio;

    @DocumentId
    public String id;

    public UserData() {
    }

    public UserData(String name, String username, String password, String bio) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.bio = bio;
    }

    public UserData(String name, String username, String password, String bio, String id) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.id = id;
    }


}
