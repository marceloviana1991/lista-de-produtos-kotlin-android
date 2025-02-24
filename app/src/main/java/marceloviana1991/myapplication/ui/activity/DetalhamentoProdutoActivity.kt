package marceloviana1991.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import marceloviana1991.myapplication.R
import marceloviana1991.myapplication.database.AppDataBase
import marceloviana1991.myapplication.databinding.ActivityDetalhamentoProdutoBinding
import marceloviana1991.myapplication.extensions.carregarImagem
import marceloviana1991.myapplication.extensions.formataParaMoedaBrasileira
import marceloviana1991.myapplication.model.Produto

class DetalhamentoProdutoActivity : AppCompatActivity() {


    private var produtoId: Long = 0L
    private var produto: Produto? = null
    private val binding by lazy {
        ActivityDetalhamentoProdutoBinding.inflate(layoutInflater)
    }
    val produtoDao by lazy {
        AppDataBase.instancia(this).produtoDao()
    }
    private val scope = CoroutineScope(Dispatchers.IO)

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

    override fun onResume() {
        super.onResume()
        scope.launch {
            produto = produtoDao.buscaPorId(produtoId)
            withContext(Dispatchers.Main) {
                produto?.let {
                    preencheCampos(it)
                }?: finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(produto != null) {
            when (item.itemId) {
                R.id.menu_detalhes_produto_editar -> {
                    Intent(this, FormularioProdutoActivity::class.java).apply {
                        putExtra(CHAVE_PRODUTO_ID, produtoId)
                        startActivity(this)
                    }
                }
                R.id.menu_detalhes_produto_remover -> {
                    scope.launch {
                        produto?.let {
                            produtoDao.remove(it)
                        }
                        finish()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
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