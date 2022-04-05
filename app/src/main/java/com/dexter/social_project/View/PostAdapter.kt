package com.dexter.social_project.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dexter.social_project.Model.local_refrence.Data_Entity
import com.dexter.social_project.R
import kotlinx.android.synthetic.main.item_layout_creator.view.*

class PostAdapter(
    private val list: ArrayList<Data_Entity>
) : RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_creator,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.setdata(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
class PostViewHolder(val item:View):RecyclerView.ViewHolder(item){
    fun setdata(dataEntity: Data_Entity){
        item.apply {
            show_title.text = dataEntity.Title
            show_desc.text = dataEntity.Desc
        }
    }
}