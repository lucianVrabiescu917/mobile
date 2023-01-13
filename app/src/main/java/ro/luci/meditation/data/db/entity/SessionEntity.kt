package ro.luci.meditation.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ro.luci.meditation.data.model.Session


@Entity(tableName = "sessions")
data class SessionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val location: String = "",

    @ColumnInfo(name = "duration")
    val durationInSeconds: Int = 0,

) {

    companion object {

        fun from(session: Session) = SessionEntity(
            location = session.location,
            durationInSeconds = session.durationInSeconds,
        )

    }

}
