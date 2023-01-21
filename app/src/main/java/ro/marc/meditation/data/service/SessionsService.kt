package ro.marc.meditation.data.service

import retrofit2.Call
import retrofit2.http.*
import ro.marc.meditation.data.api.GenericResponseDTO
import ro.marc.meditation.data.dto.ChangeLocationDTO
import ro.marc.meditation.data.dto.SessionDTO

interface SessionsService {

    @GET("/")
    fun getAll(): Call<GenericResponseDTO<List<SessionDTO>>>

    @POST("/")
    fun postSession(@Body sessionDTO: SessionDTO): Call<GenericResponseDTO<SessionDTO>>

    @PATCH("/{id}")
    fun changeLocation(@Path("id") id: Long, @Body body: ChangeLocationDTO): Call<GenericResponseDTO<SessionDTO>>

    @DELETE("/{id}")
    fun delete(@Path("id") id: Long): Call<GenericResponseDTO<Void>>

}