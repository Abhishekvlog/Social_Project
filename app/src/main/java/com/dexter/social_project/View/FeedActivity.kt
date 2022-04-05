package com.dexter.social_project.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dexter.social_project.Model.local_refrence.Data_Entity
import com.dexter.social_project.Model.local_refrence.Database_Ref
import com.dexter.social_project.R
import com.dexter.social_project.ViewModel.MainViewModel
import com.dexter.social_project.ViewModel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_feed.*
import java.util.ArrayList

class FeedActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        val refDao = Database_Ref.get_Ref_Database(this).getDao()
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(refDao))[MainViewModel::class.java]

        btnSignOut.setOnClickListener {
            mainViewModel.signOut()
            startActivity(Intent(this,MainActivity::class.java))
        }

        btn_add.setOnClickListener {
            this.supportFragmentManager.beginTransaction().add(R.id.frame_layout2, Add_DetailFragment()).addToBackStack("Add").commit()
        }

        mainViewModel.getData().observe(this, Observer {
            val list = it as ArrayList<Data_Entity>
            setRecyclerView(list)
        })

    }

    private fun setRecyclerView(list: ArrayList<Data_Entity>) {
        val linearLayoutManager = LinearLayoutManager(this)
        postAdapter = PostAdapter(list,mainViewModel.datacheck.user_type.lowercase())
        recycler_view_allPosts.apply {
            layoutManager = linearLayoutManager
            this.adapter = postAdapter
        }
    }
}