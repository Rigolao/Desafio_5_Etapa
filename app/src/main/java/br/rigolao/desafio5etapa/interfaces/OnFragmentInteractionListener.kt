package br.rigolao.desafio5etapa.interfaces

import androidx.fragment.app.Fragment

interface OnFragmentInteractionListener {
    fun onFragmentInteractionWithBackStack(fragment: Fragment)
    fun onFragmentInteractionWithoutBackStack(fragment: Fragment)
}