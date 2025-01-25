package marceloviana1991.myapplication.extensions

import android.widget.ImageView
import coil3.load
import coil3.request.fallback
import coil3.request.error
import coil3.request.placeholder
import marceloviana1991.myapplication.R


fun ImageView.carregarImagem(url: String? = null) {
    load(url) {
        fallback(R.drawable.imagem_padrao)
        error(R.drawable.erro)
        placeholder(R.drawable.placeholder)
    }
}