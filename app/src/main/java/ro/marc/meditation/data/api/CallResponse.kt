package ro.marc.meditation.data.api


sealed class CallResponse<T>(val genericResponseDTO: GenericResponseDTO<T>?) {
    
    class Success<T>(genericResponseDTO: GenericResponseDTO<T>) : CallResponse<T>(genericResponseDTO)

    class Loading<T> : CallResponse<T>(null)

    class Error<T> : CallResponse<T>(null)

}
