package dsa.upc.edu.listapp.api;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    public final String username;
    public final String avatar;
    public final String name;
    public final String surname;
    public final List<String> badges;

    public User(String username, String avatar, String name, String surname, List<String> badges)
    {
        this.username = username;
        this.avatar = avatar;
        this.name = name;
        this.surname = surname;
        this.badges = badges;
    }

}
