package br.com.renandeldotti.pokedex.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity(tableName = "regions_table")
@Parcelize
data class Regions(
    val name: String,
    val regionId: Int
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}