package ro.luci.meditation

import androidx.lifecycle.ViewModel
import ro.luci.meditation.data.model.Session
import ro.luci.meditation.data.repo.SessionRepo


class MainActivityVM(
    private val sessionRepo: SessionRepo,
): ViewModel() {

    fun postSession(session: Session) {
        sessionRepo.postSession(session)
    }

    fun getSessions(): List<Session> = sessionRepo.getAll()

    fun removeSession(id: Long) {
        sessionRepo.delete(id)
    }

    fun updateLocation(id: Long, location: String) {
        sessionRepo.updateLocation(id, location)
    }

}
