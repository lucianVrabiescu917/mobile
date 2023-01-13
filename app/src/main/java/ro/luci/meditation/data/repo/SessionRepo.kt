package ro.luci.meditation.data.repo

import ro.luci.meditation.data.model.Session


interface SessionRepo {

    fun postSession(session: Session)

    fun getAll(): List<Session>

    fun updateLocation(id: Long, location: String)

    fun delete(id: Long)

}
