package com.example.list;

import com.example.list.FlexibleItemViewHolder;
import eu.davidea.flexibleadapter.FlexibleAdapter;

import com.example.list.data.entities.Filme;
import com.example.list.databinding.ItemFilmeBinding;

public class FilmeViewHolder extends FlexibleItemViewHolder<Filme>{

    private final ItemFilmeBinding mBinding;

    public FilmeViewHolder(ItemFilmeBinding binding, FlexibleAdapter adapter) {
        super(binding.getRoot(), adapter);
        mBinding = binding;
    }

    @Override
    public void bind(Filme item) {
        mBinding.setPessoa(item);
    }
}
