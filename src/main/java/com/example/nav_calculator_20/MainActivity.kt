package com.example.nav_calculator_20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState ==null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, FragmentFirst(),"fragment1").commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val finishF=supportFragmentManager.findFragmentById(R.id.container)
        val f2=supportFragmentManager.findFragmentByTag("fragment2a")
        Log.i("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$", "onBackPressed: ")
        if(finishF is FragmentFirst)
            finish()
    }
}