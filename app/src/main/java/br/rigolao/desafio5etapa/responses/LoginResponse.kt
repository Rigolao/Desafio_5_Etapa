package br.rigolao.desafio5etapa.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("email")
    val email: String,
    @SerializedName("senha")
    val senha: String
)