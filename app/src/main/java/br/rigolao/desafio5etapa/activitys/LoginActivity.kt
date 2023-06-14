package br.rigolao.desafio5etapa.activitys

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

//        val estagioService = ServiceCreator.createService<EstagiosService>();

        btnNavega.setOnClickListener {

            carregando()

//            estagioService.getAll().enqueue(UsersCallBack())

            val navegarParaMainActivity = Intent(this, MainActivity::class.java)
            startActivity(navegarParaMainActivity)

//            if(emailTextField.text.toString() == "teste" && senhaTextField.text.toString() == "teste") {
//                val navegaParaOutraTela = Intent(this, ListActivity::class.java)
//                startActivity(navegaParaOutraTela)
//            } else {
//                Toast.makeText(
//                    this,
//                    "Usuário errado! ${emailTextField.text} - ${senhaTextField.text}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
        }

        btnCadastrar.setOnClickListener {

            val navegarParaCadastroActivity = Intent(this, CadastroActivity::class.java)
            startActivity(navegarParaCadastroActivity)
        }
    }

    inner class UsersCallBack: Callback<List<EstagioListResponse>> {
        override fun onResponse(call: Call<List<EstagioListResponse>>, response: Response<List<EstagioListResponse>>) {
            esconderCarregando()
            Toast.makeText(this@LoginActivity, "Deu Certo!", Toast.LENGTH_SHORT).show()
            if(response.isSuccessful) {

                println(response.body())
            }
        }

        override fun onFailure(call: Call<List<EstagioListResponse>>, t: Throwable) {
            esconderCarregando()
            Toast.makeText(this@LoginActivity, "Deu Ruim!", Toast.LENGTH_SHORT).show()
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