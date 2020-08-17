package fr.epita.android.final_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var data : List<Gamelist>

        var game1 : Int = 0
        var game2 : Int = 0
        var game3 : Int = 0
        var game4 : Int = 0
        val callback : Callback<List<Gamelist>> = object : Callback<List<Gamelist>>{
            override fun onFailure(call: Call<List<Gamelist>>, t: Throwable) {
                Log.d("test", "test")
            }

            override fun onResponse(
                call: Call<List<Gamelist>>,
                response: Response<List<Gamelist>>
            ) {
                if(response.code()==200){
                    data = response.body()!!
                    val randomlist = List(4) { Random.nextInt(0, data.size) }
                    var i : Int = 1
                    for (item in randomlist){
                        when (i) {
                            1 -> {
                                Glide.with(this@MainActivity).load(data[item].picture).into(view1)
                                game1 = data[item].id
                            }
                            2 -> {
                                Glide.with(this@MainActivity).load(data[item].picture).into(view2)
                                game2 = data[item].id
                            }
                            3 -> {
                                Glide.with(this@MainActivity).load(data[item].picture).into(view3)
                                game3 = data[item].id
                            }
                            4 -> {
                                Glide.with(this@MainActivity).load(data[item].picture).into(view4)
                                game4 = data[item].id
                            }
                        }
                        i = i + 1
                    }
                }
            }

        }


        val baseURL = "https://androidlessonsapi.herokuapp.com/api/game/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
        val service = retrofit.create(WSInterface::class.java)
        service.getlist().enqueue(callback)

        view1.setOnClickListener {
            val explicitIntent = Intent(this@MainActivity, GameDetails::class.java)
            explicitIntent.putExtra("MESSAGE", game1.toString())
            // Start the other activity by sending the intent
            startActivity(explicitIntent)
        }

        view2.setOnClickListener {
            val explicitIntent = Intent(this@MainActivity, GameDetails::class.java)
            explicitIntent.putExtra("MESSAGE", game2.toString())
            // Start the other activity by sending the intent
            startActivity(explicitIntent)
        }

        view3.setOnClickListener {
            val explicitIntent = Intent(this@MainActivity, GameDetails::class.java)
            explicitIntent.putExtra("MESSAGE", game3.toString())
            // Start the other activity by sending the intent
            startActivity(explicitIntent)
        }

        view4.setOnClickListener {
            val explicitIntent = Intent(this@MainActivity, GameDetails::class.java)
            explicitIntent.putExtra("MESSAGE", game4.toString())
            // Start the other activity by sending the intent
            startActivity(explicitIntent)
        }

    }
}