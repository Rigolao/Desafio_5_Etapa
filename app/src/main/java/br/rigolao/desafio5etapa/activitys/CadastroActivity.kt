package br.rigolao.desafio5etapa.activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import br.rigolao.desafio5etapa.R
import com.google.android.material.textfield.TextInputEditText

class CadastroActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val btnCriarConta : Button = findViewById(R.id.criarButtonId)
        val btnCancelar : Button = findViewById(R.id.cancelarButtonId)

        btnCancelar.setOnClickListener {

            val navegarParaLoginActivity = Intent(this, LoginActivity::class.java)
            startActivity(navegarParaLoginActivity)
        }

        btnCriarConta.setOnClickListener{

            val navegarParaLoginActivity = Intent(this, LoginActivity::class.java)
            startActivity(navegarParaLoginActivity)
        }

        val nome: TextInputEditText = findViewById(R.id.nomeValue)
        val email: TextInputEditText = findViewById(R.id.emailValue)
        val senha: TextInputEditText = findViewById(R.id.senhaValue)
        val confirmarSenha: TextInputEditText = findViewById(R.id.confirmar_senhaValue)
        val telefone: TextInputEditText = findViewById(R.id.telefoneValue)

        fun isEmailValid(email: String): Boolean {
            val emailPattern = "^[a-zA-Z0-9._%+-]+@sou\\.unaerp\\.edu\\.br$"
            val regex = Regex(emailPattern)
            return regex.matches(email)
        }


    }

}