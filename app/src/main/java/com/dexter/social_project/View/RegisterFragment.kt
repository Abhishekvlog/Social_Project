package com.dexter.social_project.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dexter.social_project.Model.local_refrence.Database_Ref
import com.dexter.social_project.Model.local_refrence.UserModel1
import com.dexter.social_project.R
import com.dexter.social_project.ViewModel.MainViewModel
import com.dexter.social_project.ViewModel.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment(R.layout.fragment_register) {

    lateinit var emailTxt : String
    lateinit var numberTxt : String
    lateinit var passwordTxt : String
    lateinit var typeTxt : String
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val refDao = Database_Ref.get_Ref_Database(requireContext()).getDao()

        mainViewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(refDao))[MainViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btn_register.setOnClickListener {
            emailTxt = et_email_signup.text.toString()
            numberTxt = et_number_signup.text.toString()
            passwordTxt = et_password_signup.text.toString()
            typeTxt = et_type_signup.text.toString()
            mainViewModel.getSignIn(UserModel1(numberTxt,emailTxt,passwordTxt,typeTxt))

        }
        mainViewModel.isSignin.observe(viewLifecycleOwner, Observer {
            when(it!!) {
                5 -> {
                    Toast.makeText(context, "Login Successfully \n Welcome", Toast.LENGTH_SHORT)
                        .show()

                }
                6 -> {
                    Toast.makeText(context, "User Registered Successfully", Toast.LENGTH_SHORT)
                        .show()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.frame_layout, LoginFragment()).addToBackStack("login").commit()
                }
            }
        })
    }
}


//
//if (numberTxt.isEmpty() ||emailTxt.isEmpty() || passwordTxt.isEmpty() || typeTxt.isEmpty()){
//    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
//        .show()
//}
//else{
//    databaseReference.child("users").child(numberTxt).child("user_type").setValue(typeTxt)
//    databaseReference.child("users").child(numberTxt).child("user_email").setValue(emailTxt)
//    databaseReference.child("users").child(numberTxt).child("user_password").setValue(passwordTxt)
//
//    requireActivity().supportFragmentManager.beginTransaction()
//        .add(R.id.frame_layout, LoginFragment()).addToBackStack("Login").commit()
//
//    Toast.makeText(requireContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show()
//}