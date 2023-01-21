package ro.marc.meditation

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object NetworkUtils {

    private lateinit var connectivityManager: ConnectivityManager
    var hasNetwork: Boolean? = null
        internal set

    private val _reconnectedLiveData = MutableLiveData<Boolean>()
    val reconnectedLiveData: LiveData<Boolean>
        get() = _reconnectedLiveData


    @SuppressLint("MissingPermission")
    internal fun init(ctx: MainActivity) {
        if (::connectivityManager.isInitialized) return

        connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        hasNetwork = connectivityManager.activeNetwork != null
        _reconnectedLiveData.postValue(false)

        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                if (hasNetwork == false) {
                    hasNetwork = true
                    ctx.runOnUiThread {
                        _reconnectedLiveData.value = true
                    }
                    _reconnectedLiveData.postValue(false)
                    return
                }
                hasNetwork = true
                _reconnectedLiveData.postValue(false)
            }

            override fun onLost(network: Network) {
                hasNetwork = false
                _reconnectedLiveData.postValue(false)
            }

        })
    }
    
}
