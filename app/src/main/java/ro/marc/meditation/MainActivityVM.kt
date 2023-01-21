package ro.marc.meditation

import androidx.lifecycle.ViewModel
import ro.marc.meditation.data.dto.ChangeLocationDTO
import ro.marc.meditation.data.dto.SessionDTO
import ro.marc.meditation.data.model.Session
import ro.marc.meditation.data.repo.LocalSessionRepo
import ro.marc.meditation.data.repo.SessionsRepo


class MainActivityVM(
    private val localSessionRepo: LocalSessionRepo,
    private val sessionsRepo: SessionsRepo,
): ViewModel() {

    fun addLocalSession(session: Session) {
        localSessionRepo.postSession(session)
    }

    fun getSessions(): List<Session> = localSessionRepo.getAll()

    fun removeLocalSession(id: Long) {
        localSessionRepo.delete(id)
    }

    fun updateLocalLocation(id: Long, location: String) {
        localSessionRepo.updateLocation(id, location)
    }

    fun clearLocal() = localSessionRepo.clear()

    fun getUncommitted() = localSessionRepo.getUncommitted()

    fun setId(localId: Long, id: Long) = localSessionRepo.setId(localId, id)

    fun setCommitted(localId: Long) = localSessionRepo.setCommitted(localId)

    fun postSession(dto: SessionDTO) = sessionsRepo.postSession(dto)

    fun fetchAll() = sessionsRepo.getAll()

    fun updateLocation(id: Long, dto: ChangeLocationDTO) = sessionsRepo.changeLocation(id, dto)

    fun remove(id: Long) = sessionsRepo.remove(id)

}
