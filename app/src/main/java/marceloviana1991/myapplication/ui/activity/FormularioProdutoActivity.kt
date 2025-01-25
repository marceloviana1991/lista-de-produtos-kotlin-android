package marceloviana1991.myapplication.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import marceloviana1991.myapplication.dao.ProdutosDao
import marceloviana1991.myapplication.databinding.ActivityFormularioProdutoBinding
import marceloviana1991.myapplication.databinding.AlertDialogFormularioImagemBinding
import marceloviana1991.myapplication.extensions.carregarImagem
import marceloviana1991.myapplication.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.activityFormularioProduto) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.imageViewActivityFormularioProduto.setOnClickListener {
            val bindingFormularioImagem = AlertDialogFormularioImagemBinding.inflate(layoutInflater)
            bindingFormularioImagem.buttonFormularioImagem.setOnClickListener {
                url = bindingFormularioImagem.editTextFormularioImagem.text.toString()
                bindingFormularioImagem.imageViewFormularioImagem.carregarImagem(url)
            }

            AlertDialog.Builder(this)
                .setView(bindingFormularioImagem.root)
                .setPositiveButton("Confirmar") { _, _ ->
                    binding.imageViewActivityFormularioProduto.carregarImagem(url)
                }
                .setNegativeButton("Cancelar") { _, _ ->
                }
                .show()
        }

        val buttonSalvar = binding.buttonSalvarActivityFormularioProduto
        buttonSalvar.setOnClickListener {
            val produto = capturaDadosDoEditText()
            ProdutosDao.adiciona(produto)

            finish()
        }
    }

    private fun capturaDadosDoEditText(): Produto {
        val editTextNome = binding.editTextNomeActivityFormularioProduto.text
        val nome = editTextNome.toString()
        val editTextDescricao = binding.editTextDescricaoActivityFormularioProduto.text
        val descricao = editTextDescricao.toString()
        val editTextValor = binding.editTextValorActivityFormularioProduto.text
        val valorEmTexto = editTextValor.toString()

        val valorEmBigDecimal = converteValor(valorEmTexto)

        val produto = Produto(nome, descricao, valorEmBigDecimal, url)
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