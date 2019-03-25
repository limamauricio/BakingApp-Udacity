package com.limamauricio.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.limamauricio.bakingapp.R;
import com.limamauricio.bakingapp.model.Step;
import com.limamauricio.bakingapp.ui.fragments.StepDetailsFragment;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;

public class StepDetailActivity extends AppCompatActivity {

    private Step step;
    private List<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);
        if (savedInstanceState == null){

            getStepData();
            initFragments();

        }
    }


    private void initFragments(){

        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipeSteps", (Serializable) steps);
        args.putInt("stepIndex",step.getId());
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
        }
    }
}
