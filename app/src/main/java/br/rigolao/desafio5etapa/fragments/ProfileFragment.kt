package br.rigolao.desafio5etapa.fragments

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import br.rigolao.desafio5etapa.R
import br.rigolao.desafio5etapa.activitys.LoginActivity
import br.rigolao.desafio5etapa.activitys.MainActivity
import br.rigolao.desafio5etapa.data.Usuario
import br.rigolao.desafio5etapa.interfaces.OnFragmentInteractionListener
import br.rigolao.desafio5etapa.responses.LoginResponse
import br.rigolao.desafio5etapa.responses.UsuarioEdicaoResponse
import br.rigolao.desafio5etapa.responses.UsuarioListResponse
import br.rigolao.desafio5etapa.services.UsuariosService
import br.rigolao.desafio5etapa.services.config.ServiceCreator
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment: Fragment() {

    private lateinit var emailTextField: TextInputEditText
    private lateinit var nomeTextField: TextInputEditText
    private lateinit var senhaTextField: TextInputEditText
    private lateinit var editarButton: Button
    private lateinit var cancelarButton: Button
    private lateinit var deletarButton: Button
    private lateinit var usuario: Usuario

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val sharedPreferences = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);

        usuario = Usuario(
            sharedPreferences.getInt("ID", 0),
            sharedPreferences.getString("NOME", "nenhum")!!,
            sharedPreferences.getString("SENHA", "nenhum")!!,
            sharedPreferences.getString("EMAIL", "nenhum")!!,
            sharedPreferences.getInt("TIPO", 0))

        emailTextField = root.findViewById(R.id.emailInputId)
        nomeTextField = root.findViewById(R.id.nomeInputId)
        senhaTextField = root.findViewById(R.id.senhaInputId)

        editarButton = root.findViewById(R.id.editarButtonId)
        cancelarButton = root.findViewById(R.id.cancelarButtonId)
        deletarButton = root.findViewById(R.id.deletarButtonId)

        emailTextField.setText(usuario.email)
        nomeTextField.setText(usuario.nome)
        senhaTextField.setText(usuario.senha)

        val toolbar: MaterialToolbar = root.findViewById(R.id.menu)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.list -> {
                    (activity as? OnFragmentInteractionListener)?.onFragmentInteractionWithoutBackStack(EstagiosListFragment())
                    true
                }
                R.id.minhasVagas -> {
                    if(usuario.tipo == 0) {
                        Toast.makeText(requireContext(), "Essa função é somente para anunciantes", Toast.LENGTH_SHORT).show()
                    } else {
                        (activity as? OnFragmentInteractionListener)?.onFragmentInteractionWithoutBackStack(MeusCardsFragments())
                    }
                    true
                }
                else -> false
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usuarioService = ServiceCreator.createService<UsuariosService>();

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

                val sharedPreferences = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);

                if(emailTextField.text.toString().isEmpty()
                    || nomeTextField.text.toString().isEmpty()
                    || senhaTextField.text.toString().isEmpty()) {
                    Toast.makeText(requireContext(), "Existem dados em branco!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                usuarioService.editar(
                    sharedPreferences.getInt("ID", 0),
                    UsuarioEdicaoResponse(
                    nomeTextField.text.toString(),
                    emailTextField.text.toString(),
                    senhaTextField.text.toString()
                )).enqueue(UsersCallBack())
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

        deletarButton.setOnClickListener {

            val alertDialogBuilder = AlertDialog.Builder(requireContext())

            alertDialogBuilder.setTitle("Deletar conta")
            alertDialogBuilder.setMessage("Essa ação irá delatar sua conta, é irreversivel, tem certeza?")

            alertDialogBuilder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                usuarioService.deletar(usuario.id).enqueue(DeletarCallBack())

                dialog.dismiss()
            })

            alertDialogBuilder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

            Toast.makeText(requireContext(), "Algo deu errado, tente novamente!", Toast.LENGTH_SHORT).show()
        }
    }

    inner class UsersCallBack: Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if(response.isSuccessful) {
                val sharedPreferences = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                val edit = sharedPreferences.edit()

                edit.putString("NOME", nomeTextField.text.toString())
                edit.putString("SENHA", senhaTextField.text.toString())
                edit.putString("EMAIL", emailTextField.text.toString())

                edit.apply()

                Toast.makeText(
                    context,
                    "Dados editados",
                    Toast.LENGTH_SHORT
                ).show()

                editarButton.text = "EDITAR"
                emailTextField.isEnabled = false
                nomeTextField.isEnabled = false
                senhaTextField.isEnabled = false
                cancelarButton.isVisible = false

            } else {
                println(response)
                Toast.makeText(requireContext(), "Algo deu errado, tente novamente!", Toast.LENGTH_SHORT).show()
                Log.e("Retrofit erro", response.message() ?: "Sem mensagem")
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Toast.makeText(requireContext(), "Algo deu errado, tente novamente!", Toast.LENGTH_SHORT).show()
            Log.e("Retrofit erro", t.message ?: "Sem mensagem")
        }
    }

    inner class DeletarCallBack: Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if(response.isSuccessful) {
                Toast.makeText(requireContext(), "Conta deletada", Toast.LENGTH_SHORT).show()

                val navegarParaLoginActivity = Intent(requireContext(), LoginActivity::class.java)
                startActivity(navegarParaLoginActivity)
            } else {
                Toast.makeText(requireContext(), "Algo deu errado, tente novamente!", Toast.LENGTH_SHORT).show()
                Log.e("Retrofit erro", response.message() ?: "Sem mensagem")
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Toast.makeText(requireContext(), "Algo deu errado, tente novamente!", Toast.LENGTH_SHORT).show()
            Log.e("Retrofit erro", t.message ?: "Sem mensagem")
        }
    }

}