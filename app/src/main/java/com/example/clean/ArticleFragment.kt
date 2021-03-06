package com.example.clean

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.clean.data.Article
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_article.*
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//Fragment for the opened article
class ArticleFragment : Fragment() {
    private val args by navArgs<ArticleFragmentArgs>()
    private val db = FirebaseDatabase.getInstance()
    private lateinit var dbReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_article, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Getting the args passed from navigation
        val article = Article(
            args.ArticleData[0],
            args.ArticleData[1],
            args.ArticleData[2],
            args.ArticleData[3],
            args.ArticleData[4],
            args.ArticleData[5],
            args.ArticleData[6]
        )
        //Formatting the date/time
        val dateString = article.publishedAt
        val instant = Instant.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").withZone(ZoneId.systemDefault())

        Picasso.get().load(article.urlToImage).into(img_preview_open)
        txt_author_open.text = article.author
        txt_time_open.text = formatter.format(instant)
        txt_body_open.text = article.description
        txt_title_open.text = article.title

        //This copies the article's URL to the clipboard so the article can be shared
        img_share.setOnClickListener{
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, article.url)
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }

        //This adds the article to Firebase as the user has saved it
        img_save.setOnClickListener{
            dbReference = db.reference
            dbReference.child(article.title.replace(Regex("[.#$\\[\\]]"), "")).setValue(article)
            img_save.setColorFilter(resources.getColor(R.color.colorAccent, requireActivity().theme))

        }
    }

}