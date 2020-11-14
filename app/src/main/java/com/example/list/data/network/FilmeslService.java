package com.example.list.data.network;

import com.example.list.data.entities.Filme;

import java.util.List;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FilmeslService {

    @GET("/3/movie/now_playing?api_key=c2e78b4a8c14e65dd6e27504e6df95ad&language=en-US&page=1")
    Single<Response<FilmesDto>> getAllFilmes();

}
