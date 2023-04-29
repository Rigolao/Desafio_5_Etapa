package br.rigolao.desafio5etapa.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import br.rigolao.desafio5etapa.R
import br.rigolao.desafio5etapa.activitys.CadastroVagaAcitivity
import br.rigolao.desafio5etapa.activitys.LoginActivity
import br.rigolao.desafio5etapa.activitys.MainActivity
import br.rigolao.desafio5etapa.data.Estagio

class EstagiosAdapter(
    private val estagiosList: List<Estagio>,
    private val onItemClickListener: (Estagio) -> Unit
) : RecyclerView.Adapter<EstagiosAdapter.EstagioViewHolder>() {

    inner class EstagioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloView: TextView = itemView.findViewById(R.id.titulo)
        val dataFimView: TextView = itemView.findViewById(R.id.dataFimValue)
        val descricaoView: TextView = itemView.findViewById(R.id.descricaoValue)
        val editarButton: ImageButton = itemView.findViewById(R.id.editarButton)
        val removerButton: ImageButton = itemView.findViewById(R.id.removerButton)

        init {
            itemView.setOnClickListener {
                onItemClickListener(estagiosList[adapterPosition])
            }
        }

        fun setInfo(estagio: Estagio) {
            tituloView.text = estagio.titulo
            dataFimView.text = estagio.dataFim
            descricaoView.text = estagio.descricao

            if(estagio.anunciante == "Matheus") {
                editarButton.isVisible = true
                removerButton.isVisible = true

                editarButton.setOnClickListener {
                    val cadastrarVagaAcitivity = Intent(itemView.context, CadastroVagaAcitivity::class.java)
                    cadastrarVagaAcitivity.putExtra("ESTAGIO", estagio)
                    itemView.context.startActivity(cadastrarVagaAcitivity)
                }

                removerButton.setOnClickListener {
                    Toast.makeText(itemView.context, "Vaga Removida", Toast.LENGTH_SHORT).show()
                }
            }
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