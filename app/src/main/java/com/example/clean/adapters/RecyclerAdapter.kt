package com.example.clean.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clean.R
import com.example.clean.data.Article
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.article_list_small.view.*

class RecyclerAdapter (private val articles: List<Article>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_list_small, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount() = articles.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(article: Article) {
            itemView.txt_category_small.text = article.author
            itemView.txt_title_small.text = article.title
            itemView.txt_source_small.text = article.source
            itemView.img_preview_small.setImageResource(R.drawable.ic_action_profile)

            itemView.setOnClickListener {
                val msg = article.title
                val snackbar = Snackbar.make(itemView, msg, Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
    }
}