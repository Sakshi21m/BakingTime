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
 * Created by sakshimajmudar on 26/02/18.
 */

public class MasterListStepsAdapter extends RecyclerView.Adapter<MasterListStepsAdapter.NumberViewHolder> {

    private List<ArrayList> mStepNames;
    private int mNumberOfSteps;
    private OnStepClickListener mCallback;



    public void setData(List<ArrayList> stepNames) {


        if(!stepNames.isEmpty()) {
            mStepNames = stepNames;
            mNumberOfSteps = stepNames.size();
            System.out.println("sakshi setdata is not empty");
        }else{
            mStepNames = null;
            mNumberOfSteps = 0;
            System.out.println("set data is empty");

        }
        notifyDataSetChanged();
    }

    public MasterListStepsAdapter(OnStepClickListener listner) {
        mCallback = listner;
    }

    public interface OnStepClickListener {
        void onStepSelected(    String stepId);
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.linear_layout_step_names;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new NumberViewHolder(view);

    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mNumberOfSteps;
    }


    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Will display the position in the list, ie 0 through getItemCount() - 1
        TextView listItemNumberView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         * @param itemView The View that you inflated in
         */
        public NumberViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_step_name);
            itemView.setOnClickListener(this);
        }

        /**
         * A method we wrote for convenience. This method will take an integer as input and
         * use that integer to display the appropriate text within a list item.
         * @param listIndex Position of the item in the list
         */
        void bind(int listIndex)
        {
           // listItemNumberView.setText(String.valueOf(listIndex));
            listItemNumberView.setText(mStepNames.get(listIndex).get(1).toString());
            listItemNumberView.setTag(mStepNames.get(listIndex).get(0).toString() );

            //listItemNumberView.setText("123");
        }

        @Override
        public void onClick(View view) {

            String stepId = (String) view.findViewById(R.id.tv_step_name).getTag();
            mCallback.onStepSelected(stepId);
                }




    }
}
