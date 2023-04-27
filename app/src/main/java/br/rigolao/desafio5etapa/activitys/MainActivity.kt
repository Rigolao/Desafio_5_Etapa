package br.rigolao.desafio5etapa.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import br.rigolao.desafio5etapa.R
import br.rigolao.desafio5etapa.fragments.EstagiosListFragment
import br.rigolao.desafio5etapa.interfaces.OnFragmentInteractionListener

class MainActivity: AppCompatActivity(), OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main, EstagiosListFragment())
        fragmentTransaction.commit()
    }

    override fun onFragmentInteraction(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager

        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main, fragment)
//            .addToBackStack(null)
        fragmentTransaction.commit()
    }
}