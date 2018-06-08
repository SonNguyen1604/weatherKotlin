package com.example.nguyenngocsonc.weatherkotlin.models.images

data class Photos(var page: Int,
                  var pages: Int,
                  var perpage: Int,
                  var total: Int,
                  var photo: ArrayList<Photo>)