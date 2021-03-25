package easv.oe.roomdb1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BEPerson(
        @PrimaryKey(autoGenerate = true) var id:Int,
                                       var name: String,
                                        var age: Int)