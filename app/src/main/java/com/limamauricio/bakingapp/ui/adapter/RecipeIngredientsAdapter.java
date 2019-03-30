package com.limamauricio.bakingapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limamauricio.bakingapp.R;
import com.limamauricio.bakingapp.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.RecipeIngredientsViewHolder> {


    private final Context mContext;

    @Getter
    private List<Ingredient> ingredients;

    public RecipeIngredientsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecipeIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.ingredients_item, viewGroup, false);
        return new RecipeIngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientsViewHolder recipeIngredientsViewHolder, int i) {

        Ingredient ingredient = ingredients.get(i);
        recipeIngredientsViewHolder.ingredientName.setText(ingredient.getIngredient());
        recipeIngredientsViewHolder.ingredientMeasure.setText(ingredient.getMeasure());
        recipeIngredientsViewHolder.ingredientQnt.setText(ingredient.getQuantity().toString());


    }

    @Override
    public int getItemCount() {
        return ingredients == null ? 0 : ingredients.size();
    }

    public void setIngredients(List<Ingredient> ingredientList){
        ingredients = ingredientList;
        notifyDataSetChanged();
    }

    public class RecipeIngredientsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ingredient_name)
        TextView ingredientName;

        @BindView(R.id.ingredient_measure)
        TextView ingredientMeasure;

        @BindView(R.id.ingredient_qnt)
        TextView ingredientQnt;

        RecipeIngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
