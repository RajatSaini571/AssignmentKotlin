package com.test.assigntest

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val modelList = ArrayList<DataModel>()
    private lateinit var newmodelList: List<DataModel>
    private lateinit var rv_title: RecyclerView
    private lateinit var rv_progress: ProgressBar
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_title = findViewById(R.id.rv_title)
        fab = findViewById(R.id.fab)
        rv_progress = findViewById(R.id.rv_progress)

        fab.setOnClickListener {
            if (!checkAccessibilityPermission()) {
                Toast.makeText(this@MainActivity, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        saveData()
        loadRoomData()
    }

    private fun loadRoomData() {
        val myDatabase = Room.databaseBuilder(
            applicationContext,
            MyDatabase::class.java, "recyclerview-database").allowMainThreadQueries().build()

        val entities = myDatabase.myDao()?.getAllEntities()
        var myList: ArrayList<DataModel> = ArrayList()
        if (entities != null) {
            for (entity in entities) {
                myList = entity.myList as ArrayList<DataModel>
                Log.d("room list====", "$myList")
            }
        }

        rv_title.layoutManager = LinearLayoutManager(applicationContext)
        rv_title.adapter = RecyclerAdapter(applicationContext, myList, this@MainActivity)
        rv_progress.visibility = View.GONE
        rv_title.visibility = View.VISIBLE
    }

    private fun saveData() {
        val call: Call<List<DataModel>>? = RetrofitClient.instance?.myApi?.getdata()
        call?.enqueue(object : Callback<List<DataModel>> {
            override fun onResponse(
                call: Call<List<DataModel>>,
                response: Response<List<DataModel>>
            ) {
                val myheroList = response.body() ?: throw AssertionError()
                for (i in myheroList.indices) {
                    modelList.add(
                        DataModel(
                            myheroList[i].userId,
                            myheroList[i].id,
                            myheroList[i].title,
                            myheroList[i].body
                        )
                    )
                }

                val myEntity = MyEntity(modelList)
                val myDatabase = Room.databaseBuilder(
                    applicationContext,
                    MyDatabase::class.java, "recyclerview-database"
                ).allowMainThreadQueries().build()

                myDatabase.myDao()?.insert(myEntity)
                Toast.makeText(applicationContext, "Saved to Room Database.", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "An error has occurred: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun checkAccessibilityPermission(): Boolean {
        var accessEnabled = 0
        try {
            accessEnabled =
                Settings.Secure.getInt(contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
        }

        return if (accessEnabled == 0) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            false
        } else {
            true
        }
    }
}
