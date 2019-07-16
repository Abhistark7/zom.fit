package com.example.zomfit.screens.center;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.zomfit.R;
import com.example.zomfit.databinding.ItemActivityCardBinding;
import com.example.zomfit.models.activity.Activity;
import com.example.zomfit.models.activity.Timing;
import com.example.zomfit.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private Context context;
    private List<Activity> activityList;
    private TimingAdapter adapter;
    private TimingClickHandler clickHandler;

    public ActivityAdapter(Context context, TimingClickHandler timingClickHandler) {
        this.context = context;
        activityList = new ArrayList<>();
        this.clickHandler = timingClickHandler;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemActivityCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_activity_card, parent, false);
        return new ActivityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
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

    class ActivityViewHolder extends RecyclerView.ViewHolder {

        private ItemActivityCardBinding binding;

        public ActivityViewHolder(ItemActivityCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Activity activity) {
            binding.activityName.setText(activity.name);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            ImageUtils.fetchSvg(context, activity.iconUrl, binding.cardImage);
            setupTimingRecycler(activity);
        }

        private void setupTimingRecycler(Activity activity) {
            adapter = new TimingAdapter(context, (timing, activity1) ->
                    clickHandler.onTimingClicked(timing, activity));
            binding.timingRecycler.setAdapter(adapter);
            binding.timingRecycler.setNestedScrollingEnabled(false);
            binding.timingRecycler.setLayoutManager(new LinearLayoutManager(
                    context, LinearLayoutManager.HORIZONTAL, false));
            adapter.update(activity.timingList);
        }
    }
}
