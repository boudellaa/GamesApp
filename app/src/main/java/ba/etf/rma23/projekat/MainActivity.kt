package ba.etf.rma23.projekat

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.setHash
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val config : Configuration = baseContext.resources.configuration
        if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){
            supportFragmentManager.beginTransaction().replace(R.id.game_details_fragment_container, GameDetailsFragment()).commit()
            supportFragmentManager.beginTransaction().replace(R.id.home_fragment_container, HomeFragment()).commit()
        }else {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
            navView.setupWithNavController(navController)

            val bottom = findViewById<BottomNavigationView>(R.id.bottom_nav)
            bottom?.menu?.findItem(R.id.gameDetailsItem)?.isEnabled = false
            bottom?.menu?.findItem(R.id.homeItem)?.isChecked = true
        }

        setHash("6f842447-0425-4921-872c-71d555b561a2")

    }

}
