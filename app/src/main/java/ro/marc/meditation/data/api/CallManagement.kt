package ro.marc.meditation.data.api

import androidx.lifecycle.MutableLiveData
import retrofit2.Response


object CallManagement {
    
    fun <T> manageOnResponse(response: Response<GenericResponseDTO<T>>, callResponse: MutableLiveData<CallResponse<T>>) {
        callResponse.value = CallResponse.Success(response.body()!!)
    }
    
    fun <T> manageOnFailure(t: Throwable, callResponse: MutableLiveData<CallResponse<T>>) {
        println("Failed, see throwable below")
        t.printStackTrace()
        callResponse.value = CallResponse.Error()
    }
}