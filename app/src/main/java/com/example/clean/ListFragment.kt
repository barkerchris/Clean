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
import com.example.clean.adapters.RecyclerAdapter
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {
    private val newsApiRepository = NewsApiRepository("5d5a36ccc90f4f7baaa94cdca8da3abc")
    private var articleList: MutableList<ArticleDto> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = recycler_view

//        getArticles()
//        Thread.sleep(2000)

        recycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = RecyclerAdapter(context, articleList, resources.getStringArray(R.array.tab_titles))
        }

        swipe_refresh.setOnRefreshListener {
            getArticles()
            Thread.sleep(2000)
            recycler.adapter!!.notifyDataSetChanged()
            swipe_refresh.isRefreshing = false
        }
    }

    @SuppressLint("CheckResult")
    fun getArticles() {
        articleList.clear()
        //Get current tab
        val tabSharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("currentTab", 0)
        val tabIndex = tabSharedPreferences.getInt("tabIndex", 0)
        //Setting article criteria
        var category: Category? = null
        var country: Country? = null
        val pageSize = 4
        val page = 1
        //My news
        if(tabIndex == 0) {
            //Get user preferences
            val sharedPrefs: SharedPreferences = requireActivity().getSharedPreferences("articlePreferences", 0)
            var userCountry = sharedPrefs.getString("country", "")
            var userCategories = sharedPrefs.getStringSet("categories", mutableSetOf())
            //If the user has no set preferences then set default values
            if(userCountry.isNullOrEmpty()) {
                userCountry = resources.getStringArray(R.array.countries)[50]
            }
            if(userCategories.isNullOrEmpty()) {
                userCategories = mutableSetOf(resources.getString(R.string.general))
            }
            for(cat in userCategories) {
                    newsApiRepository.getTopHeadlines(category = Category.valueOf(cat.toString()),
                        country = Country.valueOf(userCountry.toString()), pageSize = page, page = page)
                        .subscribeOn(Schedulers.io())
                        .toFlowable()
                        .flatMapIterable { articles -> articles.articles }
                        .subscribe({ article -> articleList.add(article) },
                            { t -> Log.d("getTopHeadlines error", t.message!!) })
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

        newsApiRepository.getTopHeadlines(category = category, country = country, pageSize = pageSize, page = page)
            .subscribeOn(Schedulers.io())
            .toFlowable()
            .flatMapIterable { articles -> articles.articles }
            .subscribe({ article -> articleList.add(article) },
                { t -> Log.d("getTopHeadlines error", t.message!!) })
    }

}