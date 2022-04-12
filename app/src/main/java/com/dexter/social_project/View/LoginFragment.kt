package com.dexter.social_project.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dexter.social_project.Model.local_refrence.UserModel
import com.dexter.social_project.Model.local_refrence.Database_Ref
import com.dexter.social_project.R
import com.dexter.social_project.ViewModel.MainViewModel
import com.dexter.social_project.ViewModel.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    lateinit var numberTxt: String
    lateinit var passwordTxt: String
    lateinit var typeTxt: String
    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val refDao = Database_Ref.get_Ref_Database(requireContext()).getDao()
        mainViewModel = ViewModelProvider(
            requireActivity(),
            MainViewModelFactory(refDao)
        )[MainViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.signOut()
        txt_new_user.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.frame_layout, RegisterFragment()).addToBackStack("register").commit()
        }
        val bundle = this.arguments
        var type="Creator"
        if(bundle!=null && bundle.containsKey("type")){
            if(bundle.getInt("type",1)==2)
                type="Manager"
        }
        btn_login.setOnClickListener {
            numberTxt = et_number.text.toString()
            passwordTxt = et_password.text.toString()
            typeTxt = type
            mainViewModel.addUser(UserModel(numberTxt, passwordTxt, typeTxt))

        }
        mainViewModel.isLogin.observe(viewLifecycleOwner, Observer {
            when (it!!) {
                1 -> {
                    Toast.makeText(context, "Successfully Login", Toast.LENGTH_SHORT).show()
                    et_number.text?.clear()
                    et_password.text?.clear()
                    startActivity(Intent(requireContext(), FeedActivity::class.java))
//                    requireActivity().supportFragmentManager.beginTransaction().add(R.id.frame_layout, FeedFragment()).addToBackStack("feed").commit()
                }
                2 -> Toast.makeText(context, "Wrong type", Toast.LENGTH_SHORT).show()
                3 -> Toast.makeText(context, "Wrong password", Toast.LENGTH_SHORT).show()
                4 -> Toast.makeText(context, "Please enter valid number", Toast.LENGTH_SHORT).show()
            }
        })

    }
}