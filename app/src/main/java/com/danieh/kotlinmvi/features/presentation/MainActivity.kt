package com.danieh.kotlinmvi.features.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.danieh.kotlinmvi.R
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupActionBar()
    }

    private fun setupActionBar() {
        val navController = findNavController(this, R.id.nav_host_fragment)
        setupActionBarWithNavController(this, navController)
    }
}
