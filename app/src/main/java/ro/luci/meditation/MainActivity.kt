package ro.luci.meditation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment


class MainActivity: AppCompatActivity() {

    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navController) as NavHostFragment
        navController = navHostFragment.navController
    }

    fun navigateFromHomeToTimer() {
        navController!!.navigate(R.id.action_mainHome_to_mainTimer)
    }

    fun navigateFromTimerToHome() {
        navController!!.navigate(R.id.action_mainTimer_to_mainHome)
    }

    fun navigateFromHomeToProgress() {
        navController!!.navigate(R.id.action_mainHome_to_mainProgress)
    }

}
