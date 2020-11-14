package com.example.list.data.network;


import androidx.annotation.NonNull;

import com.example.list.data.entities.Filme;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class FilmeRemoteDataSource extends BaseRemoteDataSource {

    private static FilmeRemoteDataSource INSTANCE;

    public FilmeRemoteDataSource( ) {
        super();
    }

    public static synchronized FilmeRemoteDataSource getInstance(  ){
        if(INSTANCE == null){
            INSTANCE = new FilmeRemoteDataSource();
        }
        return INSTANCE;
    }

    public void clearInstance(){
        INSTANCE = null;
    }

    public Single<Resource<FilmesDto>> getAllFilmes(){
        FilmeslService mainService;

        mainService = getMainService(FilmeslService.class, "");

        return mainService.getAllFilmes()
                .observeOn(Schedulers.computation())
                .onErrorReturn(this::wrapInErrorResponse)
                .map(this::proccessResponse);
    }


}
