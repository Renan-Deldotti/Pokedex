package br.com.renandeldotti.pokedex.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "pokemon_table")
@Parcelize
data class Pokemon(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val nome: String,
    val url: String,
    val fromPokedex: Int
<<<<<<< HEAD
) : Parcelable{
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
}
=======
) : Parcelable
>>>>>>> parent of 36bfc38... Update
