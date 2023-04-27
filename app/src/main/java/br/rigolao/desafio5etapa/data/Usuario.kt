package br.rigolao.desafio5etapa.data

data class Usuario(
    val id: Double,
    var nome: String,
    var senha: String,
    var email: String,
    val tipo: String
    )