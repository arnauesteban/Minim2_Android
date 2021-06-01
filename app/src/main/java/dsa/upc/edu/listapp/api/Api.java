package dsa.upc.edu.listapp.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    String URL = "http://10.0.2.2:8080/";

    @GET("/user/{userId}")
    Call<User> getUser(
            @Path("userId") int userId);

    @GET("/badges")
    Call<List<Badge>> getBadges();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}