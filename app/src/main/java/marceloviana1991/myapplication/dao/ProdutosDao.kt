package marceloviana1991.myapplication.dao

import marceloviana1991.myapplication.model.Produto

class ProdutosDao {

    companion object {
        private val produtos = mutableListOf<Produto>()

        fun adiciona(produto: Produto) {
            produtos.add(produto)
        }

        fun buscaTodos(): List<Produto> {
            return produtos.toList()
        }
    }

}