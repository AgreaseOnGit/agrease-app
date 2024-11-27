package com.bangkit.capstone.agreaseapp.data.remote.retrofit

import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.data.remote.response.RegisterResponse
import com.bangkit.capstone.agreaseapp.data.remote.response.TemplateResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    @Headers("Accept: application/json")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("displayName") displayName: String,
        @Field("phone") phone: String,
        @Field("address") address: String,
        @Field("role") role: String,
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("verify/{userid}")
//    @Headers("Accept: application/json")
    @Headers("Content-Type: application/json")
    suspend fun verify(
        @Path("userid") userid: String,
        @Field("codeOTP") codeOTP: Int
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    @Headers("Accept: application/json")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<TemplateResponse<UserModel>>

    @GET("user")
    @Headers("Accept: application/json")
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): Response<TemplateResponse<UserModel>>

//    @GET("articles")
//    @Headers("Accept: application/json")
//    suspend fun getArticles(
//        @Query("page") page: Int,
//    ): Response<TemplateResponse<List<ArticleModel>>>
//
//    @GET("articles/{id}")
//    @Headers("Accept: application/json")
//    suspend fun getArticleById(
//        @Path("id") id: Int
//    ): Response<TemplateResponse<ArticleModel>>
//
//    @Multipart
//    @POST("predict")
//    @Headers("Accept: application/json")
//    suspend fun doAnalysis(
//        @Header("Authorization") token: String,
//        @Part image: MultipartBody.Part?,
//        @Part("user_id") user_id: Int,
//    ): Response<Any>
//
//    @GET("reports")
//    @Headers("Accept: application/json")
//    suspend fun getReports(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int,
//    ): Response<TemplateResponse<List<ReportModel>>>
//
//    @GET("reports/detail/{id}")
//    @Headers("Accept: application/json")
//    suspend fun getReportById(
//        @Header("Authorization") token: String,
//        @Path("id") id: Int
//    ): Response<TemplateResponse<ReportModel>>
//
//    @GET("summary")
//    @Headers("Accept: application/json")
//    suspend fun getSummary(
//        @Header("Authorization") token: String,
//    ): Response<TemplateResponse<SummaryModel>>
//
//    @Multipart
//    @POST("update/user")
//    @Headers("Accept: application/json")
//    suspend fun updateUser(
//        @Header("Authorization") token: String,
//        @Part("name") name: String,
//        @Part profile: MultipartBody.Part?,
//        @Part("email") email: String,
//    ): Response<TemplateResponse<UserModel>>
//
//    @FormUrlEncoded
//    @POST("change/password")
//    @Headers("Accept: application/json")
//    suspend fun changePassword(
//        @Header("Authorization") token: String,
//        @Field("password") password: String,
//        @Field("confirm_password") confirm_password: String,
//    ): Response<TemplateResponse<Boolean>>
//
}