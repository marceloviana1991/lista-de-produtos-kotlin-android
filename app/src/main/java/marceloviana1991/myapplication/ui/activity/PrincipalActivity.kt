package marceloviana1991.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import marceloviana1991.myapplication.model.Produto
import marceloviana1991.myapplication.ui.adapter.ProdutoItemAdapter
import marceloviana1991.myapplication.databinding.ActivityPrincipalBinding
import java.math.BigDecimal

class PrincipalActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPrincipalBinding.inflate(layoutInflater)
    }

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
        recyclerView.adapter = ProdutoItemAdapter(
            this, listOf(
                Produto("produto", "descrição", BigDecimal(1)),
                Produto("produto", "descrição", BigDecimal(1)),
                Produto("produto", "descrição", BigDecimal(1)),
                Produto("produto", "descrição", BigDecimal(1)),
                Produto("produto", "descrição", BigDecimal(1))
            )
        )

        val floatingActionButton = binding.floatingActionButton
        floatingActionButton.setOnClickListener {
            val intent = Intent(this, FormularioProdutoActivity::class.java)
            startActivity(intent)
        }
    }
}