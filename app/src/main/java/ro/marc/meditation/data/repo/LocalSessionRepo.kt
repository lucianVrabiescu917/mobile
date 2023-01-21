package ro.marc.meditation.data.repo

import ro.marc.meditation.data.db.dao.SessionDAO
import ro.marc.meditation.data.db.entity.SessionEntity
import ro.marc.meditation.data.model.Session


class LocalSessionRepo(
    private val sessionDAO: SessionDAO
) {

    fun postSession(session: Session) {
        sessionDAO.save(SessionEntity.from(session))
    }

    fun getAll(): List<Session> = sessionDAO.fetch().map(Session::from)

    fun updateLocation(id: Long, location: String) {
        sessionDAO.updateLocation(id, location)
    }

    fun delete(id: Long) {
        sessionDAO.delete(id)
    }

    fun getUncommitted(): List<Session> = sessionDAO.fetchUncommitted().map(Session::from)

    fun setId(localId: Long, id: Long) = sessionDAO.setId(localId, id)

    fun setCommitted(localId: Long) = sessionDAO.setCommitted(localId)

    fun clear() = sessionDAO.clear()

}