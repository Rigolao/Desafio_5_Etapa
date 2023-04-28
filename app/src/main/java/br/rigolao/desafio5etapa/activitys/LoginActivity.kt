package br.rigolao.desafio5etapa.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import br.rigolao.desafio5etapa.R
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnNavega: Button = findViewById(R.id.entrarButtonId)
        val btnCadastrar: Button = findViewById(R.id.cadatrarButtonId)


        val emailTextField : TextInputEditText = findViewById(R.id.emailInputId)
        val senhaTextField : TextInputEditText = findViewById(R.id.senhaInputId)

        btnNavega.setOnClickListener {

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
}