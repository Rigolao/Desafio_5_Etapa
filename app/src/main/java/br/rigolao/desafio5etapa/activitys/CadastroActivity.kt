package br.rigolao.desafio5etapa.activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.rigolao.desafio5etapa.R
import br.rigolao.desafio5etapa.responses.UsuarioCadastroResponse
import br.rigolao.desafio5etapa.services.UsuariosService
import br.rigolao.desafio5etapa.services.config.ServiceCreator
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroActivity : AppCompatActivity() {
    lateinit var loading: ProgressBar;
    lateinit var background: View;
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val btnCriarConta : Button = findViewById(R.id.criarButtonId)
        val btnCancelar : Button = findViewById(R.id.cancelarButtonId)

        val usuarioService = ServiceCreator.createService<UsuariosService>();

        val nome: TextInputEditText = findViewById(R.id.nomeValue)
        val email: TextInputEditText = findViewById(R.id.emailValue)
        val senha: TextInputEditText = findViewById(R.id.senhaValue)
        val confirmarSenha: TextInputEditText = findViewById(R.id.confirmar_senhaValue)

        fun isEmailValid(email: String): Boolean {
            val emailPattern = "^[a-zA-Z0-9._%+-]+@sou\\.unaerp\\.edu\\.br$"
            val regex = Regex(emailPattern)
            return regex.matches(email)
        }

        btnCancelar.setOnClickListener {

            val navegarParaLoginActivity = Intent(this, LoginActivity::class.java)
            startActivity(navegarParaLoginActivity)
        }

        btnCriarConta.setOnClickListener{

            val tipoButton: MaterialButtonToggleGroup = findViewById(R.id.toggleButton)
            val opcaoSelecionada =  tipoButton.checkedButtonId
            val tipoUsuario = when (opcaoSelecionada) {
                R.id.candidato -> false
                R.id.anunciante -> true
                else -> null
            }

            if(isEmailValid(email.text.toString())) {
                Toast.makeText(this@CadastroActivity, "Email invalido!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(senha.text.toString() != confirmarSenha.text.toString()) {
                Toast.makeText(this@CadastroActivity, "Senhas são diferentes!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            usuarioService.cadastrar(
                UsuarioCadastroResponse(
                    nome.text.toString(),
                    email.text.toString(),
                    senha.text.toString(),
                    tipoUsuario!!)
            ).enqueue(UsersCallBack())


        }
    }

    inner class UsersCallBack: Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            println(response)
            if(response.isSuccessful) {
                Toast.makeText(this@CadastroActivity, "Usuário Cadastrado!", Toast.LENGTH_SHORT).show()
                val navegarParaLoginActivity = Intent(this@CadastroActivity, LoginActivity::class.java)
                startActivity(navegarParaLoginActivity)
            } else {
                Toast.makeText(this@CadastroActivity, "Algo deu errado, tente novamente!", Toast.LENGTH_SHORT).show()
                Log.e("Retrofit erro", response.message() ?: "Sem mensagem")
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Toast.makeText(this@CadastroActivity, "Algo deu errado, tente novamente!", Toast.LENGTH_SHORT).show()
            Log.e("Retrofit erro", t.message ?: "Sem mensagem")
        }
    }

}