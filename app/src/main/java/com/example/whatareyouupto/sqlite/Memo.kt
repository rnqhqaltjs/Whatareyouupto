package com.example.whatareyouupto.sqlite

data class Memo(
    var id : Long?,
    var title: String,
    var mintime : String,
    var maxtime : String,
    var year : Int,
    var month : Int,
    var day : Int
)
