package com.dexter.social_project.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import com.dexter.social_project.Model.local_refrence.Data_Entity
import com.dexter.social_project.Model.local_refrence.Database_Ref
import com.dexter.social_project.R
import com.dexter.social_project.ViewModel.MainViewModel
import com.dexter.social_project.ViewModel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.fragment_manage_tag_.*


class Manage_tag_Fragment : Fragment(R.layout.fragment_manage_tag_){
    lateinit var mainViewModel: MainViewModel
    lateinit var data:Data_Entity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.btn_add?.visibility=View.GONE
        val refDao = Database_Ref.get_Ref_Database(requireContext()).getDao()
        mainViewModel = ViewModelProvider(
            requireActivity(),
            MainViewModelFactory(refDao)
        )[MainViewModel::class.java]

        val bundle = this.arguments
        if (bundle != null && bundle.containsKey("manager_data")) {
                data = bundle.getSerializable("manager_data") as Data_Entity
            show_title_manager_txt.setText(data.Title)
            show_desc_manager.setText(data.Desc)

        }

        btn_clear_manage.setOnClickListener {
            activity?.onBackPressed()
        }
        btn_check_manger_tag.setOnClickListener {
            activity?.onBackPressed()
        }
    radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
        override fun onCheckedChanged(p0: RadioGroup?, checkedId: Int) {
            when(checkedId){
                R.id.health-> mainViewModel.addTag("HEALTH",data.id_data)
                R.id.lifestyle-> mainViewModel.addTag("LIFESTYLE", data.id_data)
                R.id.hobby-> mainViewModel.addTag("HOBBY", data.id_data)
                R.id.work-> mainViewModel.addTag("WORK", data.id_data)
                R.id.vacation-> mainViewModel.addTag("VACATION", data.id_data)
            }
        }

    })
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.btn_add?.visibility=View.VISIBLE
    }
}