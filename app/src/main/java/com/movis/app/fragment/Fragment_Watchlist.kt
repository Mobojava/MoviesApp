package com.movis.app.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movis.app.adapter.Adapter_watchlist
import com.movis.app.databinding.FragmentWatchlistBinding
import com.movis.app.room.MoviesDao
import com.movis.app.room.MoviesTable
import com.movis.app.room.MyDatabase
import com.movis.app.view.MovieDetailActivity

class Fragment_Watchlist : Fragment(), Adapter_watchlist.eventClick {

    lateinit var binding: FragmentWatchlistBinding
    lateinit var moviesDao: MoviesDao
    lateinit var myAdapter: Adapter_watchlist
     var id_movies : Int = 0
     var position_movies : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchlistBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.watchlistNotText.visibility = View.GONE
        moviesDao = MyDatabase.getDatabase(binding.root.context).moviesDao

        if (moviesDao.getAllMovies().isEmpty()) {
            binding.watchlistNotText.visibility = View.VISIBLE
        }

        myAdapter = Adapter_watchlist(moviesDao.getAllMovies() as MutableList<MoviesTable>, this)

        binding.RecyclerWatchlist.adapter = myAdapter
        binding.RecyclerWatchlist.layoutManager = LinearLayoutManager(
            binding.root.context,
            RecyclerView.VERTICAL, false
        )

    }

    override fun watchListEvent(data: Int) {
        id_movies = data


        val intent = Intent(requireContext(), MovieDetailActivity::class.java)
        intent.putExtra("movie_id", data)
        startActivity(intent)

    }

    override fun onResume() {
        super.onResume()

        binding.watchlistNotText.visibility = View.GONE
        moviesDao = MyDatabase.getDatabase(binding.root.context).moviesDao

        if (moviesDao.getAllMovies().isEmpty()) {
            binding.watchlistNotText.visibility = View.VISIBLE
        }

        myAdapter = Adapter_watchlist(moviesDao.getAllMovies() as MutableList<MoviesTable>, this)

        binding.RecyclerWatchlist.adapter = myAdapter
        binding.RecyclerWatchlist.layoutManager = LinearLayoutManager(
            binding.root.context,
            RecyclerView.VERTICAL, false
        )


    }
}