package br.rigolao.desafio5etapa.estagios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.rigolao.desafio5etapa.R

class EstagiosListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_card_estagio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvEstagios: RecyclerView? = getView()?.findViewById(R.id.listEstagios)
        rvEstagios?.layoutManager = LinearLayoutManager(context)
        rvEstagios?.adapter = EstagiosAdapter(
            listOf(
                Estagio("Vaga 1", "14/01/2023", "Oi")
            ), ::printEstagio
        )
    }

    private fun printEstagio(estagio: Estagio) {
        Toast.makeText(
            context,
            "Item clicado foi ${estagio.titulo}",
            Toast.LENGTH_SHORT
        ).show()
    }
}