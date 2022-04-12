package com.dexter.social_project.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dexter.social_project.Model.local_refrence.Data_Entity
import com.dexter.social_project.Model.local_refrence.UserModel
import com.dexter.social_project.Model.local_refrence.Dao_Ref
import com.dexter.social_project.Model.local_refrence.UserModel1
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val daoRef: Dao_Ref) : ViewModel() {

    var login = false
    var isLogin = MutableLiveData<Int>(0)
    var isSignin = MutableLiveData<Int>(0)
    var isdata = MutableLiveData<Int>(0)
    lateinit var datacheck: UserModel


    init {
        CoroutineScope(Dispatchers.IO).launch {
            if (daoRef.getData().isNotEmpty())
                datacheck = daoRef.getData()[0]
        }
    }

    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance()
        .getReferenceFromUrl("https://sociallogin-8e3be-default-rtdb.firebaseio.com/")


    fun addUser(userModel: UserModel) {
        databaseReference.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.child(userModel.user_number).exists()) {

                    val user = snapshot.child(userModel.user_number).getValue(UserModel::class.java)

                    Log.d("abhi", "${user!!.user_password}")

                    if (user!!.user_password.equals(userModel.user_password)) {

                        if (user.user_type.equals(userModel.user_type)) {
                            isLogin.postValue(1)
                            login = true
                            datacheck = userModel
                            CoroutineScope(Dispatchers.IO).launch { daoRef.addData(userModel) }

                        } else {
                            isLogin.postValue(2)
                        }
                    } else {
                        isLogin.postValue(3)
                    }
                } else {
                    isLogin.postValue(4)
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    fun getSignIn(userModel1: UserModel1) {

        if (userModel1.user_sign_number.isEmpty() || userModel1.user_sign_email.isEmpty() || userModel1.user_sign_password.isEmpty() || userModel1.user_sign_type.isEmpty()) {
            isSignin.postValue(5)
        } else {
            databaseReference.child("users").child(userModel1.user_sign_number).child("user_type")
                .setValue(userModel1.user_sign_type)
            databaseReference.child("users").child(userModel1.user_sign_number).child("user_email")
                .setValue(userModel1.user_sign_email)
            databaseReference.child("users").child(userModel1.user_sign_number)
                .child("user_password").setValue(userModel1.user_sign_password)
            CoroutineScope(Dispatchers.IO).launch { daoRef.signInData(userModel1) }
            isSignin.postValue(6)
        }
    }

    fun signOut() {
        CoroutineScope(Dispatchers.IO).launch {
            daoRef.deleteData()
        }
    }

    fun fatchData() {
        databaseReference.child("posts").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                CoroutineScope(Dispatchers.IO).launch {
                    daoRef.deleteAllPosts()
                }

                for (snapshot in snapshot.children) {
                    val data: Data_Entity? = snapshot.getValue(Data_Entity::class.java)
                    CoroutineScope(Dispatchers.IO).launch {
                        daoRef.addData_in_database(data!!)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getData(): LiveData<List<Data_Entity>> {
        return daoRef.getData_from_database()
    }


    fun addDataInFirebase(dataEntity: Data_Entity) {

        val key = databaseReference.child("posts").push().key
        dataEntity.id_data = key!!
        if (dataEntity.Title.isEmpty() || dataEntity.Desc.isEmpty()) {
            isdata.postValue(11)
            // toast -> fields are empty
        } else if (dataEntity.Title.length < 3 || dataEntity.Title.length > 30) {
            isdata.postValue(12)
            // toast -> title min length should be 3 and max is 30
        } else if (dataEntity.Desc.length < 10 || dataEntity.Desc.length > 500) {
            isdata.postValue(13)
            // toast -> title min length should be 10 and max is 500
        } else {
            databaseReference.child("posts").child(key).setValue(dataEntity)

//            CoroutineScope(Dispatchers.IO).launch { daoRef.addData_in_database(dataEntity) }
            isdata.postValue(14)
            // toast -> data add successfully

        }
    }

    fun editDataInFirebase(dataEntity: Data_Entity) {

        if (dataEntity.Title.isEmpty() || dataEntity.Desc.isEmpty()) {
            isdata.postValue(11)
            // toast -> fields are empty
        } else if (dataEntity.Title.length < 3 || dataEntity.Title.length > 30) {
            isdata.postValue(12)
            // toast -> title min length should be 3 and max is 30
        } else if (dataEntity.Desc.length < 10 || dataEntity.Desc.length > 500) {
            isdata.postValue(13)
            // toast -> title min length should be 10 and max is 500
        } else {
            databaseReference.child("posts").child(dataEntity.id_data).setValue(dataEntity)

//            CoroutineScope(Dispatchers.IO).launch { daoRef.addData_in_database(dataEntity) }
            isdata.postValue(14)
            // toast -> data add successfully

        }
    }

    fun addTag(tag: String, dataId: String) {
        databaseReference.child("posts").child(dataId).child("tag").setValue(tag)
    }

}

class MainViewModelFactory(val daoRef: Dao_Ref) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(daoRef) as T
    }

}