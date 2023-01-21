package ro.marc.meditation.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ro.marc.meditation.data.model.Session


@Entity(tableName = "sessions")
data class SessionEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "local_id")
    val localId: Long = 0L,

    val id: Long? = null,

    val location: String = "",

    @ColumnInfo(name = "duration")
    val durationInSeconds: Int = 0,

    val committed: Boolean = false,

) {

    companion object {

        fun from(session: Session) = SessionEntity(
            localId = session.localId ?: 0,
            id = session.id,
            location = session.location,
            durationInSeconds = session.durationInSeconds,
            committed = session.committed,
        )

    }

}
