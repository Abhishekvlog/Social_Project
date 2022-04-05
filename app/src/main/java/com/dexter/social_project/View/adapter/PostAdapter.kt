package com.dexter.social_project.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dexter.social_project.Model.local_refrence.Data_Entity
import com.dexter.social_project.R
import kotlinx.android.synthetic.main.item_layout_creator.view.*
import kotlinx.android.synthetic.main.item_layout_manager.view.*

class PostAdapter(private val list: ArrayList<Data_Entity>,val type:String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        if (type=="manager"){
            val view = layoutInflater.inflate(R.layout.item_layout_manager,parent,false)
            return PostViewHolder_manager(view)
        }
        else{
            val view = layoutInflater.inflate(R.layout.item_layout_creator,parent,false)
            return PostViewHolder_creator(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (type=="manager"){
            val viewHolderOne: PostViewHolder_manager = holder as PostViewHolder_manager
            viewHolderOne.setdata2(list[position])
        }
        else{
            val viewHolderOne: PostViewHolder_creator = holder as PostViewHolder_creator
            viewHolderOne.setdata1(list[position])
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}
class PostViewHolder_creator(val item:View):RecyclerView.ViewHolder(item){
    fun setdata1(dataEntity: Data_Entity){
        item.apply {
            show_title.text = dataEntity.Title
            show_desc.text = dataEntity.Desc
        }
    }
}
class PostViewHolder_manager(val item:View):RecyclerView.ViewHolder(item){
    fun setdata2(dataEntity: Data_Entity){
        item.apply {
            show_title_manager.text = dataEntity.Title
            show_desc_manager.text = dataEntity.Desc
        }
    }
}

