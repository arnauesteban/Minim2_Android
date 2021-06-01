package dsa.upc.edu.listapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import dsa.upc.edu.listapp.api.Api;
import dsa.upc.edu.listapp.api.Badge;
import dsa.upc.edu.listapp.api.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitialPage extends AppCompatActivity {

    ProgressBar progressBar;
    private final String TAG = InsigniasActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_page);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void insigniasBtnOnClick(View v)
    {
        progressBar.setVisibility(View.VISIBLE);
        doApiCallInsignias();
    }

    public void perfilBtnOnClick(View v)
    {
        progressBar.setVisibility(View.VISIBLE);
        doApiCallPerfil();
    }

    private void doApiCallInsignias() {
        Api apiService = Api.retrofit.create(Api.class);
        Call<List<Badge>> call = apiService.getBadges();

        call.enqueue(new Callback<List<Badge>>() {
            @Override
            public void onResponse(Call<List<Badge>> call, Response<List<Badge>> response) {
                Intent intent = new Intent(InitialPage.this, InsigniasActivity.class);
                intent.putExtra("response", (Serializable) response.body());
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<List<Badge>> call, Throwable t) {

                String msg = "Error in retrofit: "+t.toString();
                Log.d(TAG,msg);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            }
        });
    }

    private void doApiCallPerfil() {
        Api apiService = Api.retrofit.create(Api.class);
        Call<User> call = apiService.getUser(1);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Intent intent = new Intent(InitialPage.this, PerfilActivity.class);
                intent.putExtra("response", (Serializable) response.body());
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

                String msg = "Error in retrofit: "+t.toString();
                Log.d(TAG,msg);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            }
        });
    }
}