package marceloviana1991.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import marceloviana1991.myapplication.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos(): List<Produto>

    @Insert
    fun salva(vararg produto: Produto)

    @Delete
    fun remove(vararg produto: Produto)
}