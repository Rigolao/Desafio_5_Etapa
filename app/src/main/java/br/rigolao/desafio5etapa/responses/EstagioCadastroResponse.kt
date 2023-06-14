package br.rigolao.desafio5etapa.responses

import com.google.gson.annotations.SerializedName

data class EstagioCadastroResponse (
    @SerializedName("idUsuario")
    val idUsuario : Int,
    @SerializedName("areaConhecimento")
    val areaConhecimento: String,
    @SerializedName("descricao")
    val descricao: String,
    @SerializedName("telefone")
    val telefone: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("dataInicio")
    val dataInicio: String,
    @SerializedName("dataFim")
    val dataFim: String,
    @SerializedName("exibir")
    val exibir: String,
    @SerializedName("localidade")
    val localidade: String,
    @SerializedName("remuneracao")
    val remuneracao: Double
)