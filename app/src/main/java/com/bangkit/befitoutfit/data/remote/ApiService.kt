package com.bangkit.befitoutfit.data.remote

import com.bangkit.befitoutfit.data.model.Info
import com.bangkit.befitoutfit.data.model.Login
import com.bangkit.befitoutfit.data.model.Outfits
import com.bangkit.befitoutfit.data.model.Recommend
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Login

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Info

    @FormUrlEncoded
    @PUT("user/{id}")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("email") email: String,
    )

    @GET("outfit")
    suspend fun getOutfit(): Outfits

    @Multipart
    @POST("outfit/add")
    suspend fun addOutfit(
        @Part("name") name: RequestBody,
        @Part("type") type: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("include") include: RequestBody,
    ): Info

    @Multipart
    @PUT("outfit/{id}")
    suspend fun updateOutfit(
        @Path("id") id: Int,
        @Part("name") name: RequestBody,
        @Part("type") type: RequestBody,
        @Part("include") include: RequestBody,
    )

    @GET("recommend")
    suspend fun getRecommend(
        @Query("email") email: String,
        @Query("event") event: String,
    ): Recommend
}
