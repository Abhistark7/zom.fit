package com.example.zomfit.screens.centerlisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zomfit.R;
import com.example.zomfit.databinding.ItemCenterCardBinding;
import com.example.zomfit.models.Center;
import com.example.zomfit.screens.landing.home.CenterClickHandler;

import java.util.ArrayList;
import java.util.List;

public class CenterListingAdapter extends RecyclerView.Adapter<CenterListingAdapter.CentersViewHolder> {

    private CenterClickHandler clickHandler;
    private Context context;
    private List<Center> centerList;

    public CenterListingAdapter(Context context, CenterClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        this.context = context;
        centerList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CentersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCenterCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_center_card, parent, false);
        return new CentersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CentersViewHolder holder, int position) {
        holder.bind(centerList.get(position), clickHandler);
    }

    public void update(List<com.example.zomfit.models.Center> centerList) {
        this.centerList.clear();
        this.centerList.addAll(centerList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return centerList.size();
    }

    class CentersViewHolder extends RecyclerView.ViewHolder {

        private ItemCenterCardBinding binding;

        public CentersViewHolder(ItemCenterCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Center center, CenterClickHandler clickHandler) {
            binding.centerText.setText(center.name);
            binding.cityText.setText(center.cityName);
            binding.ratingValue.setText(center.rating);
            Glide.with(context).load(center.imageUrl).into(binding.cardImage);
            binding.getRoot().setOnClickListener(v -> clickHandler.onCenterClicker(center));
        }
    }
}
