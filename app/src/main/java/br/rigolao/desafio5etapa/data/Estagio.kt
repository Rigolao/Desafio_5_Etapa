package br.rigolao.desafio5etapa.data

import android.os.Parcel
import android.os.Parcelable

data class Estagio (
    val id: Double,
    val idAnunciante: Int,
    val titulo: String,
    val localidade: String,
    val area: String,
    val remuneracao: Double?,
    val emailContato: String,
    val telefoneContato: String,
    val anunciante: String,
    val dataInicio: String,
    val dataFim: String,
    val descricao: String,
    val exibir: String,
    val status: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(id)
        parcel.writeInt(idAnunciante)
        parcel.writeString(titulo)
        parcel.writeString(localidade)
        parcel.writeString(area)
        parcel.writeValue(remuneracao)
        parcel.writeString(emailContato)
        parcel.writeString(telefoneContato)
        parcel.writeString(anunciante)
        parcel.writeString(dataInicio)
        parcel.writeString(dataFim)
        parcel.writeString(descricao)
        parcel.writeString(exibir)
        parcel.writeInt(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Estagio> {
        override fun createFromParcel(parcel: Parcel): Estagio {
            return Estagio(parcel)
        }

        override fun newArray(size: Int): Array<Estagio?> {
            return arrayOfNulls(size)
        }
    }
}
