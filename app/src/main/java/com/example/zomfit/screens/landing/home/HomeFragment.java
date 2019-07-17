package com.example.zomfit.screens.landing.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zomfit.R;
import com.example.zomfit.databinding.FragmentHomeBinding;
import com.example.zomfit.models.Center;
import com.example.zomfit.models.City;
import com.example.zomfit.network.ApiService;
import com.example.zomfit.screens.center.CenterActivity;
import com.example.zomfit.screens.centerlisting.CentersListing;
import com.example.zomfit.utils.BasicUtils;

import org.parceler.Parcels;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {
    private static final String ARG_CITY_ID = "cityId";
    private static final String ARG_CITY_NAME = "city_name";
    private static final String ARG_CENTER = "center";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private CitiesAdapter citiesAdapter;
    private CenterAdapter centerAdapter;
    private OnFragmentInteractionListener mListener;
    private FragmentHomeBinding binding;
    private static Retrofit retrofit = null;

    public HomeFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container,false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        showLoadingView();
        retrofit = BasicUtils.connectApi();
        setUpCitiesRecyclerView();
        setUpCenterRecyclerView();
        loadCities();
        loadCenters();
    }

    private void setUpCitiesRecyclerView() {
        citiesAdapter = new CitiesAdapter(getContext(), city -> openCenterListing(city));
        binding.citiesRecycler.setNestedScrollingEnabled(false);
        binding.citiesRecycler.setAdapter(citiesAdapter);
        binding.citiesRecycler.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setUpCenterRecyclerView() {
        centerAdapter = new CenterAdapter(getContext(), center -> openCenterActivity(center));
        binding.centersRecycler.setNestedScrollingEnabled(false);
        binding.centersRecycler.setAdapter(centerAdapter);
        binding.centersRecycler.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void loadCities() {
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<City>> allCities = apiService.getAllCities();
        allCities.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                citiesAdapter.update(response.body());
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                showErrorView();
            }
        });
    }

    private void loadCenters() {
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Center>> allCenters = apiService.getAllCenters();
        allCenters.enqueue(new Callback<List<Center>>() {
            @Override
            public void onResponse(Call<List<Center>> call, Response<List<Center>> response) {
                centerAdapter.update(response.body());
                showContentView();
            }

            @Override
            public void onFailure(Call<List<Center>> call, Throwable t) {
                showErrorView();
            }
        });
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

    private void openCenterListing(City city) {
        Intent intent = new Intent(getActivity(), CentersListing.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ARG_CITY_ID, city.centerIdList);
        bundle.putString(ARG_CITY_NAME, city.name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void openCenterActivity(Center center) {
        Intent intent = new Intent(getActivity(), CenterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_CENTER, Parcels.wrap(center));
        intent.putExtras(bundle);
        startActivity(intent);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
