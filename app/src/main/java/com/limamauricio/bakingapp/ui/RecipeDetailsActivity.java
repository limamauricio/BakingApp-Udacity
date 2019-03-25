package com.limamauricio.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.limamauricio.bakingapp.R;
import com.limamauricio.bakingapp.model.Recipe;
import com.limamauricio.bakingapp.model.Step;
import com.limamauricio.bakingapp.ui.fragments.RecipeDetailsFragment;
import com.limamauricio.bakingapp.ui.fragments.StepDetailsFragment;

import java.io.Serializable;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnStepClickListener {

    private Recipe recipe;
    private List<Step> steps;
    private Step step;
    private boolean mTwoPanel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        if(findViewById(R.id.relative_layout_id) != null){
            mTwoPanel = true;


            if (savedInstanceState == null){

                getRecipeData();
                //getStepData();
                initFragments();

            }else {
                getRecipeData();
            }

        }else{
            mTwoPanel = false;
            getRecipeData();
        }

    }


    private void getRecipeData() {

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        recipe = (Recipe) bundle.getSerializable("recipe");

        setTitle(recipe.getName());

        if (recipe != null){

            if (recipe.getSteps() != null) {
                steps = recipe.getSteps();
                step = steps.get(0);
            }
            else
                Toast.makeText(this, "Failed to get steps", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onStepClicked(int position) {
        if (mTwoPanel){

            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            Bundle args = new Bundle();
            args.putSerializable("recipeSteps", (Serializable) steps);
            args.putInt("stepIndex",steps.get(position).getId());
            args.putBoolean("twoPanel",mTwoPanel);
            stepDetailsFragment.setArguments(args);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.video_container, stepDetailsFragment)
                    .commit();

        }else{
            onePanelLayout(position);
        }
    }

    private void onePanelLayout(int position){
        Intent intent = new Intent(RecipeDetailsActivity.this, StepDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("step",steps.get(position));
        bundle.putSerializable("stepList", (Serializable) steps);
        bundle.putBoolean("twoPanel",mTwoPanel);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void initFragments(){

        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipeSteps", (Serializable) steps);
        args.putInt("stepIndex",step.getId());
        args.putBoolean("twoPanel",mTwoPanel);
        stepDetailsFragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.video_container, stepDetailsFragment)
                .commit();

    }

    private void getStepData() {
        if (step == null || steps == null) {

            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            step = (Step) bundle.getSerializable("step");
            steps = (List<Step>) bundle.getSerializable("stepList");
            if (step == null)
                step = steps.get(0);
        }
    }
}
