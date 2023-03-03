package com.yuvesh.newsapp

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import java.nio.file.attribute.AclEntry
import java.nio.file.attribute.AclEntry.Builder
import java.nio.file.attribute.AclEntry.newBuilder

abstract class MainActivity : AppCompatActivity(), NewsItemClicked {
    lateinit var mAdapter:NewsListAdapter
    lateinit var recyclerView:RecyclerView
    private lateinit var builder:Builder
    lateinit var customTabsIntent:CustomTabsIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)
       mAdapter= NewsListAdapter(this@MainActivity)
fetchData()
        recyclerView.adapter=mAdapter

    }


    private fun fetchData()
    {
        val url = "https://newsapi.org/v2/everything?q=tesla&from=2023-02-03&sortBy=publishedAt&apiKey=17bb18462651459caa65882cef771d26"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<news>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = news(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)


            },
            Response.ErrorListener {

            }
        )
        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }



    override fun onItemClicked(item:news) {


        customTabsIntent.launchUrl(this@MainActivity, Uri.parse(item.url))
    }
}

private fun Any.Builder(customTabsIntent: CustomTabsIntent): CustomTabsIntent {
           return newBuilder(customTabsIntent)
}

fun newBuilder(customTabsIntent: CustomTabsIntent): CustomTabsIntent {
          return customTabsIntent
}


