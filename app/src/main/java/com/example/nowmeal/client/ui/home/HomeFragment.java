package com.example.nowmeal.client.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asksira.loopingviewpager.LoopingViewPager;
import com.example.nowmeal.R;
import com.example.nowmeal.client.adapter.MyBestDealsAdapter;
import com.example.nowmeal.client.adapter.MyPopularCategoriesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Unbinder unbinder;
    LayoutAnimationController  layoutAnimationController;

    @BindView(R.id.recycler_popular)
    RecyclerView recycler_popular;
    @BindView(R.id.viewpager)
    LoopingViewPager viewpager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        unbinder = ButterKnife.bind(this, root);
        init();
        homeViewModel.getPopularList().observe(getViewLifecycleOwner(), popularCategoryModels -> {

            // create adapter
            MyPopularCategoriesAdapter adapter = new MyPopularCategoriesAdapter(getContext(), popularCategoryModels);
            recycler_popular.setAdapter(adapter);
            recycler_popular.setLayoutAnimation(layoutAnimationController);

        });

        homeViewModel.getBestDealList().observe(getViewLifecycleOwner(), bestDealModels -> {
            MyBestDealsAdapter adapter = new MyBestDealsAdapter(getContext(), bestDealModels, true);
            viewpager.setAdapter(adapter);

        });
        return root;
    }

    private void init() {
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);
        recycler_popular.setHasFixedSize(true);
        recycler_popular.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

    }

    @Override
    public void onResume() {
        super.onResume();
        viewpager.resumeAutoScroll();
    }


    @Override
    public void onPause() {
        viewpager.pauseAutoScroll();
        super.onPause();
    }
}
