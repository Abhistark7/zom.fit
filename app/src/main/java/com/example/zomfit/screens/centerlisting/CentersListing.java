package com.example.zomfit.screens.centerlisting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.zomfit.R;
import com.example.zomfit.databinding.ActivityCentersListingBinding;
import com.example.zomfit.models.Center;
import com.example.zomfit.models.getcenters.GetCentersRequest;
import com.example.zomfit.models.getcenters.GetCentersResponse;
import com.example.zomfit.network.ApiService;
import com.example.zomfit.screens.center.CenterActivity;
import com.example.zomfit.utils.BasicUtils;

import org.parceler.Parcels;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CentersListing extends AppCompatActivity {

    private static final String ARG_CITY_ID = "cityId";
    private static final String ARG_CITY_NAME = "city_name";
    private static final String ARG_CENTER = "center";
    private ActivityCentersListingBinding binding;
    private Retrofit retrofit;
    private CenterListingAdapter adapter;
    private ArrayList<String> centerIds;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_centers_listing);
        initialize();
    }

    private void initialize() {
        retrofit = BasicUtils.connectApi();
        extract();
        showLoadingView();
        setUpCenterRecyclerView();
    }

    private void extract() {
        Intent intent = getIntent();
        centerIds = intent.getExtras().getStringArrayList(ARG_CITY_ID);
        cityName = intent.getExtras().getString(ARG_CITY_NAME);
        Log.d("user logged in", centerIds.get(0));
        setToolbarTitle(cityName);
        getAllCentersByCity(centerIds);
    }

    private void setUpCenterRecyclerView() {
        adapter = new CenterListingAdapter(this, center -> openCenterActivity(center));
        binding.centersRecycler.setNestedScrollingEnabled(false);
        binding.centersRecycler.setAdapter(adapter);
        binding.centersRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setToolbarTitle(String cityName) {
        binding.toolbar.toolbarTitle.setText(String.format(getString(R.string.centers_in_label), cityName));
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
    }

    private void getAllCentersByCity(ArrayList<String> centerIds) {
        ApiService apiService = retrofit.create(ApiService.class);
        GetCentersRequest getCentersRequest = new GetCentersRequest();
        getCentersRequest.centerIdList = centerIds;
        Call<GetCentersResponse> responseCall = apiService.getCenterByCity(getCentersRequest);
        responseCall.enqueue(new Callback<GetCentersResponse>() {
            @Override
            public void onResponse(Call<GetCentersResponse> call, Response<GetCentersResponse> response) {
                adapter.update(response.body().centerList);
                showContentView();
            }

            @Override
            public void onFailure(Call<GetCentersResponse> call, Throwable t) {
                showErrorView();
                Log.d("error", t.toString());
            }
        });
    }

    private void openCenterActivity(Center center) {
        Intent intent = new Intent(CentersListing.this, CenterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_CENTER, Parcels.wrap(center));
        intent.putExtras(bundle);
        startActivity(intent);
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
}
