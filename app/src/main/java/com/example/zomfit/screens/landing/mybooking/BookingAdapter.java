package com.example.zomfit.screens.landing.mybooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zomfit.R;
import com.example.zomfit.databinding.ItemBookNowBinding;
import com.example.zomfit.models.booking.Booking;
import com.example.zomfit.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private Context context;
    private List<Booking> bookingList;

    public BookingAdapter(Context context) {
        this.context = context;
        bookingList = new ArrayList<>();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookNowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_book_now, parent, false);
        return new BookingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        holder.bind(bookingList.get(position));
    }

    public void update(List<Booking> bookingList) {
        this.bookingList.clear();
        this.bookingList.addAll(bookingList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    class BookingViewHolder extends RecyclerView.ViewHolder {

        private ItemBookNowBinding binding;

        public BookingViewHolder(ItemBookNowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Booking booking) {
            Glide.with(context).load(booking.centerImageUrl).into(binding.centerImage);
            ImageUtils.fetchSvg(context, booking.activityIconUrl, binding.activityImage);
            binding.activityName.setText(booking.activityName);
            binding.timing.setText(String.format("%s, %s", booking.time, booking.date));
            binding.location.setText(String.format("%s, %s", booking.centerName, booking.cityName));
        }
    }
}
