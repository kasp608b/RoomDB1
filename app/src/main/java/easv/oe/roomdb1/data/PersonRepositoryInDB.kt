package easv.oe.roomdb1.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import java.lang.IllegalStateException
import java.util.concurrent.Executors

class PersonRepositoryInDB  {

    val TAG = "xyz"

    private val database: PersonDatabase

    private val personDao : PersonDao

    private lateinit var cache: List<BEPerson>

    private constructor(context: Context) {

        database = Room.databaseBuilder(context.applicationContext,
                PersonDatabase::class.java,
                "person-database").build()

        personDao = database.personDao()

        val updateCacheObserver = Observer<List<BEPerson>>{ persons ->
            cache = persons;
            Log.d(TAG, "Update Cache observer notified")
        }
        getAllLiveData().observe(context as LifecycleOwner, updateCacheObserver)
    }

    fun getAllLiveData(): LiveData<List<BEPerson>> = personDao.getAll()
    

    fun getById(id: Int): BEPerson? {
        cache.forEach { p -> if (p.id == id) return p; }
        return null;
    }

    fun getByPos(pos: Int): BEPerson? {
        if (pos < cache.size)
            return cache[pos]
        return null
    }

    fun getByFilterName(filter: String): List<BEPerson> {
        return cache.filter{ p -> p.name.contains(filter, true)}
    }

    private val executor = Executors.newSingleThreadExecutor()

    fun insert(p: BEPerson) {
        executor.execute{ personDao.insert(p) }
    }

    fun update(p: BEPerson) {
        executor.execute { personDao.update(p) }
    }

    fun delete(p: BEPerson) {
        executor.execute { personDao.delete(p) }
    }

    fun clear() {
        executor.execute { personDao.deleteAll() }
    }


    companion object {
        private var Instance: PersonRepositoryInDB? = null

        fun initialize(context: Context) {
            if (Instance == null)
                Instance = PersonRepositoryInDB(context)
        }

        fun get(): PersonRepositoryInDB {
            if (Instance != null) return Instance!!
            throw IllegalStateException("Person repo not initialized")
        }
    }







}