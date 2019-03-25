package com.limamauricio.bakingapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limamauricio.bakingapp.R;
import com.limamauricio.bakingapp.model.Step;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;

public class VideoPlayerFragment extends Fragment {

    @BindView(R.id.step_description_id)
    TextView txtStepDescription;

    @BindView(R.id.video_layout_id)
    TextView videoId;

    @BindView(R.id.btn_prev_step)
    AppCompatButton btnPrev;

    @BindView(R.id.btn_next_step)
    AppCompatButton btnNext;

    @BindView(R.id.txt_current_step)
    TextView currentStep;

    @Setter
    private String stepDescription;

    @Setter
    private String videoURI;

    @Setter
    private Integer stepId;

    private List<Step> steps;
    private boolean twoPanel = true;

    public VideoPlayerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null){

            steps = (List<Step>) savedInstanceState.getSerializable("stepList");
            stepId = savedInstanceState.getInt("stepId");
            twoPanel = savedInstanceState.getBoolean("twoPanel");

        }else {
            Bundle bundle = getArguments();
            steps = (List<Step>) bundle.getSerializable("recipeSteps");
            stepId = bundle.getInt("stepIndex");
            twoPanel = bundle.getBoolean("twoPanel");
        }
        View rootView = inflater.inflate(R.layout.video_player_fragment, container, false);
        ButterKnife.bind(this,rootView);
        updateData();

        return rootView;
    }

    private void updateData(){
        setStepDescription(steps.get(stepId).getDescription());
        setVideoURI(steps.get(stepId).getId()
                + " - " + steps.get(stepId).getVideoURL());
        if (stepDescription != null)
            txtStepDescription.setText(stepDescription);
        if (videoId != null)
            videoId.setText(videoURI);

        if (stepId == 0)
            btnPrev.setVisibility(View.INVISIBLE);
        else
            btnPrev.setVisibility(View.VISIBLE);

        if (stepId +1 == steps.size())
            btnNext.setVisibility(View.INVISIBLE);
        else
            btnNext.setVisibility(View.VISIBLE);

        currentStep.setText((stepId + 1) +
                " / "+steps.size());

        if (!twoPanel)
            getActivity().setTitle(steps.get(stepId).getShortDescription());

    }

    @OnClick(R.id.btn_next_step)
    public void onBtnNextClicked(){

       setStepDescription(steps.get(stepId + 1).getDescription());
       setVideoURI(steps.get(stepId + 1).getId()
                + " - " + steps.get(stepId + 1).getVideoURL());
       stepId = steps.get(stepId + 1).getId();
       updateData();

    }

    @OnClick(R.id.btn_prev_step)
    public void onBtnPrevClicked(){

        setStepDescription(steps.get(stepId - 1).getDescription());
        setVideoURI(steps.get(stepId - 1).getId()
                + " - " + steps.get(stepId - 1).getVideoURL());
        stepId = steps.get(stepId - 1).getId();
        updateData();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("stepList", (Serializable) steps);
        outState.putInt("stepId",stepId);
        outState.putBoolean("twoPanel",twoPanel);

    }
}
