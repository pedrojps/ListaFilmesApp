package com.example.list.data.network;


import androidx.annotation.NonNull;

import com.google.common.net.HostAndPort;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

public abstract class BaseRemoteDataSource {

    public static final String DEFAULT_PROTOCOL = "http";

    protected BaseRemoteDataSource() {
    }

    public <S> S getService(
            @NonNull Class<S> service, @NonNull HostAndPort baseUrl, @NonNull String imei
    ){
        return getService(service, DEFAULT_PROTOCOL, baseUrl, imei);
    }

    public <S> S getService(
            @NonNull Class<S> service, @NonNull String protocol, @NonNull HostAndPort baseUrl, @NonNull String imei
    ) throws IllegalArgumentException {
        try {

            HttpUrl url = new HttpUrl.Builder()
                    .scheme(protocol)
                    .host(baseUrl.getHost())
                    .port(baseUrl.getPort())
                    .build();
            return ServiceGenerator.createService(service, url, imei);
        }catch (Exception e){
            throw new IllegalArgumentException("O endereço do servidor é inválido.");
        }
    }

    public <S> S getMainService(@NonNull Class<S> service, @NonNull String imei){
        HostAndPort hostAndPort = HostAndPort.fromString("api.themoviedb.org").withDefaultPort(80);
        return getService(service, hostAndPort, imei);
    }

    public <ResponseType> Response<ResponseType> wrapInErrorResponse(Throwable throwable){
        if(throwable instanceof SocketTimeoutException){
            return Response.error(NetworkUtils.REQUEST_TIMEOUT, getErrorResponseBody());
        }

        if(throwable instanceof IOException){
            return Response.error(NetworkUtils.CONNECTION_FAILED, getErrorResponseBody());
        }

        return Response.error(NetworkUtils.UNEXPECTED_ERROR, getErrorResponseBody());
    }

    private ResponseBody getErrorResponseBody(){
        return ResponseBody.create(MediaType.parse("application/json"), "");
    }

    public <CredentialsType, DataType> IIdentifiedRequest<CredentialsType, DataType> createRequest(
            @NonNull CredentialsType credentials, DataType data
    ){
        return new IdentifiedRequest<>(credentials, data);
    }


    public <DataType> Resource<DataType> proccessResponse(Response<DataType> response){
        if(response.isSuccessful()){
            return Resource.success(response.body());
        }else {
            return Resource.error(NetworkUtils.getErrorMessageByCode(response.code()), null);
        }
    }
}
