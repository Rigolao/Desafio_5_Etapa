package br.rigolao.desafio5etapa.estagios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.rigolao.desafio5etapa.R

class EstagiosAdapter(
    private val estagiosList: List<Estagio>,
    private val onItemClickListener: (Estagio) -> Unit
) : RecyclerView.Adapter<EstagiosAdapter.EstagioViewHolder>() {

    inner class EstagioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setInfo(titulo: String, dataFim: String, descricao: String) {
            val tituloView: TextView = itemView.findViewById(R.id.titulo)
            val dataFimView: TextView = itemView.findViewById(R.id.dataFimValue)
            val descricaoView: TextView = itemView.findViewById(R.id.descricaoValue)

            tituloView.text = titulo
            tituloView.setOnClickListener{
                onItemClickListener(Estagio(titulo, dataFim, descricao))
            }
            dataFimView.text = dataFim
            descricaoView.text = descricao
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstagioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_estagio, parent, false)
        return EstagioViewHolder(view)
    }

    override fun getItemCount(): Int {
        return estagiosList.size
    }

    override fun onBindViewHolder(holder: EstagioViewHolder, position: Int) {
        holder.setInfo(estagiosList[position].titulo, estagiosList[position].dataFim, estagiosList[position].descricao)
    }

}