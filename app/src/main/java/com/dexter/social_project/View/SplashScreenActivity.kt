package com.dexter.social_project.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import com.dexter.social_project.Model.local_refrence.Database_Ref
import com.dexter.social_project.Model.local_refrence.UserModel
import com.dexter.social_project.R
import com.dexter.social_project.ViewModel.MainViewModel
import com.dexter.social_project.ViewModel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var datacheck: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val refDao = Database_Ref.get_Ref_Database(this).getDao()

        mainViewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(refDao)
            )[MainViewModel::class.java]

        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.ani)
        social_app_txt.startAnimation(slideAnimation)
        splash_app_txt.startAnimation(slideAnimation)

        CoroutineScope(Dispatchers.IO).launch {
            if (refDao.getData().isNotEmpty())
                datacheck = refDao.getData()[0]
            delay(3000)
            runOnUiThread {
                if (::datacheck.isInitialized && datacheck.user_number.isNotEmpty()) {
                    startActivity(Intent(this@SplashScreenActivity, FeedActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                }
            }

        }
    }
}