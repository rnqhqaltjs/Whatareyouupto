package com.example.whatareyouupto.sqlite

data class Memo(
    var id : Long?,
    var checkbox : Boolean,
    var title: String,
    var content: String?,
    var image : Int,
    var mintime : String,
    var maxtime : String,
    var year : Int,
    var month : Int,
    var day : Int
)
