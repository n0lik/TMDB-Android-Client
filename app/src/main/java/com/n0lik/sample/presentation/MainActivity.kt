package com.n0lik.sample.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.n0lik.sample.movie.impl.databinding.ActivityMovieBinding

class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy {
        binding.navHostContainer.getFragment<NavHostFragment>().findNavController()
    }

    private val appBarConfiguration by lazy {
        AppBarConfiguration(navController.graph)
    }

    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}