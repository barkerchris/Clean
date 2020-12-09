package com.example.clean.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.clean.R
import com.example.clean.SavedListFragmentDirections
import com.example.clean.data.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_list_small.view.*

//Adapter for the recycler view holding all saved articles
class SavedRecyclerAdapter (private val context: Context, private val savedArticleList: MutableList<Article>): RecyclerView.Adapter<SavedRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_list_small, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = savedArticleList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = savedArticleList[position]
        holder.bind(article)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(article: Article) {
            //Setting up navigation
            val arguments = arrayOf(
                article.urlToImage as String, article.author as String, article.source, article.publishedAt as String,
                article.description, article.title, article.url
            )
            val action: NavDirections =
                SavedListFragmentDirections.actionSavedListFragmentToSavedArticleFragment(arguments)

            Picasso.get().load(article.urlToImage).into(itemView.img_preview_small)
            itemView.txt_category_small.text =
                context.resources.getString(R.string.saved_articles)
            itemView.txt_title_small.text = article.title
            itemView.txt_source_small.text = article.source

            itemView.img_preview_small.setOnClickListener {
                itemView.findNavController().navigate(action)
            }
            itemView.txt_title_small.setOnClickListener {
                itemView.findNavController().navigate(action)
            }
        }
    }

}