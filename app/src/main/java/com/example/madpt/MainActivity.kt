package com.example.madpt

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import com.example.madpt.databinding.ActivityMainBinding
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.madpt.main.GoalSetPageFragment
import com.example.madpt.main.MainPageFragment
import com.example.madpt.main.PutWeightDialog
import com.example.madpt.more.MoreFragment
import com.example.madpt.social.SocialFragment
import com.example.madpt.statistics.StatisticsFragment
import com.example.madpt.training.TrainingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.fl_container, MainPageFragment(), "main").commit()

        binding.bnvMain.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.main -> {
                        changeFragment(MainPageFragment(),"main")
                    }
                    R.id.statistics -> {
                        changeFragment(StatisticsFragment(),"statistics")
                    }
                    R.id.training -> {
                        changeFragment(TrainingFragment(),"training")
                    }
                    R.id.social -> {
                        changeFragment(SocialFragment(),"social")
                    }
                    else -> {
                        changeFragment(MoreFragment(),"more")
                    }
                }
            true
        }
    }

    private fun changeFragment(fragment: Fragment, name : String){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, fragment, name)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    fun updateBottomMenu(navigation: BottomNavigationView) {
        val tag1: Fragment? = supportFragmentManager.findFragmentByTag("main")
        val tag2: Fragment? = supportFragmentManager.findFragmentByTag("statistics")
        val tag3: Fragment? = supportFragmentManager.findFragmentByTag("training")
        val tag4: Fragment? = supportFragmentManager.findFragmentByTag("social")
        val tag5: Fragment? = supportFragmentManager.findFragmentByTag("more")

        if(tag1 != null && tag1.isVisible) navigation.menu.findItem(R.id.main).isChecked = true
        else if(tag2 != null && tag2.isVisible) navigation.menu.findItem(R.id.statistics).isChecked = true
        else if(tag3 != null && tag3.isVisible) navigation.menu.findItem(R.id.training).isChecked = true
        else if(tag4 != null && tag4.isVisible) navigation.menu.findItem(R.id.social).isChecked = true
        else if(tag5 != null && tag5.isVisible) navigation.menu.findItem(R.id.more).isChecked = true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val bnv = findViewById<View>(R.id.bnv_main) as BottomNavigationView
        updateBottomMenu(bnv)
    }
    fun setFragment() {
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, MainPageFragment())
        transaction.commit()
    }

    fun changeGoalSettingFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_container, GoalSetPageFragment())
        transaction.commit()
    }

    fun showMessageDialog(){
        val customDialog = PutWeightDialog(finishApp = {finish()})
        customDialog.show(supportFragmentManager, "PutWeightDialog")
    }


}