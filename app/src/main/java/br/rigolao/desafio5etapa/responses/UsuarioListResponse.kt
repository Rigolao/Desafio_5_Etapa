package br.rigolao.desafio5etapa.responses

import com.google.gson.annotations.SerializedName

data class UsuarioListResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("senha")
    val senha: String,
    @SerializedName("tipoUsuario")
    val tipoUsuario: Int
)