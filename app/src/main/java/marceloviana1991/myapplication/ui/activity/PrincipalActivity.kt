package marceloviana1991.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import marceloviana1991.myapplication.dao.ProdutosDao
import marceloviana1991.myapplication.ui.adapter.ProdutoItemAdapter
import marceloviana1991.myapplication.databinding.ActivityPrincipalBinding

class PrincipalActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPrincipalBinding.inflate(layoutInflater)
    }

    private val adapter = ProdutoItemAdapter(this, ProdutosDao.buscaTodos())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.activityPrincipal) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = binding.recyclerViewActivityPrincipal
        recyclerView.adapter = adapter

        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhamentoProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO, it)
            }
            startActivity(intent)
        }

        val floatingActionButton = binding.floatingActionButton
        floatingActionButton.setOnClickListener {
            val intent = Intent(this, FormularioProdutoActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(ProdutosDao.buscaTodos())
    }
}