package br.rigolao.desafio5etapa.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.rigolao.desafio5etapa.R
import br.rigolao.desafio5etapa.data.Estagio
import com.google.android.material.appbar.MaterialToolbar

class EstagioFragment: Fragment() {

    private lateinit var estagio: Estagio
    private lateinit var numeroTelefone: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_estagio, container, false)

        estagio = arguments?.getParcelable<Estagio>("estagio")
            ?: Estagio(0.0, "", "", "", null, "", "", "", "", "", "")

        val toolbar = root.findViewById<MaterialToolbar>(R.id.menu)
        toolbar.setNavigationIcon(R.drawable.voltar)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        val tituloTextView: TextView = root.findViewById(R.id.titulo)
        val localidadeTextView: TextView = root.findViewById(R.id.localidadeValue)
        val areaTextView: TextView = root.findViewById(R.id.areaValue)
        val remuneracaoTextView: TextView = root.findViewById(R.id.remuneracaoValue)
        val emailContatoTextView: TextView = root.findViewById(R.id.emailContatoValue)
        val telefoneContatoTextView: TextView = root.findViewById(R.id.telefoneContatoValue)
        val anuncianteTextView: TextView = root.findViewById(R.id.anuncianteValue)
        val dataInicioTextView: TextView = root.findViewById(R.id.dataInicioValue)
        val dataFimTextView: TextView = root.findViewById(R.id.dataFimValue)
        val descricaoTextView: TextView = root.findViewById(R.id.descricaoValue)

        tituloTextView.text = estagio.titulo
        localidadeTextView.text = estagio.localidade
        areaTextView.text = estagio.area
        remuneracaoTextView.text = estagio.remuneracao.toString()
        emailContatoTextView.text = estagio.emailContato
        anuncianteTextView.text = estagio.anunciante
        dataInicioTextView.text = estagio.dataInicio
        dataFimTextView.text = estagio.dataFim
        descricaoTextView.text = estagio.descricao
        telefoneContatoTextView.text = estagio.telefoneContato
        numeroTelefone = estagio.telefoneContato

        telefoneContatoTextView.setOnClickListener{
            val permission = Manifest.permission.CALL_PHONE
            if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(permission), 1)
            } else {
                openPhoneDialer(telefoneContatoTextView.text.toString())
            }
        }


        return root
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openPhoneDialer(numeroTelefone)
        } else {
            Toast.makeText(context, "A permissão é necessária para fazer chamadas telefônicas.", Toast.LENGTH_SHORT).show()
        }
    }

    fun openPhoneDialer(number: String) {
        val intent: Intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("tel:${number}")
        }
        startActivity(intent)
    }

}