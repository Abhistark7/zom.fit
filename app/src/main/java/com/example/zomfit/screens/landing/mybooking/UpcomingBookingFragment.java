package com.example.zomfit.screens.landing.mybooking;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zomfit.R;
import com.example.zomfit.databinding.FragmentUpcomingBookingBinding;
import com.example.zomfit.models.booking.MyBookingRequest;
import com.example.zomfit.models.booking.MyBookingResponse;
import com.example.zomfit.network.ApiService;
import com.example.zomfit.screens.BookNow;
import com.example.zomfit.screens.centerlisting.CenterListingAdapter;
import com.example.zomfit.utils.BasicUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpcomingBookingFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private FragmentUpcomingBookingBinding binding;
    private BookingAdapter adapter;
    private Retrofit retrofit;

    public UpcomingBookingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming_booking, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        showLoadingView();
        setUpRecyclerView();
        getUpcomingBookings();
    }

    private void setUpRecyclerView() {
        adapter = new BookingAdapter(getActivity());
        binding.upcomingRecycler.setNestedScrollingEnabled(false);
        binding.upcomingRecycler.setAdapter(adapter);
        binding.upcomingRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void getUpcomingBookings() {
        retrofit = BasicUtils.connectApi();
        MyBookingRequest bookingRequest = new MyBookingRequest();
        bookingRequest.userId = getUserId();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<MyBookingResponse> bookingResponseCall = apiService.getUpcomingBooking(bookingRequest);
        bookingResponseCall.enqueue(new Callback<MyBookingResponse>() {
            @Override
            public void onResponse(Call<MyBookingResponse> call, Response<MyBookingResponse> response) {
                adapter.update(response.body().bookingList);
                showContentView();
            }

            @Override
            public void onFailure(Call<MyBookingResponse> call, Throwable t) {
                showErrorView();
            }
        });
    }

    private String getUserId() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(getString(R.string.user_id_label), "");
    }

    private void showLoadingView() {
        binding.contentView.setVisibility(View.GONE);
        binding.errorView.setVisibility(View.GONE);
        binding.loadingView.setVisibility(View.VISIBLE);
    }

    private void showErrorView() {
        binding.contentView.setVisibility(View.GONE);
        binding.errorView.setVisibility(View.VISIBLE);
        binding.loadingView.setVisibility(View.GONE);
    }

    private void showContentView() {
        binding.contentView.setVisibility(View.VISIBLE);
        binding.errorView.setVisibility(View.GONE);
        binding.loadingView.setVisibility(View.GONE);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public OnFragmentInteractionListener getmListener() {
        return mListener;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
