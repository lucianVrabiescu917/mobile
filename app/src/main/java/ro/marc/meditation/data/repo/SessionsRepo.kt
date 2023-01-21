package ro.marc.meditation.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ro.marc.meditation.data.api.CallManagement
import ro.marc.meditation.data.api.CallResponse
import ro.marc.meditation.data.api.GenericResponseDTO
import ro.marc.meditation.data.dto.ChangeLocationDTO
import ro.marc.meditation.data.dto.SessionDTO
import ro.marc.meditation.data.service.SessionsService

class SessionsRepo(
    private val sessionsService: SessionsService,
) {

    fun getAll(): LiveData<CallResponse<List<SessionDTO>>> {
        val callResponse = MutableLiveData<CallResponse<List<SessionDTO>>>()
        callResponse.value = CallResponse.Loading()

        sessionsService.getAll().enqueue(object :
            Callback<GenericResponseDTO<List<SessionDTO>>> {
            override fun onResponse(
                call: Call<GenericResponseDTO<List<SessionDTO>>>,
                response: Response<GenericResponseDTO<List<SessionDTO>>>
            ) {
                CallManagement.manageOnResponse(response, callResponse)
            }

            override fun onFailure(
                call: Call<GenericResponseDTO<List<SessionDTO>>>,
                t: Throwable
            ) {
                CallManagement.manageOnFailure(t, callResponse)
            }

        })

        return callResponse
    }


    fun postSession(session: SessionDTO): LiveData<CallResponse<SessionDTO>> {
        val callResponse = MutableLiveData<CallResponse<SessionDTO>>()
        callResponse.value = CallResponse.Loading()

        sessionsService.postSession(session).enqueue(object : Callback<GenericResponseDTO<SessionDTO>> {
            override fun onResponse(
                call: Call<GenericResponseDTO<SessionDTO>>,
                response: Response<GenericResponseDTO<SessionDTO>>
            ) {
                CallManagement.manageOnResponse(response, callResponse)
            }

            override fun onFailure(
                call: Call<GenericResponseDTO<SessionDTO>>,
                t: Throwable
            ) {
                CallManagement.manageOnFailure(t, callResponse)
            }

        })

        return callResponse
    }

    fun changeLocation(id: Long, dto: ChangeLocationDTO): LiveData<CallResponse<SessionDTO>> {
        val callResponse = MutableLiveData<CallResponse<SessionDTO>>()
        callResponse.value = CallResponse.Loading()

        sessionsService.changeLocation(id, dto).enqueue(object : Callback<GenericResponseDTO<SessionDTO>> {
            override fun onResponse(
                call: Call<GenericResponseDTO<SessionDTO>>,
                response: Response<GenericResponseDTO<SessionDTO>>
            ) {
                CallManagement.manageOnResponse(response, callResponse)
            }

            override fun onFailure(
                call: Call<GenericResponseDTO<SessionDTO>>,
                t: Throwable
            ) {
                CallManagement.manageOnFailure(t, callResponse)
            }

        })

        return callResponse
    }

    fun remove(id: Long): LiveData<CallResponse<Void>> {
        val callResponse = MutableLiveData<CallResponse<Void>>()
        callResponse.value = CallResponse.Loading()

        sessionsService.delete(id).enqueue(object : Callback<GenericResponseDTO<Void>> {
            override fun onResponse(
                call: Call<GenericResponseDTO<Void>>,
                response: Response<GenericResponseDTO<Void>>
            ) {
                CallManagement.manageOnResponse(response, callResponse)
            }

            override fun onFailure(
                call: Call<GenericResponseDTO<Void>>,
                t: Throwable
            ) {
                CallManagement.manageOnFailure(t, callResponse)
            }

        })

        return callResponse
    }

}