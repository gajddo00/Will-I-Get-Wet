package cs.jejda.start.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.willigetwet.model.CityInfo

@Dao
interface CityInfoDao {

    @Query("SELECT * from CityInfo")
    fun getCitiesByName(): List<CityInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(cities: List<CityInfo>)

    @Transaction
    open fun insertData(cities: List<CityInfo>) {
        insertCities(cities)
    }
}