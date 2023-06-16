package br.rigolao.desafio5etapa.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.rigolao.desafio5etapa.R
import br.rigolao.desafio5etapa.activitys.CadastroVagaAcitivity
import br.rigolao.desafio5etapa.activitys.LoginActivity
import br.rigolao.desafio5etapa.adapters.EstagiosAdapter
import br.rigolao.desafio5etapa.data.Estagio
import br.rigolao.desafio5etapa.interfaces.OnFragmentInteractionListener
import br.rigolao.desafio5etapa.responses.EstagioListResponse
import br.rigolao.desafio5etapa.services.EstagiosService
import br.rigolao.desafio5etapa.services.config.ServiceCreator
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MeusCardsFragments : Fragment(){

    private var listaEstagiosResponse: List<EstagioListResponse> = listOf();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_meus_cards, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getListaEstagios()

        val rvEstagios: RecyclerView? = getView()?.findViewById(R.id.listEstagios)

        atualizarRecyclerView(rvEstagios)

        val toolbar: MaterialToolbar = view.findViewById(R.id.menu)
        val actionButton: FloatingActionButton = view.findViewById(R.id.actionButton)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.perfil -> {
                    (activity as? OnFragmentInteractionListener)?.onFragmentInteractionWithoutBackStack(ProfileFragment())
                    true
                }
                R.id.list -> {
                    (activity as? OnFragmentInteractionListener)?.onFragmentInteractionWithoutBackStack(EstagiosListFragment())
                    true
                }
                else -> false
            }
        }

        actionButton.setOnClickListener {
            val cadastrarVagaAcitivity = Intent(view.context, CadastroVagaAcitivity::class.java)
            view.context.startActivity(cadastrarVagaAcitivity)
        }
    }

    inner class EstagiosListCallBack: Callback<List<EstagioListResponse>> {
        override fun onResponse(call: Call<List<EstagioListResponse>>, response: Response<List<EstagioListResponse>>) {
            if(response.isSuccessful) {
                listaEstagiosResponse = response.body()!!
                listaEstagiosResponse = listaEstagiosResponse.filter { it.dadosUsuario.id == 1 }
                atualizarRecyclerView();
            }
        }

        override fun onFailure(call: Call<List<EstagioListResponse>>, t: Throwable) {
            Toast.makeText(requireContext(), "Ocorreu um erro!", Toast.LENGTH_SHORT).show()
            Log.e("Retrofit erro", t.message ?: "Sem mensagem")
        }
    }

    inner class EstagioDeleteCallBack: Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if(response.isSuccessful) {
                atualizarRecyclerView();
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Toast.makeText(requireContext(), "Ocorreu um erro", Toast.LENGTH_SHORT).show()
            Log.e("Retrofit erro", t.message ?: "Sem mensagem")
        }
    }

    private fun openEstagio(estagio: Estagio) {
        val bundle: Bundle = Bundle()
        bundle.putParcelable("estagio", estagio)
        val fragment = EstagioFragment()
        fragment.arguments = bundle
        (activity as? OnFragmentInteractionListener)?.onFragmentInteractionWithBackStack(fragment)
    }

    fun transformarListaEstagioResponse(listaResponse: List<EstagioListResponse>): List<Estagio> {
        println(listaResponse);
        val listaEstagios = mutableListOf<Estagio>()

        for (response in listaResponse) {
            val estagio = Estagio(
                id = response.id!!.toDouble(),
                idAnunciante = response.dadosUsuario.id,
                titulo = response.descricao ?: "",
                localidade = response.localidade,
                area = response.areaConhecimento,
                remuneracao = response.remuneracao ?: 0.0,
                emailContato = response.email,
                telefoneContato = response.telefone,
                anunciante = if (response.exibir == "S") response.dadosUsuario.nome else "Anônimo",
                dataInicio = response.dataInicio,
                dataFim = response.dataFim,
                descricao = response.descricao,
                exibir = response.exibir,
                status = response.status
            )
            if(estagio.status == 1) listaEstagios.add(estagio)
        }

        return listaEstagios
    }

    private fun atualizarRecyclerView(rvEstagios: RecyclerView?) {
        rvEstagios?.layoutManager = LinearLayoutManager(context)

        val sharedPreferences = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        val tipo = sharedPreferences.getInt("TIPO", 0)
        val id = sharedPreferences.getInt("ID", 0)

        if (listaEstagiosResponse != null) {
            rvEstagios?.adapter = EstagiosAdapter(
                id, tipo, transformarListaEstagioResponse(listaEstagiosResponse), ::openEstagio, ::onDeleteEstagio
            )
        }
    }

    private fun atualizarRecyclerView() {
        val rvEstagios: RecyclerView? = getView()?.findViewById(R.id.listEstagios)
        rvEstagios?.layoutManager = LinearLayoutManager(context)

        val sharedPreferences = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        val tipo = sharedPreferences.getInt("TIPO", 0)
        val id = sharedPreferences.getInt("ID", 0)

        if (listaEstagiosResponse != null) {
            rvEstagios?.adapter = EstagiosAdapter(
                id, tipo, transformarListaEstagioResponse(listaEstagiosResponse), ::openEstagio, ::onDeleteEstagio
            )
        }
    }

    fun getListaEstagios() {
        val estagioService = ServiceCreator.createService<EstagiosService>();

        estagioService.getAll().enqueue(EstagiosListCallBack())
    }

    fun onDeleteEstagio(id: Int) {
        val estagioService = ServiceCreator.createService<EstagiosService>()

        estagioService.delete(id).enqueue(EstagioDeleteCallBack())

        getListaEstagios()

        atualizarRecyclerView()
    }


}