package com.example.musicapi.model

import com.example.musicapi.model.Data

data class MyData(
    val `data`: MutableList<Data>,
    val next: String,
    val total: Int
)