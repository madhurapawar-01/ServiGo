package com.example.servigo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ViewHolder> {

    Context context;
    List<Provider> providerList;

    public ProviderAdapter(Context context, List<Provider> providerList) {
        this.context = context;
        this.providerList = providerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_provider, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Provider provider = providerList.get(position);

        holder.txtName.setText(provider.getName());
        holder.txtSkill.setText(provider.getSkill() + " • ₹" + provider.getPrice() + "/hr");
        holder.txtRating.setText("⭐ " + provider.getRating() + " • " + provider.getDistance() + " away");
        holder.txtExperience.setText(provider.getExperience() + " years experience");

        holder.btnBook.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookingActivity.class);
            intent.putExtra("providerId", provider.getUserId());
            intent.putExtra("providerName", provider.getName());
            intent.putExtra("providerSkill", provider.getSkill());
            intent.putExtra("providerPrice", provider.getPrice());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return providerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtSkill, txtRating, txtExperience;
        Button btnBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtSkill = itemView.findViewById(R.id.txtSkill);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtExperience = itemView.findViewById(R.id.txtExperience);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}