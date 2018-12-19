package com.lab.greenpremium.data.network

import com.lab.greenpremium.data.entity.*
import io.reactivex.Single
import retrofit2.http.*
import javax.inject.Inject


class ApiMethods @Inject constructor(private val api: GpApi) {

    fun auth(request: AuthRequest): Single<BaseResponse<AuthData>> {
        return api.auth(request.login, request.password)
    }

    fun getObjectInfo(token: String): Single<BaseResponse<ObjectInfo>> {
        return api.getObjectInfo(token)
    }

    fun getEvents(token: String): Single<BaseResponse<List<Event>>> {
        return api.getEvents(token)
    }

    fun getContacts(): Single<BaseResponse<ContactsData>> {
        return api.getContacts()
    }

    fun getMeetingsList(token: String): Single<BaseResponse<List<Meeting>>> {
        return api.getMeetingsList(token)
    }

    fun addMeeting(token: String, manager_id: String, date: String): Single<BaseResponse<MeetingsAddResponse>> {
        return api.addMeeting(token, manager_id, date)
    }

    fun getCatalogSections(): Single<BaseResponse<List<Section>>> {
        return api.getCatalogSections()
    }

    fun getSectionProductsList(token: String, section_id: Int): Single<BaseResponse<List<Product>>> {
        return api.getSectionProductsList(token, section_id)
    }

    fun getProductDetail(token: String, product_id: Int): Single<BaseResponse<Product>> {
        return api.getProductDetail(token, product_id)
    }

    fun getMapObjects(): Single<BaseResponse<MapObjectsData>> {
        return api.getMapObjects()
    }

    fun getPortfolio(): Single<BaseResponse<Portfolio>> {
        return api.getPortfolio()
    }

}

interface GpApi {

    @FormUrlEncoded
    @POST("auth")
    fun auth(@Field("login") login: String,
             @Field("password") password: String): Single<BaseResponse<AuthData>>

    @GET("objects/info")
    fun getObjectInfo(@Header("X-Auth-Token") token: String): Single<BaseResponse<ObjectInfo>>

    @GET("events")
    fun getEvents(@Header("X-Auth-Token") token: String): Single<BaseResponse<List<Event>>>

    @GET("contacts")
    fun getContacts(): Single<BaseResponse<ContactsData>>

    @GET("meetings")
    fun getMeetingsList(@Header("X-Auth-Token") token: String): Single<BaseResponse<List<Meeting>>>

    @FormUrlEncoded
    @POST("meetings/add")
    fun addMeeting(@Header("X-Auth-Token") token: String,
                   @Field("manager_id") manager_id: String,
                   @Field("date") date: String): Single<BaseResponse<MeetingsAddResponse>>

    @GET("catalog/sections")
    fun getCatalogSections(): Single<BaseResponse<List<Section>>>

    @GET("catalog/sections/{section_id}")
    fun getSectionProductsList(@Header("X-Auth-Token") token: String,
                               @Path("section_id") section_id: Int): Single<BaseResponse<List<Product>>>

    @GET("catalog/products/{product_id}")
    fun getProductDetail(@Header("X-Auth-Token") token: String,
                         @Path("product_id") product_id: Int): Single<BaseResponse<Product>>

    @GET("portfolio")
    fun getPortfolio(): Single<BaseResponse<Portfolio>>

    @GET("objects/map")
    fun getMapObjects(): Single<BaseResponse<MapObjectsData>>

}