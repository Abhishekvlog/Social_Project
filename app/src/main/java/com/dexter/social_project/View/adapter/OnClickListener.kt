package com.dexter.social_project.View.adapter

import com.dexter.social_project.Model.local_refrence.Data_Entity

interface OnClickListener {
    fun onClickedCreator(dataEntity: Data_Entity)
    fun onClickManager(dataEntity: Data_Entity)


}