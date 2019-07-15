package com.example.zomfit.screens.landing.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zomfit.R;
import com.example.zomfit.databinding.ItemCardViewBinding;
import com.example.zomfit.models.Center;

import java.util.ArrayList;
import java.util.List;

public class CenterAdapter extends RecyclerView.Adapter<CenterAdapter.CitiesViewHolder> {

    private CenterClickHandler clickHandler;
    private Context context;
    private List<Center> centerList;

    public CenterAdapter(Context context, CenterClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        this.context = context;
        centerList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_card_view, parent, false);
        return new CitiesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesViewHolder holder, int position) {
        holder.bind(centerList.get(position), clickHandler, position);
    }

    public void update(List<Center> centerList) {
        this.centerList.clear();
        this.centerList.addAll(centerList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return centerList.size();
    }

    class CitiesViewHolder extends RecyclerView.ViewHolder {

        private ItemCardViewBinding binding;

        public CitiesViewHolder(ItemCardViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Center center, CenterClickHandler clickHandler, int position) {
            binding.cardText.setText(center.name);
            Glide.with(context).load(center.imageUrl).into(binding.cardImage);
            binding.getRoot().setOnClickListener(v ->
                    clickHandler.onCenterClicker(center));
        }
    }
}