package br.rigolao.desafio5etapa.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import br.rigolao.desafio5etapa.R
import br.rigolao.desafio5etapa.data.Usuario
import br.rigolao.desafio5etapa.interfaces.OnFragmentInteractionListener
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText

class ProfileFragment: Fragment() {

    private lateinit var emailTextField: TextInputEditText
    private lateinit var nomeTextField: TextInputEditText
    private lateinit var senhaTextField: TextInputEditText
    private lateinit var editarButton: Button
    private lateinit var cancelarButton: Button
    private lateinit var usuario: Usuario

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        usuario = Usuario(1.0, "Matheus", "123456", "matheus@email.com", "Administrador")

        emailTextField = root.findViewById(R.id.emailInputId)
        nomeTextField = root.findViewById(R.id.nomeInputId)
        senhaTextField = root.findViewById(R.id.senhaInputId)

        editarButton = root.findViewById(R.id.editarButtonId)
        cancelarButton = root.findViewById(R.id.cancelarButtonId)

        emailTextField.setText(usuario.email)
        nomeTextField.setText(usuario.nome)
        senhaTextField.setText(usuario.senha)

        val toolbar: MaterialToolbar = root.findViewById(R.id.menu)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.list -> {
                    (activity as? OnFragmentInteractionListener)?.onFragmentInteraction(EstagiosListFragment())
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

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editarButton.setOnClickListener {
            editarButton.text = "SALVAR"
            emailTextField.isEnabled = true
            nomeTextField.isEnabled = true
            senhaTextField.isEnabled = true
            cancelarButton.isVisible = true

            if(emailTextField.text.toString() != usuario.email
                || nomeTextField.text.toString() != usuario.nome
                || senhaTextField.text.toString() != usuario.senha) {
                usuario.nome = nomeTextField.text.toString()
                usuario.email = emailTextField.text.toString()
                usuario.senha = senhaTextField.text.toString()

                Toast.makeText(
                    context,
                    "Dados editados",
                    Toast.LENGTH_SHORT
                ).show()

                emailTextField.isEnabled = false
                nomeTextField.isEnabled = false
                senhaTextField.isEnabled = false
                cancelarButton.isVisible = false
            }

        }

        cancelarButton.setOnClickListener {
            emailTextField.isEnabled = false
            nomeTextField.isEnabled = false
            senhaTextField.isEnabled = false
            cancelarButton.isVisible = false

            emailTextField.setText(usuario.email)
            nomeTextField.setText(usuario.nome)
            senhaTextField.setText(usuario.senha)
        }
    }

}