package com.noahseidman.comcast.models

data class Stat (val base_stat : Int, val effort : Int, val stat : StatDetails)

data class StatDetails (val name : String, val url : String)