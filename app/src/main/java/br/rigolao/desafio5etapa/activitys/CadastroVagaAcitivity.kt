package br.rigolao.desafio5etapa.activitys

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.rigolao.desafio5etapa.R
import br.rigolao.desafio5etapa.data.Estagio
import com.google.android.material.textfield.TextInputEditText

class CadastroVagaAcitivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_adicionar)

        val estagio: Estagio? = intent.getParcelableExtra("ESTAGIO")

        val criarButton: Button = findViewById(R.id.criarButtonId)
        val cancelarButton: Button = findViewById(R.id.cancelarButtonId)

        val paginaTitle: TextView = findViewById(R.id.titulo)
        val tituloValue: TextInputEditText = findViewById(R.id.descricaoValue)
        val descricaoValue: TextInputEditText = findViewById(R.id.descricaoValue)
        val areaValue: TextInputEditText = findViewById(R.id.descricaoValue)
        val localidadeValue: TextInputEditText = findViewById(R.id.descricaoValue)
        val dataInicioValue: TextInputEditText = findViewById(R.id.descricaoValue)
        val dataFimValue: TextInputEditText = findViewById(R.id.confirmar_senhaValue)
        val telefoneValue: TextInputEditText = findViewById(R.id.telefoneValue)
        val emailValue: TextInputEditText = findViewById(R.id.emailValue)

        cancelarButton.setOnClickListener {
            this.onBackPressed()
        }

        criarButton.setOnClickListener {
            Toast.makeText(this, "Vaga criada!", Toast.LENGTH_SHORT).show()
            this.onBackPressed()
        }

        if(estagio != null) {
            paginaTitle.text = "Editar Vaga"
            tituloValue.setText(estagio.titulo)
            descricaoValue.setText(estagio.descricao)
            areaValue.setText(estagio.area)
            localidadeValue.setText(estagio.localidade)
            dataInicioValue.setText(estagio.dataInicio)
            dataFimValue.setText(estagio.dataFim)
            telefoneValue.setText(estagio.telefoneContato)
            emailValue.setText(estagio.emailContato)
        }

    }
}