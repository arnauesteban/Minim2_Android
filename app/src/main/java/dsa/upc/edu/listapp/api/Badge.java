package dsa.upc.edu.listapp.api;

import java.io.Serializable;

public class Badge implements Serializable {

    public final String name;
    public final String url;


    public Badge(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
