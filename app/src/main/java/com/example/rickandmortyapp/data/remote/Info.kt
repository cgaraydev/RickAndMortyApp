package com.example.rickandmortyapp.data.remote

data class Info(
    val count: Int,
    val next: Any,
    val pages: Int,
    val prev: String
)