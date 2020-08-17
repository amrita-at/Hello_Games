package fr.epita.android.final_project

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WSInterface {
    @GET(value = "list" )
    fun getlist(): Call<List<Gamelist>>

    @GET(value = "details")
    fun getdetails(@Query(value = "game_id") id: Int): Call<GameInfo>
}