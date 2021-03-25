package easv.oe.roomdb1.data

import androidx.room.Database
import androidx.room.RoomDatabase
import easv.oe.roomdb1.data.BEPerson
import easv.oe.roomdb1.data.PersonDao

@Database(entities = [BEPerson::class], version=1)
abstract class PersonDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao
}