package br.com.renandeldotti.pokedex.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "pokemon_table")
@Parcelize
data class Pokemon(
    val name: String,
    val url: String,
    val fromPokedex: Int
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}