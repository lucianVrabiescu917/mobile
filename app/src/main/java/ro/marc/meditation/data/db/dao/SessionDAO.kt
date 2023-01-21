package ro.marc.meditation.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ro.marc.meditation.data.db.entity.SessionEntity


@Dao
interface SessionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(payment: SessionEntity)

    @Query("SELECT * FROM sessions ORDER BY id DESC")
    fun fetch(): List<SessionEntity>

    @Query("UPDATE sessions SET location = :location WHERE local_id = :id")
    fun updateLocation(id: Long, location: String)

    @Query("DELETE FROM sessions WHERE local_id = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM sessions WHERE committed = 0")
    fun fetchUncommitted(): List<SessionEntity>

    @Query("UPDATE sessions SET id = :id WHERE local_id = :localId")
    fun setId(localId: Long, id: Long)

    @Query("UPDATE sessions SET committed = 1 WHERE local_id = :localId")
    fun setCommitted(localId: Long)

    @Query("DELETE FROM sessions WHERE committed = 1")
    fun clear()

}
