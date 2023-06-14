package br.rigolao.desafio5etapa.services.config

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.137.1:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    inline fun <reified T> createService() : T {
        return retrofit.create(T::class.java)
    }
}