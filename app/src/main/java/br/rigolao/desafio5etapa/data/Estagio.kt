package br.rigolao.desafio5etapa.data

data class Estagio (
    val id: Double,
    val titulo: String,
    val localidade: String,
    val area: String,
    val remuneracao: Double?,
    val emailContato: String,
    val telefoneContato: String,
    val anunciante: String,
    val dataInicio: String,
    val dataFim: String,
    val descricao: String
)