package br.com.renandeldotti.pokedex.ui.pokemondetail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import br.com.renandeldotti.pokedex.MainActivity
import br.com.renandeldotti.pokedex.R
import br.com.renandeldotti.pokedex.databinding.FragmentPokemonDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.util.*

class PokemonDetailFragment : Fragment() {

    private lateinit var binding: FragmentPokemonDetailBinding
    private lateinit var viewModel: PokemonDetailViewModel
    private val args: PokemonDetailFragmentArgs by navArgs()
    private var pokemonId:Int = 0

    companion object {
        private const val TAG:String = "PokemonDetailFragment"
    }

    @ExperimentalStdlibApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_detail, container, false)
        binding.textChangedColor = R.color.colorPrimaryTextLight
        viewModel = ViewModelProvider(this)[PokemonDetailViewModel::class.java]

        (activity as MainActivity).changeAppBarTitle("")

        viewModel.getPokemonData(args.pokemonId).observe(viewLifecycleOwner, Observer {
            binding.pokemonName = it.name.toUpperCase(Locale.getDefault())
            binding.pokemonId = getString(R.string.pokedex_id) + it.id.toString()
            binding.pokemonType = try {
                it.types[0].type.name.capitalize(Locale.ROOT)
            }catch (e:Exception){
                ""
            }
            pokemonId = it.id
            changeImage()
        })

        viewModel.getPokemonDesc(args.pokemonId).observe(viewLifecycleOwner, Observer {
            if (!it.flavor_text_entries.isNullOrEmpty()){
                binding.pokemonDescription.text = it.flavor_text_entries[0].flavor_text
            }
        })

        binding.pokemonDetailSwitchShiny.setOnCheckedChangeListener { _, _ ->
            changeImage()
        }

        return binding.root
    }

    private fun changeImage(){
        if (pokemonId == 0){
            return
        }
        if(binding.pokemonDetailSwitchShiny.isChecked){
            Glide.with(binding.pokemonDetailPokemonImage)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$pokemonId.png")
                .placeholder(R.drawable.ditto_shiny_placeholder)
                .into(binding.pokemonDetailPokemonImage)
        }else{
            Glide.with(binding.pokemonDetailPokemonImage)
                .asBitmap()
                .placeholder(R.drawable.ditto_shiny_placeholder)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png")
                .into(object: CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}

                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        binding.pokemonDetailPokemonImage.setImageBitmap(resource)
                        Palette.from(resource).generate{
                            it?.let {
                                val vibrantSwatch = it.vibrantSwatch
                                val lightVibrantSwatch = it.lightVibrantSwatch
                                val darkVibrantSwatch = it.darkVibrantSwatch

                                lightVibrantSwatch?.rgb?.let { lightRgb->
                                    darkVibrantSwatch?.rgb?.let { darkRgb->
                                        val gradient = GradientDrawable(
                                            GradientDrawable.Orientation.BOTTOM_TOP,
                                            arrayOf(lightRgb,darkRgb).toIntArray()
                                        )
                                        binding.root.background = gradient
                                    }
                                }

                                vibrantSwatch?.titleTextColor?.let { textColor->
                                    binding.textChangedColor = textColor
                                }
                            }
                        }
                    }
                })
        }
    }
}