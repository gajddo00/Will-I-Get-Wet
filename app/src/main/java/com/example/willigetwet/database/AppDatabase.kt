package cs.jejda.start.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.willigetwet.model.CityInfo

@Database(entities = [CityInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun TODOItemDao(): CityInfoDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "AppDatabase"
                ).build()
            }
            return INSTANCE
        }

        fun destroyAppDatabase() {
            INSTANCE = null
        }
    }
}