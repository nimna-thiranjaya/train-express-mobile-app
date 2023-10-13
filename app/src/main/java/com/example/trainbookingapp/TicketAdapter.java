package com.example.trainbookingapp;

        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import com.bumptech.glide.Glide;
        import java.util.ArrayList;
        import java.util.List;


public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private List<TicketDomain> mostViewedList;

    public TicketAdapter(List<TicketDomain> mostViewedList) {
        this.mostViewedList = mostViewedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TicketDomain item = mostViewedList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mostViewedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView DepStation;
        private TextView DesStation;
        private TextView Seats;
        private TextView DepDate;
        private TextView UClass;
        private TextView DepTime;
        private TextView UserNic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            DepStation = itemView.findViewById(R.id.deps);
            DesStation = itemView.findViewById(R.id.dess);
            DepDate = itemView.findViewById(R.id.dates);
            UClass = itemView.findViewById(R.id.Second);
            DepTime = itemView.findViewById(R.id.times);
            UserNic = itemView.findViewById(R.id.nicf);
            Seats = itemView.findViewById(R.id.seats);

            // Set OnClickListener to the entire card view item
            itemView.setOnClickListener(this);
        }

        public void bind(TicketDomain item) {
            DepStation.setText(item.getDepStation());
            DesStation.setText(item.getDesStation());
            DepDate.setText(item.getDepDate());
            UClass.setText(item.getUClass());
            DepTime.setText(item.getDepTime());
            UserNic.setText(item.getUserNic());
            Seats.setText(item.getSeats());
            // Load your image into imageView using an image loading library like Picasso or Glide
            // For example, using Glide:
            // Glide.with(itemView.getContext()).load(item.getImageUrl()).into(imageView);
        }

        @Override
        public void onClick(View view) {
            // Handle item click here
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Get the clicked item data and pass it to the next activity/fragment
                TicketDomain clickedItem = mostViewedList.get(position);

//                // Example: Start a new activity with the clicked item data
//                Intent intent = new Intent(view.getContext(), Reservation_details.class);
//                intent.putExtra("title", clickedItem.getTitle());
//                intent.putExtra("subtitle", clickedItem.getSubtitle());
//                // Add more data as needed
//
//                view.getContext().startActivity(intent);
            }
        }
    }
}

