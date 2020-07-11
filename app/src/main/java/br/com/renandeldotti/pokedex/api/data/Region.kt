package br.com.renandeldotti.pokedex.api.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Region(
    @SerializedName("results")
    val results: @RawValue List<Results>
) : Parcelable

data class Results(
    val name: String,
    val url: String
)