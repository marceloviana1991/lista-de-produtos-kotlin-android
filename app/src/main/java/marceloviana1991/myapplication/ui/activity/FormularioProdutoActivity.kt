package marceloviana1991.myapplication.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import marceloviana1991.myapplication.database.AppDataBase
import marceloviana1991.myapplication.databinding.ActivityFormularioProdutoBinding
import marceloviana1991.myapplication.extensions.carregarImagem
import marceloviana1991.myapplication.model.Produto
import marceloviana1991.myapplication.ui.alertdialog.FormularioImagemAlertDialog
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var produtoId = 0L
    private val produtoDao by lazy {
        val db = AppDataBase.instancia(this)
        db.produtoDao()
    }
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Cadastrar Produto"

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.activityFormularioProduto) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.imageViewActivityFormularioProduto.setOnClickListener {
            FormularioImagemAlertDialog(this).carregaImagem(url) {
                url -> this.url = url
                binding.imageViewActivityFormularioProduto.carregarImagem(url)
            }
        }

        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)

        val buttonSalvar = binding.buttonSalvarActivityFormularioProduto

        buttonSalvar.setOnClickListener {
            val produto = capturaDadosDoEditText()
            scope.launch {
                produtoDao.salva(produto)
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        scope.launch {
            produtoDao.buscaPorId(produtoId)?.let {
                withContext(Dispatchers.Main) {
                    preencheCampos(it)
                }
            }
        }
    }

    private fun preencheCampos(produto: Produto) {
        title = "Editar Produto"
        url = produto.imagem
        binding.imageViewActivityFormularioProduto.carregarImagem(produto.imagem)
        binding.editTextNomeActivityFormularioProduto.setText(produto.nome)
        binding.editTextDescricaoActivityFormularioProduto.setText(produto.descricao)
        binding.editTextValorActivityFormularioProduto.setText(
            produto.valor.toPlainString()
        )
    }

    private fun capturaDadosDoEditText(): Produto {
        val editTextNome = binding.editTextNomeActivityFormularioProduto.text
        val nome = editTextNome.toString()
        val editTextDescricao = binding.editTextDescricaoActivityFormularioProduto.text
        val descricao = editTextDescricao.toString()
        val editTextValor = binding.editTextValorActivityFormularioProduto.text
        val valorEmTexto = editTextValor.toString()

        val valorEmBigDecimal = converteValor(valorEmTexto)

        val produto = Produto(
            id = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valorEmBigDecimal,
            imagem = url)
        return produto
    }

    private fun converteValor(valorEmTexto: String): BigDecimal {
        return if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }
    }
}