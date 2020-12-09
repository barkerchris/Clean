package com.example.clean

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dfl.newsapi.NewsApiRepository
import com.dfl.newsapi.enums.Category
import com.dfl.newsapi.enums.Country
import com.dfl.newsapi.model.ArticleDto
import com.example.clean.adapters.ListRecyclerAdapter
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*

//Fragment holding the recycler view for viewing articles
class ListFragment : Fragment() {

    //Can use this in other classes
    companion object {
        val newsApiRepository = NewsApiRepository("736bca77f5a64911805f4c07e052e98a")
        var articleList: MutableList<ArticleDto> = mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArticles()

        Thread.sleep(1000)

        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter
        }

        swipe_refresh.setOnRefreshListener {
            getArticles()
            swipe_refresh.isRefreshing = false
            Thread.sleep(1000)
            recycler_view.adapter!!.notifyDataSetChanged()
        }
    }

    //Get all relevant info ready to actually pull the articles
    private fun getArticles() {
        articleList.clear()
        //Get current tab
        val tabSharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("currentTab", 0)
        val tabIndex = tabSharedPreferences.getInt("tabIndex", 0)
        //Setting article criteria
        var category: Category? = null
        var country: Country? = null
        var pageSize = 10
        val page = 1
        //My news
        if(tabIndex == 0) {
            //Get user preferences
            val sharedPrefs: SharedPreferences = requireActivity().getSharedPreferences("articlePreferences", 0)
            val userCountry = sharedPrefs.getString("country", "")
            var userCategories = sharedPrefs.getStringSet("categories", mutableSetOf())
            lateinit var uCountry: Country
            //If the user has no set preferences then set default values
            if(userCountry.isNullOrEmpty()) {
                uCountry = Country.valueOf(resources.getStringArray(R.array.countries)[50])
            } else {
                uCountry = Country.valueOf(userCountry)
            }
            if(userCategories.isNullOrEmpty()) {
                userCategories = mutableSetOf(resources.getString(R.string.general))
            }
            pageSize = 3
            for(cat in userCategories) {
                pullArticles(Category.valueOf(cat), uCountry, pageSize, page)
            }
            return
        } else if(tabIndex == 1) {
            category = Category.valueOf(resources.getString(R.string.general))
            country = null
        } else if(tabIndex == 2) {
            category = null
            country = Country.valueOf(resources.getStringArray(R.array.countries)[50])
        } else if(tabIndex == 3) {
            category = Category.valueOf(resources.getString(R.string.technology))
            country = null
        } else if(tabIndex == 4) {
            category = Category.valueOf(resources.getString(R.string.science))
            country = null
        } else if(tabIndex == 5) {
            category = Category.valueOf(resources.getString(R.string.health))
            country = null
        } else if(tabIndex == 6) {
            category = Category.valueOf(resources.getString(R.string.sports))
            country = null
        } else if(tabIndex == 7) {
            category = Category.valueOf(resources.getString(R.string.entertainment))
            country = null
        } else if(tabIndex == 8) {
            category = Category.valueOf(resources.getString(R.string.business))
            country = null
        }
        pullArticles(category, country, pageSize, page)
    }

    //Pulls the articles
    @SuppressLint("CheckResult")
    fun pullArticles(category: Category?, country: Country?, pageSize: Int, page: Int) {
        newsApiRepository.getTopHeadlines(category = category, country = country, pageSize = pageSize, page = page)
            .subscribeOn(Schedulers.io())
            .toFlowable()
            .flatMapIterable { articles -> articles.articles }
            .subscribe({ article -> filter(article) },
                { t -> Log.d("getTopHeadlines error", t.message!!) })
    }

    //Filters out articles (stops duplicates + allows bonus blacklist feature)
    private fun filter(article: ArticleDto) {
        val sharedPrefs: SharedPreferences = requireActivity().getSharedPreferences("blacklistPreferences", 0)
        val blacklist = sharedPrefs.getStringSet("words", mutableSetOf())
        var add: Boolean = true
        //Check to see if title contains blocked words
        if(!blacklist.isNullOrEmpty()) {
            for(word in blacklist) {
                if(article.title.contains(word, ignoreCase = true)) {
                    add = false
                }
            }
            //Add to list
            if(add && !articleList.contains(article)) {
                articleList.add(article)
                recycler_view.adapter = ListRecyclerAdapter(requireContext(), articleList, resources.getStringArray(R.array.tab_titles))
            }
            return
        }
        //This is here to stop duplicates in the case that the blacklist is empty
        if(!articleList.contains(article)) {
            articleList.add(article)
            recycler_view.adapter = ListRecyclerAdapter(requireContext(), articleList, resources.getStringArray(R.array.tab_titles))
        }
    }

}