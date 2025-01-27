package marceloviana1991.myapplication.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import marceloviana1991.myapplication.model.Produto
import marceloviana1991.myapplication.databinding.AdapterProdutoItemBinding
import marceloviana1991.myapplication.extensions.carregarImagem
import marceloviana1991.myapplication.extensions.formataParaMoedaBrasileira

class ProdutoItemAdapter(
    private val context: Context,
    produtos: List<Produto>,
    var quandoClicaNoItem: (produto: Produto) -> Unit = {}
) : RecyclerView.Adapter<ProdutoItemAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()

    inner class ViewHolder(
        private val binding: AdapterProdutoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var produto: Produto

        init {
            itemView.setOnClickListener {
                if (::produto.isInitialized) {
                    quandoClicaNoItem(produto)
                }
            }
        }

        fun vincula(produto: Produto) {
            this.produto = produto
            val nome = binding.textViewNomeAdapterProdutoItem
            nome.text = produto.nome
            val descricao = binding.textViewDescricaoAdapterProdutoItem
            descricao.text = produto.descricao
            val valor = binding.textViewValorAdapterProdutoItem
            valor.text = produto.valor.formataParaMoedaBrasileira()
            binding.imageViewAdapterProdutoItem.carregarImagem(produto.imagem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = AdapterProdutoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return produtos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }
}