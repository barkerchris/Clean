package com.example.clean

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.core.app.NotificationCompat
import com.dfl.newsapi.enums.Category
import com.dfl.newsapi.enums.Country
import com.dfl.newsapi.model.ArticleDto
import io.reactivex.schedulers.Schedulers

//This receives the broadcast and checks for new articles
class NotificationBroadcast : BroadcastReceiver() {
    private var notificationHelper: NotificationHelper? = null
    private val newsApiRepository = ListFragment.newsApiRepository
    private var userArticleList: MutableList<ArticleDto> = ListFragment.articleList
    private var serviceArticleList: MutableList<ArticleDto> = mutableListOf()

    override fun onReceive(context: Context, intent: Intent) {
        //Get notification preferences
        val sharedPrefs: SharedPreferences = context.getSharedPreferences("notificationPreferences", 0)
        val userNotifications = sharedPrefs.getStringSet("categories", mutableSetOf())
        if(!userNotifications.isNullOrEmpty()) {
            for(cat in userNotifications) {
                serviceArticles(Category.valueOf(cat), null, 3, 1)
            }
        }
        Thread.sleep(5000)
        //Check to see if articles last pulled are the same as the ones just pulled
        if(!userArticleList.containsAll(serviceArticleList)) {
            notificationHelper = NotificationHelper(context)
            postNotification(notification_one, context.resources.getString(R.string.notification_title), context)
        }
    }

    //Pulls articles down
    @SuppressLint("CheckResult")
    fun serviceArticles(category: Category?, country: Country?, pageSize: Int, page: Int) {
        newsApiRepository.getTopHeadlines(category = category, country = country, pageSize = pageSize, page = page)
            .subscribeOn(Schedulers.io())
            .toFlowable()
            .flatMapIterable { articles -> articles.articles }
            .subscribe({ article -> serviceArticleList.add(article) },
                { t -> Log.d("$TAG error", t.message!!) })
    }

    //Post the notifications
    private fun postNotification(id: Int, title: String, context: Context) {
        var notificationBuilder: NotificationCompat.Builder? = null
        when (id) {
            notification_one -> notificationBuilder = notificationHelper!!.getNotification1(
                title,
                context.resources.getString(R.string.notification_body)
            )
        }
        if (notificationBuilder != null) {
            notificationHelper!!.notify(id, notificationBuilder)
        }
    }

    companion object {
        private const val TAG = "NotificationBroadcast"
        const val notification_one = 101
    }
}