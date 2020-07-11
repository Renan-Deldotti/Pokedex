package br.com.renandeldotti.pokedex.api.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class PokemonListFromApi(
    val next: String,
    val previous: String,
    val results: @RawValue List<Results>
) : Parcelable