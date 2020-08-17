package fr.epita.android.final_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_game_details.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class GameDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        val originIntent = intent
        val message = originIntent.getStringExtra("MESSAGE")
        var data : GameInfo

        val callback : Callback<GameInfo> = object : Callback<GameInfo> {
            override fun onFailure(call: Call<GameInfo>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<GameInfo>,
                response: Response<GameInfo>
            ) {

                if(response.code()==200){
                    data = response.body()!!

                    namelabel.text = data.name
                    typelabel.text = data.type
                    playerlabel.text = data.players.toString()
                    yearlabel.text = data.year.toString()
                    descriptionlabel.text = data.description_en
                    Glide.with(this@GameDetails).load(data.picture).into(imageView)
                }
            }

        }


        val baseURL = "https://androidlessonsapi.herokuapp.com/api/game/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
        val service = retrofit.create(WSInterface::class.java)
        service.getdetails(message!!.toInt()).enqueue(callback)
    }
}