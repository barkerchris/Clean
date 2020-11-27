package com.example.clean.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dfl.newsapi.model.ArticleDto
import com.example.clean.MainFragmentDirections
import com.example.clean.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_list_big.view.*
import kotlinx.android.synthetic.main.article_list_small.view.*
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class RecyclerAdapter (private val context: Context, private val articleList: MutableList<ArticleDto>, private val tabTitles: Array<String>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    //viewType 0 = Big, 1 = Small
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == 0) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.article_list_big, parent, false)
            return ViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_list_small, parent, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0) {
            return 0
        }
        return 1
    }

    override fun getItemCount() = articleList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList[position]
        holder.bind(article, position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(article: ArticleDto, viewType: Int) {
            //Setting up navigation
            val arguments = arrayOf(article.urlToImage, article.author, article.publishedAt,
            article.description, article.title, article.url)
            val action: NavDirections = MainFragmentDirections.actionMainFragmentToArticleFragment(arguments)
            //Getting current tab
            val sharedPrefs: SharedPreferences = context.getSharedPreferences("currentTab", 0)
            val tabIndex = sharedPrefs.getInt("tabIndex", 0)
            val currentTab = tabTitles[tabIndex]

            if(viewType == 0) {
                //Formatting the date/time
                val dateString = article.publishedAt
                val instant = Instant.parse(dateString)
                val formatter = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault())

                Picasso.get().load(article.urlToImage).into(itemView.img_preview_big)
                itemView.txt_category_big.text = currentTab
                itemView.txt_title_big.text = article.title
                itemView.txt_source_big.text = article.source.name
                itemView.txt_time_big.text = formatter.format(instant)

                itemView.img_preview_big.setOnClickListener{
                    itemView.findNavController().navigate(action)
                }
                itemView.txt_title_big.setOnClickListener{
                    itemView.findNavController().navigate(action)
                }
            } else {
                Picasso.get().load(article.urlToImage).into(itemView.img_preview_small)
                itemView.txt_category_small.text = currentTab
                itemView.txt_title_small.text = article.title
                itemView.txt_source_small.text = article.source.name

                itemView.img_preview_small.setOnClickListener{
                    itemView.findNavController().navigate(action)
                }
                itemView.txt_title_small.setOnClickListener{
                    itemView.findNavController().navigate(action)
                }
            }
        }
    }

}