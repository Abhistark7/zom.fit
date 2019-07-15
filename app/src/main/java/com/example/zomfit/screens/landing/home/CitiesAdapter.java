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
import com.example.zomfit.models.City;

import java.util.ArrayList;
import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder> {

    private ClickHandler clickHandler;
    private Context context;
    private List<City> cities;

    public CitiesAdapter(Context context, ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        this.context = context;
        cities = new ArrayList<>();
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
        holder.bind(cities.get(position), clickHandler);
    }

    public void update(List<City> cityList) {
        this.cities.clear();
        this.cities.addAll(cityList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    class CitiesViewHolder extends RecyclerView.ViewHolder {

        private ItemCardViewBinding binding;

        public CitiesViewHolder(ItemCardViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(City city, ClickHandler clickHandler) {
            binding.cardText.setText(city.name);
            Glide.with(context).load(city.imageUrl).into(binding.cardImage);
        }
    }
}
