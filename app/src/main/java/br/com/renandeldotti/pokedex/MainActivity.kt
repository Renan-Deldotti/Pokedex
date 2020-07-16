package br.com.renandeldotti.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import br.com.renandeldotti.pokedex.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var configuration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(activityMainBinding.root)
        val toolbar: Toolbar = findViewById(R.id.activity_main_toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = activityMainBinding.drawerLayout
        val navView: NavigationView = activityMainBinding.navigationView
        navController = findNavController(R.id.fragmentNavHost)
        configuration =
            AppBarConfiguration(
                setOf(R.id.nav_regions, R.id.nav_pokemonList, R.id.nav_items),
                drawerLayout)
        setupActionBarWithNavController(navController, configuration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(configuration) || super.onSupportNavigateUp()
    }

    fun changeAppBar(toolbar: Toolbar){
        //Log.e("TAG", "changeAppBar: " )
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, configuration)
        //activityMainBinding.activityMainToolbar.visibility = GONE
    }

    fun changeAppBarTitle(title: String){
        supportActionBar?.title = title
    }
}