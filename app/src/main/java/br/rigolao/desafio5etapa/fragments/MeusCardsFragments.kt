package br.rigolao.desafio5etapa.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.rigolao.desafio5etapa.R
import br.rigolao.desafio5etapa.activitys.CadastroVagaAcitivity
import br.rigolao.desafio5etapa.activitys.LoginActivity
import br.rigolao.desafio5etapa.adapters.EstagiosAdapter
import br.rigolao.desafio5etapa.data.Estagio
import br.rigolao.desafio5etapa.interfaces.OnFragmentInteractionListener
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MeusCardsFragments : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_meus_cards, container, false)

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
                    "Vaga")
            ), ::openEstagio
        )

        val toolbar: MaterialToolbar = view.findViewById(R.id.menu)
        val actionButton: FloatingActionButton = view.findViewById(R.id.actionButton)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.perfil -> {
                    (activity as? OnFragmentInteractionListener)?.onFragmentInteractionWithoutBackStack(ProfileFragment())
                    true
                }
                R.id.list -> {
                    (activity as? OnFragmentInteractionListener)?.onFragmentInteractionWithoutBackStack(EstagiosListFragment())
                    true
                }
                else -> false
            }
        }

        actionButton.setOnClickListener {
            val cadastrarVagaAcitivity = Intent(view.context, CadastroVagaAcitivity::class.java)
            view.context.startActivity(cadastrarVagaAcitivity)
        }
    }

    private fun openEstagio(estagio: Estagio) {
        val bundle: Bundle = Bundle()
        bundle.putParcelable("estagio", estagio)
        val fragment = EstagioFragment()
        fragment.arguments = bundle
        (activity as? OnFragmentInteractionListener)?.onFragmentInteractionWithBackStack(fragment)
    }




}