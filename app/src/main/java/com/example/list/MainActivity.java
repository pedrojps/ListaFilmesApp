package com.example.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.ActionMenuViewBindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.list.data.network.Status;
import com.example.list.databinding.ActivityMainBinding;

import java.util.ArrayList;

import eu.davidea.flexibleadapter.common.FlexibleItemDecoration;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;

    private ActivityMainBinding mBinding;

    private FilmeFlexibleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setVm(mViewModel);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        mViewModel=new MainViewModel(this.getApplication());
        setupAdapter();

        subscribeItems();
    }

    private void setupAdapter() {
        mAdapter = new FilmeFlexibleAdapter(new ArrayList<>(), this);

        FlexibleItemDecoration itemDecoration = new FlexibleItemDecoration(this)
                .withDefaultDivider(R.layout.item_filme);

        mBinding.listPessoas.setLayoutManager(new LinearLayoutManager(this));
        mBinding.listPessoas.setAdapter(mAdapter);
        mBinding.listPessoas.addItemDecoration(itemDecoration);

    }
    private void subscribeItems() {
        mViewModel.getItems().observe(this, resource -> {
            mViewModel.dataLoading.set(resource.status == Status.LOADING);

            if (resource.status == Status.SUCCESS) {
                mViewModel.dataAvaliable.set( null!=(resource.data) && !resource.data.isEmpty());
                mAdapter.updateDataSet(resource.data, false);
            } else if (resource.status == Status.ERROR) {
                mViewModel.dataAvaliable.set(false);
                //showError(resource.message);
            }
        });
    }

}