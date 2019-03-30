package com.limamauricio.bakingapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.limamauricio.bakingapp.R;
import com.limamauricio.bakingapp.model.Ingredient;
import com.limamauricio.bakingapp.model.Recipe;
import com.limamauricio.bakingapp.model.Step;
import com.limamauricio.bakingapp.ui.RecipeDetailsActivity;
import com.limamauricio.bakingapp.ui.adapter.RecipeIngredientsAdapter;
import com.limamauricio.bakingapp.ui.adapter.RecipeStepsAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment {

    @BindView(R.id.ingredients_recycler_view)
    RecyclerView ingredientsRecyclerView;

    @BindView(R.id.steps_recycler_view)
    RecyclerView stepsRecyclerView;

    private List<Step> steps;
    private List<Ingredient> ingredients;
    private Recipe recipe;

    private RecipeIngredientsAdapter ingredientsAdapter;
    private RecipeStepsAdapter stepsAdapter;

    OnStepClickListener mCallback;

    public interface OnStepClickListener{

        void onStepClicked(int position);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Recipe recipeWidget){

        Intent i = new Intent(getActivity(),RecipeDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe",recipeWidget);
        i.putExtras(bundle);
        //i.putExtra("recipe",recipeWidget);
        getActivity().startActivity(i);

    }

    public RecipeDetailsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recipe_details_fragment,
                container, false);

        ButterKnife.bind(this,view);
        getRecipeData();
        prepareRecyclerview();

        return view;
    }


    private void getRecipeData() {

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        recipe = (Recipe) bundle.getSerializable("recipe");

        getActivity().setTitle(recipe.getName());

        if (recipe != null){

            if (recipe.getIngredients() != null)
                ingredients = recipe.getIngredients();
            else
                Toast.makeText(getContext(), "Failed to get ingredients", Toast.LENGTH_SHORT).show();

            if (recipe.getSteps() != null)
                steps = recipe.getSteps();
            else
                Toast.makeText(getContext(), "Failed to get steps", Toast.LENGTH_SHORT).show();

        }

    }

    private void prepareRecyclerview(){

        ingredientsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));
        ingredientsAdapter = new RecipeIngredientsAdapter(getContext());
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        stepsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext())
        );

        stepsAdapter = new RecipeStepsAdapter(getContext());
        stepsRecyclerView.setAdapter(stepsAdapter);

        stepsAdapter.setSteps(steps);
        ingredientsAdapter.setIngredients(ingredients);

        stepsAdapter.setOnItemClickListener(new RecipeStepsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                mCallback.onStepClicked(position);

            }
        });
    }

}
