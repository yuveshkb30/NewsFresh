package com.yuvesh.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener:NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {
    private lateinit var items:ArrayList<news>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
                    val view=LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        val viewHolder=NewsViewHolder(view)
        view.setOnClickListener{
listener.onItemClicked(items[viewHolder.adapterPosition])
        }
                     return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
               val currentItem=items[position]
               holder.titleView.text=currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updateNews:ArrayList<news>)
    {
        items.clear()
        items.addAll(updateNews)

        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    val titleView:TextView=itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)
}

interface NewsItemClicked{
    abstract val CustomTabsIntent: Any

    fun onItemClicked(item:news)
}