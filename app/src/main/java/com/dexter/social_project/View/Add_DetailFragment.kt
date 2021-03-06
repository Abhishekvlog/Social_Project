package com.dexter.social_project.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dexter.social_project.Model.local_refrence.Data_Entity
import com.dexter.social_project.Model.local_refrence.Database_Ref
import com.dexter.social_project.Model.local_refrence.UserModel
import com.dexter.social_project.R
import com.dexter.social_project.ViewModel.MainViewModel
import com.dexter.social_project.ViewModel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.fragment_add__detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Add_DetailFragment : Fragment(R.layout.fragment_add__detail) {

    lateinit var titleTxt: String
    lateinit var descriptionTxt: String
    lateinit var numberTxt: String
    lateinit var check: UserModel
    lateinit var mainViewModel: MainViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.btn_add?.visibility=View.GONE

        val refDao = Database_Ref.get_Ref_Database(requireContext()).getDao()


        CoroutineScope(Dispatchers.IO).launch { check = refDao.getData()[0] }
        mainViewModel = ViewModelProvider(
            requireActivity(),
            MainViewModelFactory(refDao)
        )[MainViewModel::class.java]


        val bundle = this.arguments
        if (bundle != null && bundle.containsKey("data")) {
            val data = bundle.getSerializable("data") as Data_Entity
            et_title_input.setText(data.Title)
            et_desc_input.setText(data.Desc)
            create_post_txt.setText("Edit Post")
            btn_check.setOnClickListener {
                data.Title=et_title_input.text.toString()
                data.Desc=et_desc_input.text.toString()
                mainViewModel.editDataInFirebase(data)
            }

        } else {
            btn_check.setOnClickListener {
                titleTxt = et_title_input.text.toString()
                descriptionTxt = et_desc_input.text.toString()
                numberTxt = check.user_number.toString()
                create_post_txt.setText("Create Post")
                mainViewModel.addDataInFirebase(
                    Data_Entity(
                        titleTxt,
                        descriptionTxt,
                        numberTxt,
                        "Undefined",
                        ""
                    )
                )
            }

        }
        btn_clear.setOnClickListener {
            activity?.onBackPressed()
        }
        mainViewModel.isdata.observe(viewLifecycleOwner, Observer {
            when (it!!) {
                11 -> Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
                12 -> Toast.makeText(
                    context,
                    "Title length should under 3 to 30",
                    Toast.LENGTH_SHORT
                ).show()
                13 -> Toast.makeText(
                    context,
                    "Description length should under 10 to 500",
                    Toast.LENGTH_SHORT
                ).show()
                14 -> {
                    Toast.makeText(context, "Data added successfully", Toast.LENGTH_SHORT)
                        .show()
                    et_title_input.text?.clear()
                    et_desc_input.text?.clear()
                    startActivity(Intent(requireContext(), FeedActivity::class.java))

                }
            }
        })

    }
    override fun onDestroy() {
        super.onDestroy()
        activity?.btn_add?.visibility=View.VISIBLE
    }
}
