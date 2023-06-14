package br.rigolao.desafio5etapa.responses

import com.google.gson.annotations.SerializedName
import java.util.*

data class EstagioListResponse (
    @SerializedName("id")
    val id : Int?,
    @SerializedName("areaConhecimento")
    val areaConhecimento: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("descricao")
    val descricao: String,
    @SerializedName("exibir")
    val exibir: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("remuneracao")
    val remuneracao: Double,
    @SerializedName("telefone")
    val telefone: String,
    @SerializedName("dataInicio")
    val dataInicio: String,
    @SerializedName("dataFim")
    val dataFim: String,
    @SerializedName("localidade")
    val localidade: String,
    @SerializedName("dadosUsuario")
    val dadosUsuario: DadosUsuario
) {
    data class DadosUsuario (
        @SerializedName("id")
        val id : Int,
        @SerializedName("nome")
        val nome: String)
}