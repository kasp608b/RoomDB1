package easv.oe.roomdb1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import easv.oe.roomdb1.data.BEPerson
import easv.oe.roomdb1.data.PersonRepositoryInDB
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PersonRepositoryInDB.initialize(this)
        //insertTestData()

        setupDataObserver()

    }

    private fun insertTestData() {
        val mRep = PersonRepositoryInDB.get()
        mRep.insert(BEPerson(0,"Rip", 3))
        mRep.insert(BEPerson(0,"Rap", 3))
        mRep.insert(BEPerson(0,"Rup", 3))
    }

    var cache: List<BEPerson>? = null;

    private fun setupDataObserver() {
        val mRep = PersonRepositoryInDB.get()
        val nameObserver = Observer<List<BEPerson>>{ persons ->
            cache = persons;
            val asStrings = persons.map { p -> "${p.id}, ${p.name}"}
             val adapter: ListAdapter = ArrayAdapter(
                     this,
                              android.R.layout.simple_list_item_1,
                              asStrings.toTypedArray()
                                                    )
             lvNames.adapter = adapter
        }
        mRep.getAll().observe(this, nameObserver)

        lvNames.onItemClickListener = AdapterView.OnItemClickListener {_,_,pos,_ -> onClickPerson(pos)}
    }

    private fun onClickPerson(pos: Int) {
        val id = cache!![pos].id
        //Toast.makeText(this, "You have clicked $name at position $pos", Toast.LENGTH_LONG).show()
        val personObserver = Observer<BEPerson> { person ->
            Toast.makeText(this, "You have clicked ${person.name} ", Toast.LENGTH_LONG).show()

        }
        val mRep = PersonRepositoryInDB.get()
        mRep.getById(id).observe(this , personObserver)

    }

    fun onClickInsert(view: View) {
        val mRep = PersonRepositoryInDB.get()
        mRep.insert(BEPerson(0, etName.text.toString(), 23))
    }

    fun onClickClear(view: View) {
        val mRep = PersonRepositoryInDB.get()
        mRep.clear()
    }
}