package com.example.zomfit.screens.landing.mybooking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.zomfit.R;
import com.example.zomfit.databinding.FragmentMyBookingBinding;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyBookingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyBookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyBookingFragment extends Fragment implements UpcomingBookingFragment.OnFragmentInteractionListener,
            CompletedBookingFragment.OnFragmentInteractionListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;


    private FragmentMyBookingBinding binding;
    private FragmentAdapter adapter;

    public MyBookingFragment() {
        // Required empty public constructor
    }

    public static MyBookingFragment newInstance(String param1, String param2) {
        MyBookingFragment fragment = new MyBookingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_booking, container,false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        setupViewPager(binding.viewpager);
        binding.tabs.setupWithViewPager(binding.viewpager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new FragmentAdapter(getChildFragmentManager());
        adapter.addFragment(new UpcomingBookingFragment(), "Upcoming Booking");
        adapter.addFragment(new CompletedBookingFragment(), "Completed Booking");
        viewPager.setAdapter(adapter);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
