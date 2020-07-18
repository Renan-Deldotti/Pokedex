package br.com.renandeldotti.pokedex.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "region_table")
@Parcelize
data class Region(val regionName:String, val regionId:String) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L
}