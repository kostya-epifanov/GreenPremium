package com.lab.greenpremium.data.network

import com.lab.greenpremium.data.entity.*
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*
import javax.inject.Inject


class ApiMethods @Inject constructor(private val api: GpApi) {

    //region AUTH
    fun auth(request: AuthRequest): Single<BaseResponse<AuthResponse>> {
        return api.auth(request.login, request.password)
    }

    fun passwordRecovery(email: String): Single<BaseResponse<Any>> {
        return api.passwordRecovery(email)
    }
    //endregion


    //region PROFILE
    fun getObjectInfo(token: String): Single<BaseResponse<ObjectInfoResponse>> {
        return api.getObjectInfo(token)
    }

    fun getEvents(token: String): Single<BaseResponse<MutableList<Event>>> {
        return api.getEvents(token, 1)
    }

    fun getEventsNextPage(token: String, page: Int): Single<BaseResponse<MutableList<Event>>> {
        return api.getEvents(token, page)
    }

    fun calculateService(token: String, request: CalcServiceRequest): Single<BaseResponse<CalcServiceResponse>> {
        return api.calculateService(token,
                request.plants_count_s1, request.pots_count_s1,
                request.plants_count_s2, request.pots_count_s2,
                request.plants_count_s3, request.pots_count_s3,
                request.plants_count_s4, request.pots_count_s4,
                request.plants_count_s5, request.pots_count_s5)
    }

    fun getOrderList(token: String, request: OrderRequest): Single<BaseResponse<OrderResponse>> {
        return api.getOrderList(token, request.order_id)
    }
    //endregion


    //region CONTACTS
    fun getContacts(token: String): Single<BaseResponse<ContactsResponse>> {
        return api.getContacts(token)
    }

    fun getMeetingsList(token: String): Single<BaseResponse<List<Meeting>>> {
        return api.getMeetingsList(token)
    }

    fun addMeeting(token: String, request: MeetingAddRequest): Single<BaseResponse<MeetingsAddResponse>> {
        return api.addMeeting(token, request.manager_id, request.date)
    }
    //endregion


    //region CATALOG
    fun getCatalogSections(): Single<BaseResponse<MutableList<Section>>> {
        return api.getCatalogSections()
    }

    fun getSectionProductsList(token: String, request: SectionProductListRequest): Single<BaseResponse<MutableList<Product>>> {
        return api.getSectionProductsList(token, request.section_id, 1)
    }

    fun getSectionProductsListNextPage(token: String, request: SectionProductListRequest, page: Int): Single<BaseResponse<MutableList<Product>>> {
        return api.getSectionProductsList(token, request.section_id, page)
    }

    fun getProductDetail(token: String, request: ProductRequest): Single<BaseResponse<Product>> {
        return api.getProductDetail(token, request.product_id)
    }
    //endregion


    //region CART
    fun getCart(token: String): Single<BaseResponse<CartResponse>> {
        return api.getCart(token)
    }

    fun addToCart(token: String, request: AddToCartRequest): Single<BaseResponse<CartResponse>> {
        return api.addToCart(token, request.product_id, request.quantity)
    }

    fun makeOrder(token: String): Single<BaseResponse<MakeOrderResponse>> {
        return api.makeOrder(token)
    }
    //endregion


    //region FAVORITES
    fun editFavorites(token: String, request: EditFavoritesRequest): Single<BaseResponse<EditFavoriteResponse>> {
        return if (request.is_favorite) api.addToFavorites(token, request.product_id)
        else api.removeFromFavorites(token, request.product_id)
    }

    fun getFavorites(token: String): Single<BaseResponse<MutableList<Product>>> {
        return api.getFavorites(token)
    }
    //endregion


    //region PORTFOLIO
    fun getPortfolio(): Single<BaseResponse<List<PortfolioSection>>> {
        return api.getPortfolio()
    }
    //endregion


    //region MAP
    fun getMapObjects(): Single<BaseResponse<MapObjectsResponse>> {
        return api.getMapObjects()
    }
    //endregion


    //region MESSAGES
    fun addProjects(token: String, request: AddProjectRequest): Single<BaseResponse<AddProjectResponse>> {
        return api.addProjects(token, request.message, request.photos)
    }

    fun addMessages(token: String, request: AddMessageRequest): Single<BaseResponse<AddMessageResponse>> {
        return api.addMessages(token, request.theme, request.message, request.photos)
    }

    fun addClaims(token: String, request: AddClaimRequest): Single<BaseResponse<AddClaimResponse>> {
        return api.addClaims(token, request.message, request.photos)
    }

    fun addRatings(token: String, request: AddRatingRequest): Single<BaseResponse<AddRatingResponse>> {
        return api.addRatings(token, request.rating, request.message)
    }
    //endregion

}

interface GpApi {

    //region AUTH
    @FormUrlEncoded
    @POST("auth")
    fun auth(@Field("login") login: String,
             @Field("password") password: String): Single<BaseResponse<AuthResponse>>

    @FormUrlEncoded
    @POST("forgot_password")
    fun passwordRecovery(@Field("email") login: String): Single<BaseResponse<Any>>
    //endregion


