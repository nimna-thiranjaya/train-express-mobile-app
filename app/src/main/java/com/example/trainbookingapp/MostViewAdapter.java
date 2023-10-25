package com.example.trainbookingapp;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class MostViewAdapter extends RecyclerView.Adapter<MostViewAdapter.ViewHolder> {

    private List<MostViewedDomain> mostViewedList;

    public MostViewAdapter(List<MostViewedDomain> mostViewedList) {
        this.mostViewedList = mostViewedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MostViewedDomain item = mostViewedList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mostViewedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView subTitle;
        private ImageView imageView;

        private ConstraintLayout constraintLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            subTitle = itemView.findViewById(R.id.subTitleTxt);
            imageView = itemView.findViewById(R.id.img_view);
            constraintLayout = itemView.findViewById(R.id.clickme);

            // Set OnClickListener to the entire card view item
            itemView.setOnClickListener(this);
        }

        public void bind(MostViewedDomain item) {
            title.setText(item.getTitle());
            subTitle.setText(item.getSubtitle());
            constraintLayout.setTag(item.getId());
        }

        @Override
        public void onClick(View view) {
            Log.d("MostViewAdapter", "onClick: " + view.getTag());
            // Handle item click here
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Get the clicked item data and pass it to the next activity/fragment
                MostViewedDomain clickedItem = mostViewedList.get(position);
                Intent intent = new Intent(view.getContext(), Reservation_details.class);
                intent.putExtra("title", clickedItem.getTitle());
                intent.putExtra("subtitle", clickedItem.getSubtitle());
                intent.putExtra("id", clickedItem.getId());
                view.getContext().startActivity(intent);
            }
        }
    }
}

