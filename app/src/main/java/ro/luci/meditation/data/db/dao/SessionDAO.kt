package ro.luci.meditation.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ro.luci.meditation.data.db.entity.SessionEntity


@Dao
interface SessionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(payment: SessionEntity)

    @Query("SELECT * FROM sessions ORDER BY id DESC")
    fun fetch(): List<SessionEntity>

    @Query("UPDATE sessions SET location = :location WHERE id = :id")
    fun updateLocation(id: Long, location: String)

    @Query("DELETE FROM sessions WHERE id = :id")
    fun delete(id: Long)

}
