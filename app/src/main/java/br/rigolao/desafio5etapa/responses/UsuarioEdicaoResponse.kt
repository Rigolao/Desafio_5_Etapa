package br.rigolao.desafio5etapa.responses

import com.google.gson.annotations.SerializedName

data class UsuarioEdicaoResponse (
    @SerializedName("nome")
    val nome: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("senha")
    val senha: String
)