package com.limamauricio.bakingapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.limamauricio.bakingapp.R;
import com.limamauricio.bakingapp.model.Step;
import com.limamauricio.bakingapp.ui.fragments.StepDetailsFragment;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class StepDetailActivity extends AppCompatActivity {

    private Step step;
    private List<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkFullScreen();
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

    private void checkFullScreen(){

        if(getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            getSupportActionBar().hide();

        }

    }
}
