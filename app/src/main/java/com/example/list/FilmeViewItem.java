package com.example.list;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.list.data.entities.Filme;
import com.example.list.databinding.ItemFilmeBinding;
import com.google.common.base.Objects;

import java.util.List;

//import br.com.sistemaprevix.servixmobile.data.entities.Pessoa;
//import br.com.sistemaprevix.servixmobile.databinding.ItemPessoalBinding;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFilterable;

public class FilmeViewItem extends AbstractFlexibleItem<FilmeViewHolder> implements IFilterable{

    private final Filme filme;

    private final String titulo;

    private final String mRegistro;

    private final String mFuncao;

    public FilmeViewItem(Filme pessoa) {
        filme = pessoa;
        titulo = pessoa.getTitulo();
        mRegistro = pessoa.getPopularidade()+"";
        mFuncao = pessoa.getResumo();
    }

    public String getTitulo(){
        return titulo;
    }

    public String getRegistro(){
        return mRegistro;
    }

    public Filme getModel(){
        return filme;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) {
        return filme.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(filme.getTitulo());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_filme;
    }

    @Override
    public FilmeViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
        ItemFilmeBinding binding = DataBindingUtil.bind(view);
        return new FilmeViewHolder(binding, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, FilmeViewHolder holder, int position, List payloads) {
        holder.bind(filme);
    }

    @Override
    public boolean filter(String constraint) {
        String registro, nome, word, funcao;
        registro = mRegistro.toLowerCase();
        nome = titulo.toLowerCase();
        funcao = mFuncao.toLowerCase();
        word = constraint.toLowerCase();


        return registro.contains(word) || nome.contains(word) || funcao.contains(word);
    }
}
