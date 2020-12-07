package com.example.willigetwet

import android.app.ActionBar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.willigetwet.model.CityInfo
import com.example.willigetwet.model.Cloud
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import cs.jejda.start.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var navController: NavController = NavController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(true)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_clouds
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

       /* try {
            val json = resources.openRawResource(R.raw.cz_cities)
                .bufferedReader().use { it.readText() }
            val sType = object : TypeToken<List<CityInfo>>() { }.type
            val list = Gson().fromJson<List<CityInfo>>(json, sType)
            val appDatabase = AppDatabase.getAppDatabase(this)

            GlobalScope.launch(Dispatchers.IO) {
                appDatabase?.TODOItemDao()?.insertData(list)
            }
        } catch (e: Exception) {
            println(e)
        }*/
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}
