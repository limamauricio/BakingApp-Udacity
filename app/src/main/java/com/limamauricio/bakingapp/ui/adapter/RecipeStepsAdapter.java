package com.limamauricio.bakingapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limamauricio.bakingapp.R;
import com.limamauricio.bakingapp.model.Step;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsViewHolder> {

    private Context mContext;

    @Getter
    private List<Step> steps;

    private static ClickListener mClickListener;

    public RecipeStepsAdapter(Context context) {
        this.mContext = context;

    }

    @NonNull
    @Override
    public RecipeStepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.step_item, viewGroup, false);
        return new RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsViewHolder recipeStepsViewHolder, int i) {

        final Step step = steps.get(i);
        recipeStepsViewHolder.stepName.setText(step.getId()+1
            + ". "+step.getShortDescription());

    }

    @Override
    public int getItemCount() {
        return steps == null ? 0 : steps.size();
    }

    public void setSteps(List<Step> stepList){
        steps = stepList;
        notifyDataSetChanged();
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.step_name)
        TextView stepName;

        public RecipeStepsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecipeStepsAdapter.mClickListener = clickListener;
    }


}
