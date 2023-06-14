package br.rigolao.desafio5etapa.fragments

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
import br.rigolao.desafio5etapa.data.Estagio
import br.rigolao.desafio5etapa.adapters.EstagiosAdapter
import br.rigolao.desafio5etapa.interfaces.OnFragmentInteractionListener
import br.rigolao.desafio5etapa.responses.EstagioListResponse
import br.rigolao.desafio5etapa.services.EstagiosService
import br.rigolao.desafio5etapa.services.config.ServiceCreator
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EstagiosListFragment : Fragment() {

    private var listaEstagiosResponse: List<EstagioListResponse> = listOf();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list_card_estagio, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getListaEstagios()

        val rvEstagios: RecyclerView? = getView()?.findViewById(R.id.listEstagios)

        atualizarRecyclerView(rvEstagios)

        rvEstagios?.adapter = EstagiosAdapter(
            transformarListaEstagioResponse(listaEstagiosResponse), ::openEstagio, ::onDeleteEstagio
        )

        val toolbar: MaterialToolbar = view.findViewById(R.id.menu)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.perfil -> {
                    (activity as? OnFragmentInteractionListener)?.onFragmentInteractionWithoutBackStack(ProfileFragment())
                    true
                }
                R.id.minhasVagas -> {
                    (activity as? OnFragmentInteractionListener)?.onFragmentInteractionWithoutBackStack(MeusCardsFragments())
                    true
                }
                else -> false
            }
        }
    }

    inner class EstagiosListCallBack: Callback<List<EstagioListResponse>> {
        override fun onResponse(call: Call<List<EstagioListResponse>>, response: Response<List<EstagioListResponse>>) {
            Toast.makeText(requireContext(), "Deu Certo!", Toast.LENGTH_SHORT).show()
            if(response.isSuccessful) {
                listaEstagiosResponse = response.body()!!
                atualizarRecyclerView();
            }
        }

        override fun onFailure(call: Call<List<EstagioListResponse>>, t: Throwable) {
            Toast.makeText(requireContext(), "Deu Ruim!", Toast.LENGTH_SHORT).show()
            Log.e("Retrofit erro", t.message ?: "Sem mensagem")
        }
    }

    inner class EstagioDeleteCallBack: Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            Toast.makeText(requireContext(), "Deu Certo!", Toast.LENGTH_SHORT).show()
            if(response.isSuccessful) {
                atualizarRecyclerView();
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            Toast.makeText(requireContext(), "Deu Ruim!", Toast.LENGTH_SHORT).show()
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

        if (listaEstagiosResponse != null) {
            rvEstagios?.adapter = EstagiosAdapter(
                transformarListaEstagioResponse(listaEstagiosResponse), ::openEstagio, ::onDeleteEstagio
            )
        }
    }

    private fun atualizarRecyclerView() {
        val rvEstagios: RecyclerView? = getView()?.findViewById(R.id.listEstagios)
        rvEstagios?.layoutManager = LinearLayoutManager(context)

        if (listaEstagiosResponse != null) {

            println(listaEstagiosResponse)

            rvEstagios?.adapter = EstagiosAdapter(
                transformarListaEstagioResponse(listaEstagiosResponse), ::openEstagio, ::onDeleteEstagio
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