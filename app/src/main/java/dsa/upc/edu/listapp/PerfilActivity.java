package dsa.upc.edu.listapp;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import dsa.upc.edu.listapp.api.Api;
import dsa.upc.edu.listapp.api.Badge;
import dsa.upc.edu.listapp.api.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Based on http://www.vogella.com/tutorials/AndroidRecyclerView/article.html
//      and https://zeroturnaround.com/rebellabs/getting-started-with-retrofit-2/

public class PerfilActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapterPerfil adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ImageView imageView;
    private TextView usernameTextView;
    private TextView fullNameTextView;

    private User user;

    private final String TAG = PerfilActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        user = (User)getIntent().getSerializableExtra("response");

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        swipeRefreshLayout = findViewById(R.id.my_swipe_refresh);
        imageView = (ImageView) findViewById(R.id.imageView);
        usernameTextView = (TextView) findViewById(R.id.usernameTv);
        fullNameTextView = (TextView) findViewById(R.id.fullNameTv);

        usernameTextView.setText(user.username);
        fullNameTextView.setText(user.name + user.surname);

        GlideApp.with(imageView.getContext())
                .load(user.avatar)
                .into(imageView);

        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(PerfilActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        // Set the adapter
        adapter = new MyAdapterPerfil();
        recyclerView.setAdapter(adapter);

        adapter.setData(user.badges);

        //doApiCall(null);

        // Manage swipe on items
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        adapter.remove(viewHolder.getAdapterPosition());
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        doApiCall(swipeRefreshLayout);
                    }
                }
        );

    }

    private void doApiCall(final SwipeRefreshLayout mySwipeRefreshLayout) {
        Api apiService = Api.retrofit.create(Api.class);
        Call<User> call = apiService.getUser("arnauem");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // set the results to the adapter
                adapter.setData(response.body().badges);

                if(mySwipeRefreshLayout!=null) mySwipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if(mySwipeRefreshLayout!=null) mySwipeRefreshLayout.setRefreshing(false);

                String msg = "Error in retrofit: "+t.toString();
                Log.d(TAG,msg);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            }
        });
    }
}
