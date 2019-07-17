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
import com.example.zomfit.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private Context context;
    private List<Activity> activityList;
    private TimingAdapter adapter;
    private TimingClickHandler clickHandler;
    private LikeClickHandler likeClickHandler;
    private String userId;

    public ActivityAdapter(Context context, TimingClickHandler timingClickHandler, String userId, LikeClickHandler clickHandler) {
        this.context = context;
        activityList = new ArrayList<>();
        this.clickHandler = timingClickHandler;
        this.likeClickHandler = clickHandler;
        this.userId = userId;
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
        holder.bind(activityList.get(position), userId, likeClickHandler);
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
        private boolean isLiked;

        public ActivityViewHolder(ItemActivityCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Activity activity, String userId, LikeClickHandler likeClickHandler) {
            binding.activityName.setText(activity.name);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            ImageUtils.fetchSvg(context, activity.iconUrl, binding.cardImage);
            setupTimingRecycler(activity);
            handelLikeButton(activity, binding, userId, likeClickHandler);
        }

        private void handelLikeButton(Activity activity, ItemActivityCardBinding binding,
                                      String userId, LikeClickHandler likeClickHandler) {
            isLiked = activity.likedUserids.contains(userId);
            if(isLiked) {
                binding.likeButton.setImageDrawable(binding.getRoot().getContext().getDrawable(R.drawable.ic_heart_filled));
            } else {
                binding.likeButton.setImageDrawable(binding.getRoot().getContext().getDrawable(R.drawable.ic_heart_empty));
            }
            binding.likeButton.setOnClickListener(v -> {
                if(isLiked) {
                    likeClickHandler.unlike(userId, activity.activityId);
                    binding.likeButton.setImageDrawable(binding.getRoot().getContext().getDrawable(R.drawable.ic_heart_empty));
                    isLiked = false;
                } else {
                    likeClickHandler.like(userId, activity.activityId);
                    binding.likeButton.setImageDrawable(binding.getRoot().getContext().getDrawable(R.drawable.ic_heart_filled));
                    isLiked = true;
                }
            });
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
