package com.utama.basoproject.network

import com.utama.basoproject.model.Menu
import retrofit2.http.GET

interface ApiService {

    @GET("get_menu.php")
    suspend fun getMenu():List<Menu>

}