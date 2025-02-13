package marceloviana1991.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import marceloviana1991.myapplication.database.converter.Conversor
import marceloviana1991.myapplication.database.dao.ProdutoDao
import marceloviana1991.myapplication.model.Produto

@Database(entities = [Produto::class], version = 1)
@TypeConverters(Conversor::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao

    companion object {
        fun instancia(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "lista-de-produtos.db"
            ).build()
        }
    }
}