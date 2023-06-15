package br.rigolao.desafio5etapa.activitys

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
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

        val emailTextField : TextInputEditText = findViewById(R.id.emailInputId)
        val senhaTextField : TextInputEditText = findViewById(R.id.senhaInputId)

        val usuarioService = ServiceCreator.createService<UsuariosService>();

        btnNavega.setOnClickListener {

            carregando()

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

    private fun carregando() {
        background.visibility = View.VISIBLE
        loading.visibility = View.VISIBLE
    }

    private fun esconderCarregando() {
        background.visibility = View.GONE
        loading.visibility = View.GONE
    }
}