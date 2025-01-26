package marceloviana1991.myapplication.ui.alertdialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import marceloviana1991.myapplication.databinding.AlertDialogFormularioImagemBinding
import marceloviana1991.myapplication.extensions.carregarImagem

class FormularioImagemAlertDialog(private val context: Context) {

    private var url: String? = null

    fun carregaImagem(
        urlCadastrada: String?,
        confirmaImagem: (imagem: String?) -> Unit
    ) {
        AlertDialogFormularioImagemBinding.inflate(
            LayoutInflater.from(context)
        ).apply {

            urlCadastrada?.let {
                imageViewFormularioImagem.carregarImagem(it)
                editTextFormularioImagem.setText(it)
            }

            buttonFormularioImagem.setOnClickListener {
                url = editTextFormularioImagem.text.toString()
                imageViewFormularioImagem.carregarImagem(url)
            }

            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Confirmar") { _, _ ->
                    confirmaImagem(url)
                }
                .setNegativeButton("Cancelar") { _, _ ->
                }
                .show()
        }
    }
}