    //region PROFILE
    @GET("objects/info")
    fun getObjectInfo(@Header("X-Auth-Token") token: String): Single<BaseResponse<ObjectInfoResponse>>

    @GET("events")
    fun getEvents(@Header("X-Auth-Token") token: String,
                  @Query("page") page: Int): Single<BaseResponse<MutableList<Event>>>

    @FormUrlEncoded
    @POST("calc/service")
    fun calculateService(@Header("X-Auth-Token") token: String,
                         @Field("plants_count_s1") plants_count_s1: Int, @Field("pots_count_s1") pots_count_s1: Int,
                         @Field("plants_count_s2") plants_count_s2: Int, @Field("pots_count_s2") pots_count_s2: Int,
                         @Field("plants_count_s3") plants_count_s3: Int, @Field("pots_count_s3") pots_count_s3: Int,
                         @Field("plants_count_s4") plants_count_s4: Int, @Field("pots_count_s4") pots_count_s4: Int,
                         @Field("plants_count_s5") plants_count_s5: Int, @Field("pots_count_s5") pots_count_s5: Int): Single<BaseResponse<CalcServiceResponse>>

    @GET("order/{order_id}")
    fun getOrderList(@Header("X-Auth-Token") token: String,
                     @Path("order_id") order_id: Int): Single<BaseResponse<OrderResponse>>
    //endregion


    //region CONTACTS
    @GET("contacts")
    fun getContacts(@Header("X-Auth-Token") token: String): Single<BaseResponse<ContactsResponse>>

    @GET("meetings")
    fun getMeetingsList(@Header("X-Auth-Token") token: String): Single<BaseResponse<List<Meeting>>>

    @FormUrlEncoded
    @POST("meetings/add")
    fun addMeeting(@Header("X-Auth-Token") token: String,
                   @Field("manager_id") manager_id: String,
                   @Field("date") date: String): Single<BaseResponse<MeetingsAddResponse>>
    //endregion


    //region CATALOG
    @GET("catalog/sections")
    fun getCatalogSections(): Single<BaseResponse<MutableList<Section>>>    

    @GET("catalog/sections/{section_id}")
    fun getSectionProductsList(@Header("X-Auth-Token") token: String,
                               @Path("section_id") section_id: Int,
                               @Query("page") page: Int): Single<BaseResponse<MutableList<Product>>>

    @GET("catalog/products/{product_id}")
    fun getProductDetail(@Header("X-Auth-Token") token: String,
                         @Path("product_id") product_id: Int): Single<BaseResponse<Product>>
    //endregion


    //region CART
    @GET("cart/info")
    fun getCart(@Header("X-Auth-Token") token: String): Single<BaseResponse<CartResponse>>

    @FormUrlEncoded
    @POST("cart/add")
    fun addToCart(@Header("X-Auth-Token") token: String,
                  @Field("product_id") product_id: Int,
                  @Field("quantity") quantity: Int): Single<BaseResponse<CartResponse>>

    @POST("order/add")
    fun makeOrder(@Header("X-Auth-Token") token: String): Single<BaseResponse<MakeOrderResponse>>
    //endregion


    //region FAVORITES
    @FormUrlEncoded
    @POST("catalog/favorites/add")
    fun addToFavorites(@Header("X-Auth-Token") token: String,
                       @Field("product_id") product_id: Int): Single<BaseResponse<EditFavoriteResponse>>

    @FormUrlEncoded
    @POST("catalog/favorites/delete")
    fun removeFromFavorites(@Header("X-Auth-Token") token: String,
                            @Field("product_id") product_id: Int): Single<BaseResponse<EditFavoriteResponse>>

    @GET("catalog/favorites")
    fun getFavorites(@Header("X-Auth-Token") token: String): Single<BaseResponse<MutableList<Product>>>
    //endregion


    //region PORTFOLIO
    @GET("portfolio")
    fun getPortfolio(): Single<BaseResponse<List<PortfolioSection>>>
    //endregion


    //region MAP
    @GET("objects/map")
    fun getMapObjects(): Single<BaseResponse<MapObjectsResponse>>
    //endregion


    //region MESSAGES
    @Multipart
    @POST("projects/add")
    fun addProjects(@Header("X-Auth-Token") token: String,
                    @Part("message") message: String,
                    @Part photos: List<MultipartBody.Part>): Single<BaseResponse<AddProjectResponse>>

    @Multipart
    @POST("messages/add")
    fun addMessages(@Header("X-Auth-Token") token: String,
                    @Part("theme") theme: String,
                    @Part("message") message: String,
                    @Part photos: List<MultipartBody.Part>): Single<BaseResponse<AddMessageResponse>>

    @Multipart
    @POST("claims/add")
    fun addClaims(@Header("X-Auth-Token") token: String,
                  @Part("message") message: String,
                  @Part photos: List<MultipartBody.Part>): Single<BaseResponse<AddClaimResponse>>

    @FormUrlEncoded
    @POST("ratings/add")
    fun addRatings(@Header("X-Auth-Token") token: String,
                   @Field("rating") rating: Int,
                   @Field("message") message: String): Single<BaseResponse<AddRatingResponse>>
    //endregion

}