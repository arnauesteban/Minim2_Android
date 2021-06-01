package dsa.upc.edu.listapp.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    String URL = "http://192.168.1.44:8080/dsaApp/";

    @GET("/user/{username}")
    Call<User> getUser(
            @Path("username") String username);

    @GET("/badges")
    Call<List<Badge>> getBadges();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}