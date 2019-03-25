package com.limamauricio.bakingapp.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.limamauricio.bakingapp.R;
import com.limamauricio.bakingapp.model.Step;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Setter;

public class StepDetailsFragment extends Fragment {

    @BindView(R.id.step_description_id)
    TextView txtStepDescription;

    @BindView(R.id.txt_no_video)
    TextView txtNoVideo;

    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;

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
    private long mPlayerPosition;

    private SimpleExoPlayer mExoPlayer;

    public StepDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null){

            steps = (List<Step>) savedInstanceState.getSerializable("stepList");
            stepId = savedInstanceState.getInt("stepId");
            twoPanel = savedInstanceState.getBoolean("twoPanel");
            mPlayerPosition = savedInstanceState.getLong("videoTime");

        }else {
            Bundle bundle = getArguments();
            steps = (List<Step>) bundle.getSerializable("recipeSteps");
            stepId = bundle.getInt("stepIndex");
            twoPanel = bundle.getBoolean("twoPanel");
        }
        View rootView = inflater.inflate(R.layout.step_details_fragment, container, false);
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

        playVideo(steps.get(stepId));

        if (!twoPanel)
            getActivity().setTitle(steps.get(stepId).getShortDescription());

    }

    private void playVideo(Step step){

        if (!step.getVideoURL().equals("")){
            txtNoVideo.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(step.getVideoURL()));
        }
        else if (!step.getThumbnailURL().equals("")){
            txtNoVideo.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(step.getThumbnailURL()));

        } else{
            txtNoVideo.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
        }

    }
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getContext(), "RecipeStepVideo");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

            if (mPlayerPosition != C.TIME_UNSET)
                mExoPlayer.seekTo(mPlayerPosition);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            updateVideoTimePosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @OnClick(R.id.btn_next_step)
    public void onBtnNextClicked(){
        releasePlayer();
       setStepDescription(steps.get(stepId + 1).getDescription());
       setVideoURI(steps.get(stepId + 1).getId()
                + " - " + steps.get(stepId + 1).getVideoURL());
       stepId = steps.get(stepId + 1).getId();
       updateData();

    }

    @OnClick(R.id.btn_prev_step)
    public void onBtnPrevClicked(){
        releasePlayer();
        setStepDescription(steps.get(stepId - 1).getDescription());
        setVideoURI(steps.get(stepId - 1).getId()
                + " - " + steps.get(stepId - 1).getVideoURL());
        stepId = steps.get(stepId - 1).getId();
        updateData();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        updateVideoTimePosition();
        outState.putSerializable("stepList", (Serializable) steps);
        outState.putInt("stepId",stepId);
        outState.putBoolean("twoPanel",twoPanel);
        outState.putLong("videoTime", mPlayerPosition);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void updateVideoTimePosition() {
        if (mExoPlayer != null) {
            mPlayerPosition = mExoPlayer.getCurrentPosition();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        playVideo(steps.get(stepId));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mExoPlayer != null){
            mExoPlayer.setPlayWhenReady(true);
            mExoPlayer.seekTo(mPlayerPosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer != null){
            updateVideoTimePosition();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mExoPlayer != null){
            updateVideoTimePosition();
            releasePlayer();
        }
    }
}
