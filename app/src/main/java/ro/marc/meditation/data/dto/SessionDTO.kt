package ro.marc.meditation.data.dto

import ro.marc.meditation.data.model.Session


data class SessionDTO(
    val id: Long?,
    val location: String,
    val duration: Int,
) {

    companion object {
        fun from(session: Session) = SessionDTO(
            session.id,
            session.location,
            session.durationInSeconds,
        )
    }

}