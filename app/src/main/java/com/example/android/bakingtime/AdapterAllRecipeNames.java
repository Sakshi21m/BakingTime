package com.example.android.bakingtime;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterAllRecipeNames extends RecyclerView.Adapter<AdapterAllRecipeNames.GridViewHolder>{



    private int mNumberItems;
    private List<ArrayList>  recipeData;
    private final RecipeAdapterOnClickHandler clickHandler;

    public AdapterAllRecipeNames(RecipeAdapterOnClickHandler cHandler)
    {

        clickHandler = cHandler;
    }

    public interface RecipeAdapterOnClickHandler {
        void onClick(List recipeDetails);
    }
    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context;

        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_all_recipe_names, viewGroup, false);
        return  new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position){

        holder.bind(position);
    }

    @Override
    public int getItemCount(){
        return mNumberItems;
    }

    class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView recipeName;

        public GridViewHolder(View itemView){

            super(itemView);

            recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
            itemView.setOnClickListener(this);
        }

        void bind (int listPosition)
        {
            if(!recipeData.isEmpty()) {

                recipeName.setText(recipeData.get(listPosition).get(1).toString());
            }
        }

        @Override
        public void onClick(View view){
            int adapterPosition = getAdapterPosition();
            if(!recipeData.isEmpty()) {
                List selectedRecipeDetails = recipeData.get(adapterPosition);
                clickHandler.onClick(selectedRecipeDetails);
            }
        }
    }

    public void setPMData(List<ArrayList> pmData) {
        if(!pmData.isEmpty()) {
            recipeData = pmData;
            mNumberItems = pmData.size();
        }else{
            recipeData = null;
            mNumberItems = 0;
        }
        notifyDataSetChanged();
    }


}
