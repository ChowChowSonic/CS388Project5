package com.codepath.articlesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val articles = mutableListOf<Day>()
    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        articlesRecyclerView = findViewById<RecyclerView>(R.id.articles)
        val articleAdapter = DayAdapter(this, articles)
        articlesRecyclerView.adapter = articleAdapter
        articlesRecyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            (application as ArticleApplication).db.articleDao().getAll().collect { databaseList ->
                println(databaseList.size)
                if(databaseList.isNotEmpty()) {
                    databaseList.map { entity ->
                        Day(
                            entity.id,
                            entity.steps,
                            entity.caloriesEaten,
                            entity.sleepHrs,
                            entity.waterGlasses
                        )
                    }.also { mappedList ->
                        articles.clear()
                        articles.addAll(mappedList)

                    }
                }
            }
        }
        findViewById<Button>(R.id.submit).setOnClickListener{
            lifecycleScope.launch(IO) {
//                (application as ArticleApplication).db.articleDao().deleteAll()

                val dayno = articles.size.toLong()
                println(dayno)
                val cals = findViewById<TextView>(R.id.cals).text.toString()
                val steps = findViewById<TextView>(R.id.steps).text.toString()
                val sleep = findViewById<TextView>(R.id.sleep).text.toString()
                (application as ArticleApplication).db.articleDao().insertOne(
                    Day(
                        dayno,
                        steps.toInt(),
                        cals.toInt(),
                        sleep.toInt(),
                        sleep.toInt()
                    )
                )
                (application as ArticleApplication).db.articleDao().getAll().collect { databaseList ->
                    println(databaseList.size)
                    databaseList.map { entity ->
                        Day(
                            entity.id,
                            entity.steps,
                            entity.caloriesEaten,
                            entity.sleepHrs,
                            entity.waterGlasses
                        )
                    }.also { mappedList ->
                        articles.clear()
                        articles.addAll(mappedList)

                    }
                }//*/
            }
            articleAdapter.notifyDataSetChanged()
        }
    }//ends OnCreate

}