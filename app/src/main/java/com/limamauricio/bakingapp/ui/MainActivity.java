package com.limamauricio.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.limamauricio.bakingapp.R;
import com.limamauricio.bakingapp.model.Recipe;
import com.limamauricio.bakingapp.proxy.Proxy;
import com.limamauricio.bakingapp.proxy.ProxyFactory;
import com.limamauricio.bakingapp.ui.adapter.RecipeAdapter;
import com.limamauricio.bakingapp.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ItemClickListener{

    private Call<List<Recipe>> call;
    private List<Recipe> recipeList;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.recipes_recycler_view)
    RecyclerView recipeRecyclerView;

    private RecipeAdapter recipeAdapter;
    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (findViewById(R.id.txt_id) != null)
            twoPane = true;
        else
            twoPane = false;
        prepareRecyclerview();
        checkNetworkConnection(this);

    }

    private void prepareRecyclerview() {
        if (twoPane){
            recipeRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recipeAdapter = new RecipeAdapter(this, this);
        }else {
            recipeRecyclerView.setLayoutManager(
                    new LinearLayoutManager(this));
            recipeAdapter = new RecipeAdapter(this, this);
        }
    }


    private void loadRecipes(){

        Proxy proxy = ProxyFactory.getInstance().create(Proxy.class);

        if (Utils.checkInternetConnection(getApplicationContext())){

            call  = proxy.getAll();

            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                    recipeList = response.body();
                    recipeAdapter.setRecipes(recipeList);
                    recipeRecyclerView.setAdapter(recipeAdapter);


                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, getString(R.string.failed_to_get_recipes), Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(MainActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }


    }

    private void checkNetworkConnection(Context context){

        if(!Utils.checkInternetConnection(context)){
            Toast.makeText(MainActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, getString(R.string.loading_recipes), Toast.LENGTH_SHORT).show();
            loadRecipes();
        }

    }

    @Override
    public void onItemClickListener(int itemId) {
        Intent intent = new Intent(MainActivity.this,RecipeDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe",recipeList.get(itemId - 1));
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(MainActivity.this, recipeList.get(itemId - 1).getName(), Toast.LENGTH_SHORT).show();
    }
}
