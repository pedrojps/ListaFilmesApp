package com.example.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.example.list.data.entities.Filme;
import com.example.list.data.network.FilmeRemoteDataSource;
import com.example.list.data.network.FilmesDto;
import com.example.list.data.network.Resource;
import com.google.common.collect.Lists;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainViewModel extends AndroidViewModel {

    private LiveData<Resource<List<FilmeViewItem>>> mItems;

    private FilmeRemoteDataSource filmeRemoteDataSource;

    private final Collator sCollator;

    public final ObservableBoolean dataAvaliable = new ObservableBoolean(false);

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    public MainViewModel(@NonNull Application application) {
        super(application);
        sCollator = Collator.getInstance();
        sCollator.setStrength(Collator.TERTIARY);
        filmeRemoteDataSource=FilmeRemoteDataSource.getInstance();
        loadPessoas();
    }


    private void loadPessoas() {
        mItems = LiveDataReactiveStreams.fromPublisher(getPessoasPublisher()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()));
    }

    private Flowable<Resource<List<FilmeViewItem>>> getPessoasPublisher() {
        return Flowable.create(e -> {
            e.onNext(Resource.loading(null));

            filmeRemoteDataSource.getAllFilmes()
                    .observeOn(Schedulers.computation())
                    .map(this::sortAndMapToFlexibleItem)
                    .map(Resource::success)
                    .onErrorReturn(Resource::error)
                    .subscribe(e::onNext);
        }, BackpressureStrategy.BUFFER);
    }

    private List<FilmeViewItem> sortAndMapToFlexibleItem(Resource<FilmesDto> filmes){
        List<Filme> sortedList=new ArrayList<>();
        if(filmes.data!=null)
        sortedList = new ArrayList<>(filmes.data.results);
        Collections.sort(sortedList, this::sortByNomeAsc);
        return Lists.transform(sortedList,FilmeViewItem::new);
    }

    private int sortByNomeAsc(@NonNull Filme filme1, @NonNull Filme filme2) {
        return sCollator.compare(filme1.getTitulo(), filme2.getTitulo());
    }


    public LiveData<Resource<List<FilmeViewItem>>> getItems() {
        return mItems;
    }
}
