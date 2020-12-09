package com.example.clean

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clean.adapters.SavedRecyclerAdapter
import com.example.clean.data.Article
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_saved_list.*

//Displays all the saved articles in a recycler view
class SavedListFragment : Fragment() {
    val savedArticleList: MutableList<Article> = mutableListOf()
    private val db = FirebaseDatabase.getInstance()
    private lateinit var dbReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedArticleList.clear()
        //Gets the data from the Realtime Firebase database
        dbReference = db.reference
        val getData = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                for(article in snapshot.children) {
                    val art = Article(
                        article.child("urlToImage").value as String?,
                        article.child("author").value as String?,
                        article.child("source").value as String,
                        article.child("publishedAt").value as String?,
                        article.child("description").value as String,
                        article.child("title").value as String,
                        article.child("url").value as String
                    )
                    if(!savedArticleList.contains(art)) {
                        savedArticleList.add(art)
                        recycler_view_saved?.adapter = SavedRecyclerAdapter(requireContext(), savedArticleList)
                    }
                }
            }
        }
        dbReference.addValueEventListener(getData)
        dbReference.addListenerForSingleValueEvent(getData)

        recycler_view_saved.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter
        }
    }

}