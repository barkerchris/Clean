package com.example.clean

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_article.view.*
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class ArticleFragment : Fragment() {
    private val args by navArgs<ArticleFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_article, container, false)

        val articleArgs = args.ArticleData
        //Formatting the date/time
        val dateString = articleArgs[2]
        val instant = Instant.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").withZone(ZoneId.systemDefault())

        Picasso.get().load(articleArgs[0]).into(view.img_preview_open)
        view.txt_author_open.text = articleArgs[1]
        view.txt_time_open.text = formatter.format(instant)
        view.txt_body_open.text = articleArgs[3]
        view.txt_title_open.text = articleArgs[4]

        view.img_share.setOnClickListener{
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, view.txt_title_open.text)
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }

        return view
    }

}