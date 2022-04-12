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
            val bundle=Bundle()
            bundle.putInt("type",1)
            supportFragmentManager.beginTransaction().add(R.id.frame_layout,LoginFragment::class.java,bundle)
                .addToBackStack("creator_fragment").commit()
        }
        manager_card.setOnClickListener {
            val bundle=Bundle()
            bundle.putInt("type",2)
            supportFragmentManager.beginTransaction().add(R.id.frame_layout,LoginFragment::class.java,bundle)
                .addToBackStack("manager_fragment").commit()
        }

    }
}