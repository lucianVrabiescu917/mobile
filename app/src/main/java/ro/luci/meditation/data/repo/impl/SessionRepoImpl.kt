package ro.luci.meditation.data.repo.impl

import ro.luci.meditation.data.db.dao.SessionDAO
import ro.luci.meditation.data.db.entity.SessionEntity
import ro.luci.meditation.data.model.Session
import ro.luci.meditation.data.repo.SessionRepo


class SessionRepoImpl(
    private val sessionDAO: SessionDAO
): SessionRepo {

    override fun postSession(session: Session) {
        sessionDAO.save(SessionEntity.from(session))
    }

    override fun getAll(): List<Session> = sessionDAO.fetch().map(Session::from)

    override fun updateLocation(id: Long, location: String) {
        sessionDAO.updateLocation(id, location)
    }

    override fun delete(id: Long) {
        sessionDAO.delete(id)
    }

}