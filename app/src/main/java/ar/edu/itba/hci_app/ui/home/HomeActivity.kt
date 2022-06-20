package ar.edu.itba.hci_app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ar.edu.itba.hci_app.R
import ar.edu.itba.hci_app.databinding.ActivityHomeBinding
import ar.edu.itba.hci_app.ui.AboutUsActivity
import ar.edu.itba.hci_app.ui.HelpActivity
import ar.edu.itba.hci_app.ui.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_devices,
                R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        replaceFragment(HomeFragment(), true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.appbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.item2 -> {
                val intent = Intent(this, HelpActivity::class.java)
                startActivity(intent)
            }
            R.id.item3 -> {
                val intent = Intent(this, AboutUsActivity::class.java)
                startActivity(intent)
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    fun replaceFragment(newFragment: Fragment, addToBackStack: Boolean) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction();
        if (addToBackStack)
            transaction.addToBackStack(null);
        transaction.replace(R.id.nav_host_fragment_activity_home, newFragment);
        transaction.commit();
    }

    fun popBackStack() {
        supportFragmentManager.popBackStack()
    }
}