package ro.marc.meditation.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ro.marc.meditation.data.db.dao.SessionDAO
import ro.marc.meditation.data.db.entity.SessionEntity


@Database(entities = [SessionEntity::class], version = 2)
abstract class Database: RoomDatabase() {

    abstract fun sessionDAO(): SessionDAO

}
