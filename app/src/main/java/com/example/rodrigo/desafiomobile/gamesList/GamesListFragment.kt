package com.example.rodrigo.desafiomobile.gamesList

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.rodrigo.desafiomobile.R
import com.example.rodrigo.desafiomobile.entity.GamesEntity
import com.example.rodrigo.desafiomobile.entity.GamesListEntity
import com.example.rodrigo.desafiomobile.gamesListDetail.GamesDetailFragment

import kotlinx.android.synthetic.main.custom_progress_bar.*
import kotlinx.android.synthetic.main.fragment_games_list.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GamesListFragment : Fragment(), GamesListView, OnRecyclerViewSelected {

    private val gamesListPresenter: GamesListPresenter = GamesListPresenter(this)

    private lateinit var adapter: GamesListAdapter

    override fun displayGames(gamesListEntity: GamesListEntity) {
        adapter.data = (gamesListEntity)
    }

    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = GamesListAdapter(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_games_list, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Lista de Games"

        // Coloca o adapter na Recycler View
        rvGames.adapter = adapter
        rvGames.layoutManager = LinearLayoutManager(context)
        rvGames.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter.onRecyclerViewSelected = (this)

        // Presenter atualiza informações da lista
        gamesListPresenter.updateList()

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        lateinit var GAME_EXTRA_KEY : GamesEntity
        fun newInstance(args: GamesEntity): GamesListFragment = GamesListFragment().apply {
            GAME_EXTRA_KEY = args
        }

    }

    override fun onClick(gamesEntity: GamesEntity) {

        val gamesDetailFragment = GamesDetailFragment()
        val transaction = childFragmentManager.beginTransaction()

        newInstance(gamesEntity)

        transaction.addToBackStack(null)
        transaction.replace(R.id.gamesList, gamesDetailFragment).commit()

    }

    override fun showMessage(msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()}
    override fun showLoading(){linear_layout_loading.visibility = View.VISIBLE}
    override fun hideLoading(){linear_layout_loading.visibility = View.GONE }
}