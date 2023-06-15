package br.rigolao.desafio5etapa.services

import br.rigolao.desafio5etapa.responses.LoginResponse
import br.rigolao.desafio5etapa.responses.UsuarioCadastroResponse
import br.rigolao.desafio5etapa.responses.UsuarioEdicaoResponse
import br.rigolao.desafio5etapa.responses.UsuarioListResponse
import retrofit2.Call
import retrofit2.http.*

interface UsuariosService {
    @POST("user/login")
    fun login(@Body loginResponse: LoginResponse
    ): Call<UsuarioListResponse>

    @POST("user")
    fun cadastrar(
        @Body usuarioCadastroResponse: UsuarioCadastroResponse
    ): Call<Unit>

    @PUT("user/{id}")
    fun editar(
        @Path("id") id: Int,
        @Body usuarioEdicaoResponse: UsuarioEdicaoResponse
    ): Call<Unit>

    @DELETE("user/{id}")
    fun deletar(
        @Path("id") id: Int
    ): Call<Unit>
}