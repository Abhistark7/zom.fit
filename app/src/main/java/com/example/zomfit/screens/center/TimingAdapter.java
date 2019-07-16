package com.example.zomfit.screens.center;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zomfit.R;
import com.example.zomfit.databinding.ItemTimingCardBinding;
import com.example.zomfit.models.activity.Timing;
import com.example.zomfit.utils.BasicUtils;

import java.util.ArrayList;
import java.util.List;

public class TimingAdapter extends RecyclerView.Adapter<TimingAdapter.TimingViewHolder> {

    private TimingClickHandler clickHandler;
    private Context context;
    private List<Timing> timingList;

    public TimingAdapter(Context context, TimingClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        this.context = context;
        timingList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TimingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTimingCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_timing_card, parent, false);
        return new TimingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TimingViewHolder holder, int position) {
        holder.bind(timingList.get(position), clickHandler);
    }

    public void update(List<Timing> timingList) {
        this.timingList.clear();
        this.timingList.addAll(timingList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return timingList.size();
    }

    class TimingViewHolder extends RecyclerView.ViewHolder {

        private ItemTimingCardBinding binding;

        public TimingViewHolder(ItemTimingCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Timing timing, TimingClickHandler timingClickHandler) {
            binding.timingText.setText(timing.time);
            binding.dateText.setText(timing.date);
            if(!timing.isAvailable) {
                binding.getRoot().setOnClickListener(v ->
                        BasicUtils.makeToast(context,
                                "This slot is already booked, Please select another one"));
                binding.container.setBackgroundColor(binding.getRoot()
                        .getContext().getResources().getColor(R.color.colorPrimaryDark));
            } else {
            binding.getRoot().setOnClickListener(v -> timingClickHandler.onTimingClicked(timing));
            }
        }
    }
}
