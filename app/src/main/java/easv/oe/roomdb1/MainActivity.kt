package easv.oe.roomdb1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    val TAG = "xyz"

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

    private fun setupDataObserver() {
        val mRep = PersonRepositoryInDB.get()
        val updateGUIObserver = Observer<List<BEPerson>>{ persons ->
            val asStrings = persons.map { p -> "${p.id}, ${p.name}"}
             val adapter: ListAdapter = ArrayAdapter(
                     this,
                              android.R.layout.simple_list_item_1,
                              asStrings.toTypedArray()
                                                    )
             lvNames.adapter = adapter
            Log.d(TAG, "UpdateGUI Observer notified")
        }
        mRep.getAllLiveData().observe(this, updateGUIObserver)

        lvNames.onItemClickListener = AdapterView.OnItemClickListener {_,_,pos,_ -> onClickPerson(pos)}
    }

    private fun onClickPerson(pos: Int) {
        val mRep = PersonRepositoryInDB.get()
        val person = mRep.getByPos(pos)
        if (person != null)
        {
            Toast.makeText(this, "You have clicked ${person} ", Toast.LENGTH_LONG).show()
        }
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