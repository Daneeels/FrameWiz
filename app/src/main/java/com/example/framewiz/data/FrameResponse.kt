package com.example.framewiz.data

data class FrameResponse (
    val shape : String,
    val list: List<FrameData>
)

data class FrameData (
    val name : String,
    val imageUrl : String
)