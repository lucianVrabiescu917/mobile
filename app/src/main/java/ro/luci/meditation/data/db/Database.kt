package ro.luci.meditation.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ro.luci.meditation.data.db.dao.SessionDAO
import ro.luci.meditation.data.db.entity.SessionEntity


@Database(entities = [SessionEntity::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun sessionDAO(): SessionDAO

}
