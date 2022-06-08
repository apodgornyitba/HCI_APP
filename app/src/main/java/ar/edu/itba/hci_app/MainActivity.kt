package ar.edu.itba.hci_app

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import ar.edu.itba.hci_app.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button?.setOnClickListener{ startActivity() }
    }
    private fun startActivity(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}

