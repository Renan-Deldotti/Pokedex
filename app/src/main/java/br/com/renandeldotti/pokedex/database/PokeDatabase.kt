package br.com.renandeldotti.pokedex.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Region::class], version = 1, exportSchema = false)
abstract class PokeDatabase : RoomDatabase() {

    abstract val regionDao: RegionDao

    companion object {

        private const val DATABASE_NAME: String = "poke_database"

        @Volatile
        private var INSTANCE: PokeDatabase? = null

        fun getInstance(context: Context): PokeDatabase {
            synchronized(this) {
                var tempInstance = INSTANCE
                if (tempInstance == null) {
                    tempInstance = Room.databaseBuilder(
                        context.applicationContext,
                        PokeDatabase::class.java,
                        DATABASE_NAME
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = tempInstance
                }
                return tempInstance
            }
        }
    }
}