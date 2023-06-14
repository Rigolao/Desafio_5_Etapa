package br.rigolao.desafio5etapa.services

import br.rigolao.desafio5etapa.responses.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UsersService {
    @FormUrlEncoded
    @POST("users/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("users")
    fun cadastrar(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("type") type: String
    ): Call<UserResponse>
}