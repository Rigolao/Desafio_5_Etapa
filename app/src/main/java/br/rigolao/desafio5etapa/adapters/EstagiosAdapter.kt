package br.rigolao.desafio5etapa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.rigolao.desafio5etapa.R
import br.rigolao.desafio5etapa.data.Estagio

class EstagiosAdapter(
    private val estagiosList: List<Estagio>,
    private val onItemClickListener: (Estagio) -> Unit
) : RecyclerView.Adapter<EstagiosAdapter.EstagioViewHolder>() {

    inner class EstagioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloView: TextView = itemView.findViewById(R.id.titulo)
        val dataFimView: TextView = itemView.findViewById(R.id.dataFimValue)
        val descricaoView: TextView = itemView.findViewById(R.id.descricaoValue)

        init {
            itemView.setOnClickListener {
                onItemClickListener(estagiosList[adapterPosition])
            }
        }

        fun setInfo(estagio: Estagio) {
            tituloView.text = estagio.titulo
            dataFimView.text = estagio.dataFim
            descricaoView.text = estagio.descricao
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
        holder.setInfo(estagiosList[position])
    }

}