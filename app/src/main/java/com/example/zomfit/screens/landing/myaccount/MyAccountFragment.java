package com.example.zomfit.screens.landing.myaccount;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.zomfit.R;
import com.example.zomfit.databinding.FragmentMyAccountBinding;
import com.example.zomfit.screens.likedactivity.LikedActivityActivity;
import com.example.zomfit.screens.SavedCenterActivity;
import com.example.zomfit.screens.landing.LoginActivity;

public class MyAccountFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private FragmentMyAccountBinding binding;
    private String userName;
    private String userEmail;

    public MyAccountFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        getUserCredentials();
        handleLogout();
    }

    private void getUserCredentials() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        userName = sharedPref.getString(getString(R.string.user_name_label), "");
        userEmail = sharedPref.getString(getString(R.string.user_email_label), "");
        setupStaticData();
    }

    private void setupStaticData() {
        binding.profile.nameTitle.setText(userName);
        binding.profile.emailTitle.setText(userEmail);
        setUpOptions();
    }

    private void setUpOptions() {
        binding.savedCenter.optionTitle.setText(getString(R.string.saved_centers_label));
        binding.savedCenter.getRoot().setOnClickListener(v -> openSavedCenter());
        binding.likedActivity.optionTitle.setText(getString(R.string.liked_activities_label));
        binding.likedActivity.getRoot().setOnClickListener(v -> openLikedActivity());
    }

    private void openSavedCenter() {
        Intent intent = new Intent(getActivity(), SavedCenterActivity.class);
        startActivity(intent);
    }

    private void openLikedActivity() {
        Intent intent = new Intent(getActivity(), LikedActivityActivity.class);
        startActivity(intent);
    }

    private void handleLogout() {
        binding.logout.setOnClickListener(v-> logout());
    }

    private void logout() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.is_logged_in_label), false);
        editor.apply();
        startLoginActivity();
    }

    private void startLoginActivity() {
        final Runnable r = () -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        };
        Handler handler = new Handler();
        handler.postDelayed(r, 10);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
