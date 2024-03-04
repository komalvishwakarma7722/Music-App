package com.example.musicapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicapi.databinding.ActivitySearchArtistBinding

class SearchArtist : AppCompatActivity() {
    private lateinit var binding:ActivitySearchArtistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchArtistBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}