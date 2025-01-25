package marceloviana1991.myapplication.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import marceloviana1991.myapplication.model.Produto
import marceloviana1991.myapplication.databinding.AdapterProdutoItemBinding
import marceloviana1991.myapplication.extensions.carregarImagem
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class ProdutoItemAdapter(
    private val context: Context,
    produtos: List<Produto>
) : RecyclerView.Adapter<ProdutoItemAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()

    class ViewHolder(
        private val binding: AdapterProdutoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun vincula(produto: Produto) {
            val nome = binding.textViewNomeAdapterProdutoItem
            nome.text = produto.nome
            val descricao = binding.textViewDescricaoAdapterProdutoItem
            descricao.text = produto.descricao
            val valor = binding.textViewValorAdapterProdutoItem
            val valorEmMoeda = formataParaMoedaBrasileira(produto.valor)
            valor.text = valorEmMoeda
            binding.imageViewAdapterProdutoItem.carregarImagem(produto.imagem)
        }

        private fun formataParaMoedaBrasileira(valor: BigDecimal): String {
            val formatador: NumberFormat = NumberFormat
                .getCurrencyInstance(Locale("pt", "br"))
            val valorEmMoeda = formatador.format(valor)
            return valorEmMoeda
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