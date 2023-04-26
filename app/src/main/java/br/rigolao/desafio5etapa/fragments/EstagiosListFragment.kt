package br.rigolao.desafio5etapa.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.rigolao.desafio5etapa.R
import br.rigolao.desafio5etapa.data.Estagio
import br.rigolao.desafio5etapa.estagios.EstagiosAdapter
import br.rigolao.desafio5etapa.interfaces.OnFragmentInteractionListener
import com.google.android.material.appbar.MaterialToolbar

class EstagiosListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list_card_estagio, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvEstagios: RecyclerView? = getView()?.findViewById(R.id.listEstagios)
        rvEstagios?.layoutManager = LinearLayoutManager(context)
        rvEstagios?.adapter = EstagiosAdapter(
            listOf(
                Estagio(1.0,
                    "Vaga 1",
                    "Ribeirão Preto",
                    "TI",
                    null,
                    "teste@teste.com",
                    "99999-9999",
                "Matheus",
                "14/01/2023",
                "14/02/2023",
                "Vaga"),
                Estagio(1.0,
                    "Vaga 1",
                    "Ribeirão Preto",
                    "TI",
                    null,
                    "teste@teste.com",
                    "99999-9999",
                    "Matheus",
                    "14/01/2023",
                    "14/02/2023",
                    "Vaga"),
                Estagio(1.0,
                    "Vaga 1",
                    "Ribeirão Preto",
                    "TI",
                    null,
                    "teste@teste.com",
                    "99999-9999",
                    "Matheus",
                    "14/01/2023",
                    "14/02/2023",
                    "Vaga"),
                Estagio(1.0,
                    "Vaga 1",
                    "Ribeirão Preto",
                    "TI",
                    null,
                    "teste@teste.com",
                    "99999-9999",
                    "Matheus",
                    "14/01/2023",
                    "14/02/2023",
                    "Vaga")
            ), ::printEstagio
        )

        val toolbar: MaterialToolbar = view.findViewById(R.id.menu)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.perfil -> {
                    (activity as? OnFragmentInteractionListener)?.onFragmentInteraction(ProfileFragment())
                    true
                }
                R.id.adicionar -> {
                    Toast.makeText(
                        context,
                        "Adicionar",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun printEstagio(estagio: Estagio) {
        Toast.makeText(
            context,
            "Item clicado foi ${estagio.titulo}",
            Toast.LENGTH_SHORT
        ).show()
    }
}