package marceloviana1991.myapplication.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import marceloviana1991.myapplication.databinding.ActivityDetalhamentoProdutoBinding
import marceloviana1991.myapplication.extensions.carregarImagem
import marceloviana1991.myapplication.extensions.formataParaMoedaBrasileira
import marceloviana1991.myapplication.model.Produto

class DetalhamentoProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhamentoProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.activityDetalhamentoProduto) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tentaCarregarProduto()
    }

    private fun tentaCarregarProduto() {
        //verificação de versão do compilador do SDK
        val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //método novo para os SDK mais novos
            intent.getParcelableExtra(CHAVE_PRODUTO,Produto::class.java)
        } else{
            //método deprecated  para os SDK mais antigos
            intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)
        }
        userData?.let { produtoCarregado ->
            preencheCampos(produtoCarregado)
        }?: finish()
    }

    private fun preencheCampos(produtoCarregado: Produto) {
        with(binding) {
            imageViewActivityDetalhamentoProduto.carregarImagem(produtoCarregado.imagem)
            textViewValorProdutoActivityDetalhamentoProduto.text =
                produtoCarregado.valor.formataParaMoedaBrasileira()
            textViewNomeProdutoActivityDetalhamentoProduto.text = produtoCarregado.nome
            textViewDescricaoProdutoActivityDetalhamentoProduto.text = produtoCarregado.descricao
        }
    }
}