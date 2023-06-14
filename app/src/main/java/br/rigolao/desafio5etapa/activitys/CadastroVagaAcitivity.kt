package br.rigolao.desafio5etapa.activitys

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.rigolao.desafio5etapa.R
import br.rigolao.desafio5etapa.data.Estagio
import br.rigolao.desafio5etapa.responses.EstagioCadastroResponse
import br.rigolao.desafio5etapa.responses.EstagioEdicaoresponse
import br.rigolao.desafio5etapa.responses.EstagioListResponse
import br.rigolao.desafio5etapa.services.EstagiosService
import br.rigolao.desafio5etapa.services.config.ServiceCreator
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroVagaAcitivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_adicionar)

        val estagio: Estagio? = intent.getParcelableExtra("ESTAGIO")

        val criarButton: Button = findViewById(R.id.criarButtonId)
        val cancelarButton: Button = findViewById(R.id.cancelarButtonId)

        val paginaTitle: TextView = findViewById(R.id.titulo)
        val tituloValue: TextInputEditText = findViewById(R.id.tituloValue)
        val descricaoValue: TextInputEditText = findViewById(R.id.descricaoValue)
        val areaValue: TextInputEditText = findViewById(R.id.areaValue)
        val localidadeValue: TextInputEditText = findViewById(R.id.localidadeValue)
        val dataInicioValue: TextInputEditText = findViewById(R.id.dataInicioValue)
        val dataFimValue: TextInputEditText = findViewById(R.id.confirmar_senhaValue)
        val telefoneValue: TextInputEditText = findViewById(R.id.telefoneValue)
        val emailValue: TextInputEditText = findViewById(R.id.emailValue)
        val remuneracaoValue: TextInputEditText = findViewById(R.id.remuneracaoValue)

        val estagioService = ServiceCreator.createService<EstagiosService>();

        if (estagio != null) {
            criarButton.text = "Editar Vaga"
            paginaTitle.text = "Editar Vaga"
            tituloValue.setText(estagio.titulo)
            descricaoValue.setText(estagio.descricao)
            areaValue.setText(estagio.area)
            localidadeValue.setText(estagio.localidade)
            dataInicioValue.setText(estagio.dataInicio)
            dataFimValue.setText(estagio.dataFim)
            telefoneValue.setText(estagio.telefoneContato)
            emailValue.setText(estagio.emailContato)
            remuneracaoValue.setText(estagio.remuneracao.toString())
        }

        cancelarButton.setOnClickListener {
            this.onBackPressed()
        }

        criarButton.setOnClickListener {

            println("Clicando!!!")

            if (estagio?.id != null) {
                println("Editando!!!")
                val estagioResponse = EstagioEdicaoresponse(
                    areaConhecimento = areaValue.text.toString(),
                    email = emailValue.text.toString(),
                    descricao = descricaoValue.text.toString(),
                    exibir = estagio.exibir,
                    remuneracao = estagio.remuneracao ?: 0.0,
                    telefone = telefoneValue.text.toString(),
                    dataInicio = dataInicioValue.text.toString(),
                    dataFim = dataFimValue.text.toString(),
                    localidade = localidadeValue.text.toString()
                )

                println(estagioResponse);

                estagioService.edit(estagio.id.toInt(), estagioResponse).enqueue(EstagioEditCallBack())
            } else {
                println("Criando!!!")
                val novoEstagio = EstagioCadastroResponse(
                    idUsuario = 1,
                    areaConhecimento = areaValue.text.toString(),
                    email = emailValue.text.toString(),
                    descricao = descricaoValue.text.toString(),
                    exibir = "S",
                    remuneracao = remuneracaoValue.text.toString().toDouble(),
                    telefone = telefoneValue.text.toString(),
                    dataInicio = dataInicioValue.text.toString(),
                    dataFim = dataFimValue.text.toString(),
                    localidade = localidadeValue.text.toString()
                )
                estagioService.post(novoEstagio).enqueue(EstagioCreateCallBack())
            }

        }
    }

    inner class EstagioCreateCallBack : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful) {
                println("Deu certo")
                Toast.makeText(this@CadastroVagaAcitivity, "Vaga criada!", Toast.LENGTH_SHORT)
                    .show()
                this@CadastroVagaAcitivity.onBackPressed()
            }

            println(response)
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Toast.makeText(
                this@CadastroVagaAcitivity,
                "Não foi possível criar a vaga, tente novamente!",
                Toast.LENGTH_SHORT
            ).show()
            Log.e("Retrofit erro", t.message ?: "Sem mensagem")
        }
    }

    inner class EstagioEditCallBack : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            println(response)
            if (response.isSuccessful) {
                Toast.makeText(this@CadastroVagaAcitivity, "Vaga editada!", Toast.LENGTH_SHORT)
                    .show()
                this@CadastroVagaAcitivity.onBackPressed()
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Toast.makeText(
                this@CadastroVagaAcitivity,
                "Não foi possível editar a vaga, tente novamente!",
                Toast.LENGTH_SHORT
            ).show()
            Log.e("Retrofit erro", t.message ?: "Sem mensagem")
        }
    }
}