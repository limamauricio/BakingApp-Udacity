package com.limamauricio.bakingapp.ui;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import com.limamauricio.bakingapp.R;
import com.limamauricio.bakingapp.model.Step;
import com.limamauricio.bakingapp.ui.fragments.VideoPlayerFragment;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

        VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
        Bundle args = new Bundle();
        args.putSerializable("recipeSteps", (Serializable) steps);
        args.putInt("stepIndex",step.getId());
        videoPlayerFragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.video_container, videoPlayerFragment)
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
