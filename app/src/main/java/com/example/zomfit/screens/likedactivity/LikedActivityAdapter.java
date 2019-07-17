package com.example.zomfit.screens.likedactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zomfit.R;
import com.example.zomfit.databinding.ItemActivityBinding;
import com.example.zomfit.models.activity.Activity;
import com.example.zomfit.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class LikedActivityAdapter extends RecyclerView.Adapter<LikedActivityAdapter.LikedActivityViewHolder> {

    private Context context;
    private List<Activity> activityList;

    public LikedActivityAdapter(Context context) {
        this.context = context;
        activityList = new ArrayList<>();
    }

    @NonNull
    @Override
    public LikedActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemActivityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_activity, parent, false);
        return new LikedActivityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LikedActivityViewHolder holder, int position) {
        holder.bind(activityList.get(position));
    }

    public void update(List<Activity> activityList) {
        this.activityList.clear();
        this.activityList.addAll(activityList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    class LikedActivityViewHolder extends RecyclerView.ViewHolder {

        private ItemActivityBinding binding;

        public LikedActivityViewHolder(ItemActivityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Activity activity) {
            binding.centerText.setText(activity.name);
            ImageUtils.fetchSvg(context, activity.iconUrl, binding.cardImage);
        }
    }
}
