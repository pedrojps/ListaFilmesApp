package com.example.list.data.entities;

import com.google.gson.annotations.SerializedName;

public class Filme {

    @SerializedName("title")
    private  String titulo;

    @SerializedName("popularity")
    private String popularidade;

    @SerializedName("overview")
    private String Resumo;

    public String getPopularidade() {
        return popularidade;
    }

    public void setPopularidade(String popularidade) {
        this.popularidade = popularidade;
    }

    public String getResumo() {
        return Resumo;
    }

    public void setResumo(String resumo) {
        Resumo = resumo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
}
