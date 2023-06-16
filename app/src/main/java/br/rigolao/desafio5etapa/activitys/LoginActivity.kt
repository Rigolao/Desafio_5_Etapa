package br.rigolao.desafio5etapa.activitys

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.rigolao.desafio5etapa.R
import br.rigolao.desafio5etapa.responses.EstagioListResponse
import br.rigolao.desafio5etapa.responses.LoginResponse
import br.rigolao.desafio5etapa.responses.UsuarioCadastroResponse
import br.rigolao.desafio5etapa.responses.UsuarioListResponse
import br.rigolao.desafio5etapa.services.UsuariosService
import br.rigolao.desafio5etapa.services.config.ServiceCreator
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var loading: ProgressBar;
    lateinit var background: View;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loading = findViewById(R.id.loading)
        background = findViewById(R.id.background)

        val btnNavega: Button = findViewById(R.id.entrarButtonId)
        val btnCadastrar: Button = findViewById(R.id.cadatrarButtonId)
        val btnRecupera: Button = findViewById(R.id.recuperarButtonId)

        val emailTextField : TextInputEditText = findViewById(R.id.emailInputId)
        val senhaTextField : TextInputEditText = findViewById(R.id.senhaInputId)

        val usuarioService = ServiceCreator.createService<UsuariosService>();

        btnRecupera.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)

            alertDialogBuilder.setTitle("Recuperar sua senha")
            alertDialogBuilder.setMessage("Digite seu e-mail:")

            val input = EditText(this)
            alertDialogBuilder.setView(input)

            alertDialogBuilder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                val email = input.text.toString()

                usuarioService.recuperarSenha(email).enqueue(RecuperarCallBack())

                dialog.dismiss()
            })

            alertDialogBuilder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        btnNavega.setOnClickListener {

            carregando()

            if(emailTextField.text.toString().isEmpty() || senhaTextField.text.toString().isEmpty()) {
                Toast.makeText(this@LoginActivity, "Existem dados em brancos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            usuarioService.login(LoginResponse(emailTextField.text.toString(), senhaTextField.text.toString())).enqueue(UsersCallBack())
        }

        btnCadastrar.setOnClickListener {

            val navegarParaCadastroActivity = Intent(this, CadastroActivity::class.java)
            startActivity(navegarParaCadastroActivity)
        }
    }

    inner class UsersCallBack: Callback<UsuarioListResponse> {
        override fun onResponse(call: Call<UsuarioListResponse>, response: Response<UsuarioListResponse>) {
            esconderCarregando()
            if(response.isSuccessful) {
                println(response.body())
                val sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
                val edit = sharedPreferences.edit()

                edit.putString("NOME", response.body()?.nome)
                edit.putString("SENHA", response.body()?.senha)
                edit.putString("EMAIL", response.body()?.email)
                edit.putInt("TIPO", response.body()?.tipoUsuario!!)
                edit.putInt("ID", response.body()?.id!!)

                edit.apply()

                val navegarParaMainActivity = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(navegarParaMainActivity)
            } else {
                println(response)
                Toast.makeText(this@LoginActivity, "Algo deu errado, tente novamente!", Toast.LENGTH_SHORT).show()
                Log.e("Retrofit erro", response.message() ?: "Sem mensagem")
            }
        }

        override fun onFailure(call: Call<UsuarioListResponse>, t: Throwable) {
            esconderCarregando()
            Toast.makeText(this@LoginActivity, "Algo deu errado, tente novamente!", Toast.LENGTH_SHORT).show()
            Log.e("Retrofit erro", t.message ?: "Sem mensagem")
        }
    }

    inner class RecuperarCallBack: Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            esconderCarregando()
            if(response.isSuccessful) {
                Toast.makeText(this@LoginActivity, "Sua senha foi enviada ao seu e-mail!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@LoginActivity, "Algo deu errado, tente novamente!", Toast.LENGTH_SHORT).show()
                Log.e("Retrofit erro", response.message() ?: "Sem mensagem")
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            esconderCarregando()
            Toast.makeText(this@LoginActivity, "Algo deu errado, tente novamente!", Toast.LENGTH_SHORT).show()
            Log.e("Retrofit erro", t.message ?: "Sem mensagem")
        }
    }

    private fun carregando() {
        background.visibility = View.VISIBLE
        loading.visibility = View.VISIBLE
    }

    private fun esconderCarregando() {
        background.visibility = View.GONE
        loading.visibility = View.GONE
    }
}