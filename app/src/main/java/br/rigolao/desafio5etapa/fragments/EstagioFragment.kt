package br.rigolao.desafio5etapa.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.rigolao.desafio5etapa.R
import br.rigolao.desafio5etapa.data.Estagio
import com.google.android.material.appbar.MaterialToolbar

class EstagioFragment: Fragment() {

    private lateinit var estagio: Estagio

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_estagio, container, false)

        estagio = arguments?.getParcelable<Estagio>("estagio")
            ?: Estagio(0.0, "", "", "", null, "", "", "", "", "", "")

        val toolbar = root.findViewById<MaterialToolbar>(R.id.menu)
        toolbar.setNavigationIcon(R.drawable.voltar) // icone de voltar
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed() // volta para o fragment anterior
        }

        val tituloTextView: TextView = root.findViewById(R.id.titulo)

        tituloTextView.text = estagio.titulo

        return root
    }
}