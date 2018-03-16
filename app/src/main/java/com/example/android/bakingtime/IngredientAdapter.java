package com.example.android.bakingtime;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakshimajmudar on 27/02/18.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ListOfIngredients>{

   private int mIngredientCount;
   private  List<ArrayList> mIngredientDetails;

    public IngredientAdapter(){}
    public void setter(List<ArrayList> list){

        if(!list.isEmpty()) {
            mIngredientDetails = list;
            mIngredientCount = list.size();
        }else{
            mIngredientDetails = null;
            mIngredientCount = 0;

        }
        notifyDataSetChanged();
    }

    @Override
    public ListOfIngredients onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredients_list_view_details, parent,false);
        return new ListOfIngredients(view);

    }

    @Override
    public void onBindViewHolder(ListOfIngredients holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mIngredientCount;
    }

    class ListOfIngredients extends RecyclerView.ViewHolder{

        final TextView quantity;
        final TextView measure;
        final TextView ingredientName;

        public ListOfIngredients(View itemView) {
            super(itemView);

            quantity = (TextView) itemView.findViewById(R.id.tv_quantity);
            measure = (TextView) itemView.findViewById(R.id.tv_measure);
            ingredientName = (TextView) itemView.findViewById(R.id.tv_ingredient_name);
        }

        public void bind(int index){

            quantity.setText(mIngredientDetails.get(index).get(0).toString());
            measure.setText(mIngredientDetails.get(index).get(1).toString());
            ingredientName.setText(mIngredientDetails.get(index).get(2).toString());

        }
    }
}
