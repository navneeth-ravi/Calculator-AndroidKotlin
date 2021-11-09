package com.example.nav_calculator_20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    val ac=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState ==null) {
           supportFragmentManager.beginTransaction()?.replace(R.id.container,FragmentFirst(),"fragment1a")
            ?.addToBackStack("abc")?.commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        val finishF=supportFragmentManager.findFragmentById(R.id.container)
        val ab=findViewById<FrameLayout>(R.id.container)
        val noOfFragments=supportFragmentManager.backStackEntryCount
        Log.i("#############################", "MCount : $noOfFragments ")
        if(noOfFragments>=0){
            supportFragmentManager.beginTransaction()?.replace(R.id.container,FragmentFirst(),"fragment1a")
                ?.addToBackStack("abc")?.commit()
        }
        else {
            finish()
        }
//        else{
//            finish()
//        }
    }
}