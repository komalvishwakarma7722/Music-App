package com.example.musicapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapi.adapter.MusicAdapter
import com.example.musicapi.databinding.ActivityMainBinding
import com.example.musicapi.model.Data
import com.example.musicapi.model.MyData
import com.example.musicapi.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var artistList = mutableListOf<Data>()
    private lateinit var musicAdapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        musicAdapter= MusicAdapter(this,artistList)
        binding.rv.layoutManager=LinearLayoutManager(this)
        binding.rv.adapter=musicAdapter

        getdata()

    }


   private fun getdata() {
       ApiClient.init().getUserlist("Arijit").enqueue(object : Callback<MyData> {
           override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
               if (response.isSuccessful){
                       val userResponse = response.body()?.data
                       userResponse?.let {
                           musicAdapter.artistList= it
                           musicAdapter.notifyDataSetChanged()
                       }

               }
           }

           override fun onFailure(call: Call<MyData>, t: Throwable) {
               Log.d("MainActivity", "Error fetching data", t)
           }


       })

   }
}

