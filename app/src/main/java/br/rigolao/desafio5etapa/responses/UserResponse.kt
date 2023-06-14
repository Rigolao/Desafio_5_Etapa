package br.rigolao.desafio5etapa.responses

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("iduser")
    val iduser: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("type")
    val type: String
)