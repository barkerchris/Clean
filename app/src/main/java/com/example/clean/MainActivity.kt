package com.example.clean

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import kotlinx.android.synthetic.main.activity_main.*

//Main activity that hosts all navigation
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        //Start JobServiceIntent used for notifications
        val serviceIntent = Intent(this, MyService::class.java)
        val myService = MyService()
        myService.startWork(this, serviceIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.toolbar_layout), menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profileFragment -> {
                return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
            }
            R.id.settingsFragment -> {
                return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}