package com.dexter.social_project.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dexter.social_project.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        creator_card.setOnClickListener {
            supportFragmentManager.beginTransaction().add(R.id.frame_layout,LoginFragment())
                .addToBackStack("creator_fragment").commit()
        }
        manager_card.setOnClickListener {
            supportFragmentManager.beginTransaction().add(R.id.frame_layout,LoginFragment())
                .addToBackStack("manager_fragment").commit()
        }

    }
}