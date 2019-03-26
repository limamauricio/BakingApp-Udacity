package com.limamauricio.bakingapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.limamauricio.bakingapp.R;
import com.limamauricio.bakingapp.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context mContext;
    final private ItemClickListener mItemClickListener;

    @Getter
    private List<Recipe> recipes;

    public RecipeAdapter(Context context, ItemClickListener itemClickListener) {
        this.mContext = context;
        this.mItemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recipe_item, viewGroup, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder recipeViewHolder, int i) {

        Recipe recipe = recipes.get(i);

        recipeViewHolder.recipeName.setText(recipe.getName());
        Glide.with(recipeViewHolder.itemView)
                .load(recipe.getImage())
                .into(recipeViewHolder.imgRecipe);

    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }

    public void setRecipes(List<Recipe> recipeList){
        recipes = recipeList;
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_name)
        TextView recipeName;

        @BindView(R.id.recipe_img_background)
        ImageView imgRecipe;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onClick(View view) {
            int elementId = recipes.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }

        @OnClick(R.id.btnAddWidget)
        public void btnAddtoWidgetClicked(){
            int elementId = recipes.get(getAdapterPosition()).getId();
            mItemClickListener.onAddToWidgetClickListener(elementId);

        }
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
        void onAddToWidgetClickListener(int itemId);
    }
}
