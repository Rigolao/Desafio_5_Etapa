package br.rigolao.desafio5etapa.services

import br.rigolao.desafio5etapa.responses.EstagioCadastroResponse
import br.rigolao.desafio5etapa.responses.EstagioEdicaoresponse
import br.rigolao.desafio5etapa.responses.EstagioListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EstagiosService {

    @GET("vagas")
    fun getAll(): Call<List<EstagioListResponse>>

    @POST("vagas")
    fun post(@Body estagioResponse: EstagioCadastroResponse): Call<Unit>

    @PUT("vagas/{id}")
    fun edit(@Path("id") id: Int, @Body estagioResponse: EstagioEdicaoresponse): Call<Unit>

    @DELETE("vagas/{id}")
    fun delete(@Path("id") id: Int): Call<Unit>

}