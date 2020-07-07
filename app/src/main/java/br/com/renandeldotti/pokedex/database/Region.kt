package br.com.renandeldotti.pokedex.database

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "regions_table")
@Parcelize
data class Region(val regionName:String, val regionId:String) : Parcelable {
    var id:Long = 0
}