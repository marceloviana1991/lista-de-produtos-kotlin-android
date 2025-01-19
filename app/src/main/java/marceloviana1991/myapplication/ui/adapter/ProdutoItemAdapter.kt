package marceloviana1991.myapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import marceloviana1991.myapplication.model.Produto
import marceloviana1991.myapplication.databinding.AdapterProdutoItemBinding

class ProdutoItemAdapter(
    private val context: Context,
    private val produtos: List<Produto>
) : RecyclerView.Adapter<ProdutoItemAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: AdapterProdutoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun vincula(produto: Produto) {
            val nome = binding.textViewNomeAdapterProdutoItem
            nome.text = produto.nome
            val descricao = binding.textViewDescricaoAdapterProdutoItem
            descricao.text = produto.descricao
            val valor = binding.textViewValorAdapterProdutoItem
            valor.text = produto.valor.toPlainString()
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

}