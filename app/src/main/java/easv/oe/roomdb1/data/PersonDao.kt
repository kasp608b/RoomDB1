package easv.oe.roomdb1.data

import androidx.lifecycle.LiveData
import androidx.room.*
import easv.oe.roomdb1.data.BEPerson

@Dao
interface PersonDao {

    @Query("SELECT * from BEPerson order by id")
    fun getAll(): LiveData<List<BEPerson>>

    @Query("SELECT name from BEPerson order by name")
    fun getAllNames(): LiveData<List<String>>

    @Query("SELECT * from BEPerson where id = (:id)")
    fun getById(id: Int): LiveData<BEPerson>

    @Insert
    fun insert(p: BEPerson)

    @Update
    fun update(p: BEPerson)

    @Delete
    fun delete(p: BEPerson)

    @Query("DELETE from BEPerson")
    fun deleteAll()
}