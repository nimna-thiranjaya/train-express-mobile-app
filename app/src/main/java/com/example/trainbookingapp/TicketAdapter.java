package com.example.trainbookingapp;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.constraintlayout.widget.ConstraintLayout;
        import androidx.recyclerview.widget.RecyclerView;
        import com.bumptech.glide.Glide;
        import com.example.trainbookingapp.Auth.LoginActivity;
        import com.example.trainbookingapp.model.request.UserStatusUpdateReuest;
        import com.example.trainbookingapp.model.response.ErrorResponse;
        import com.example.trainbookingapp.model.response.StandardResponse;
        import com.example.trainbookingapp.network.ApiService;
        import com.example.trainbookingapp.network.ReservationApiService;
        import com.example.trainbookingapp.network.RetrofitClient;
        import com.google.gson.Gson;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;


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

        private ConstraintLayout clickticket;

        private ImageView editBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            DepStation = itemView.findViewById(R.id.deps);
            DesStation = itemView.findViewById(R.id.dess);
            DepDate = itemView.findViewById(R.id.dates);
            UClass = itemView.findViewById(R.id.Second);
            DepTime = itemView.findViewById(R.id.times);
            UserNic = itemView.findViewById(R.id.nicf);
            Seats = itemView.findViewById(R.id.seats);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            clickticket = itemView.findViewById(R.id.clickticket);

            //set on click listener to the edit button
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Get the clicked item data and pass it to the next activity/fragment
                        TicketDomain clickedItem = mostViewedList.get(position);

                        Intent intent = new Intent(v.getContext(), EditReservationActivity.class);
                        intent.putExtra("reservationId", clickedItem.getId());
                        v.getContext().startActivity(intent);
                    }
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Get the clicked item data and pass it to the next activity/fragment
                        TicketDomain clickedItem = mostViewedList.get(position);

                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Confirm");
                        builder.setMessage("Are you sure you want to cancel this Reservation?");
                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                cancelReservation(clickedItem.getId(), v);
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                        // Create and show the dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }
            });

        }

        public void bind(TicketDomain item) {
            DepStation.setText(item.getDepStation());
            DesStation.setText(item.getDesStation());
            DepDate.setText(item.getDepDate());
            UClass.setText(item.getUClass());
            DepTime.setText(item.getDepTime());
            UserNic.setText(item.getUserNic());
            Seats.setText(item.getSeats());
            clickticket.setTag(item.getId());
            // Load your image into imageView using an image loading library like Picasso or Glide
            // For example, using Glide:
            // Glide.with(itemView.getContext()).load(item.getImageUrl()).into(imageView);
        }

        @Override
        public void onClick(View v) {

        }


//        @Override
//        public void onClick(View view) {
//            // Handle item click here
//            int position = getAdapterPosition();
//            if (position != RecyclerView.NO_POSITION) {
//                // Get the clicked item data and pass it to the next activity/fragment
//                TicketDomain clickedItem = mostViewedList.get(position);
//
//            }
//        }
    }

//    private void editReservation(View view) {
//
//        int position = getAdapterPosition();
//    }

    public void  cancelReservation(String reservationId, View view) {
        Log.d("TicketAdapter", "cancelReservation: " + reservationId);
        ReservationApiService reservationApiService = RetrofitClient.getRetrofitInstance().create(ReservationApiService.class);

        Call<StandardResponse> call = reservationApiService.deleteReservationById(reservationId);

        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if(response.isSuccessful()){
                    Log.d("BookReservationActivity", "onResponse: " + response.body().getMessage());
                    showToast(response.body().getMessage(), view);
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    view.getContext().startActivity(intent);

                }else{
                    try {
                        Gson gson = new Gson();
                        ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                        String errorMessage = errorResponse.getMessage();
                        showToast(errorMessage, view);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                Log.d("TicketAdapter", "onFailure: " + t.getLocalizedMessage());

            }
        });

    }

    private void showToast(String message, View view) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }

}

