package ro.marc.meditation.data.model

import ro.marc.meditation.data.db.entity.SessionEntity
import ro.marc.meditation.data.dto.SessionDTO


data class Session(

    val localId: Long? = null,

    val id: Long? = null,

    val location: String = "",

    val durationInSeconds: Int = 0,

    val committed: Boolean = false,

) {

    companion object {

        fun from(sessionEntity: SessionEntity) = Session(
            sessionEntity.localId,
            sessionEntity.id,
            sessionEntity.location,
            sessionEntity.durationInSeconds,
            sessionEntity.committed,
        )

        fun from(dto: SessionDTO) = Session(
            null,
            dto.id,
            dto.location,
            dto.duration,
            dto.id != null,
        )

    }

}
