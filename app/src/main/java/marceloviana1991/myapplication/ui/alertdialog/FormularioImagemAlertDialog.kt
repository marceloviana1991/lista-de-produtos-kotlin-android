package marceloviana1991.myapplication.ui.alertdialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import marceloviana1991.myapplication.databinding.AlertDialogFormularioImagemBinding
import marceloviana1991.myapplication.extensions.carregarImagem

class FormularioImagemAlertDialog(private val context: Context) {

    private var url: String? = null

    fun carregaImagem(confirmaImagem: (imagem: String?) -> Unit) {
        val binding = AlertDialogFormularioImagemBinding.inflate(
            LayoutInflater.from(context)
        )
        binding.buttonFormularioImagem.setOnClickListener {
            url = binding.editTextFormularioImagem.text.toString()
            binding.imageViewFormularioImagem.carregarImagem(url)
        }

        AlertDialog.Builder(context)
            .setView(binding.root)
            .setPositiveButton("Confirmar") { _, _ ->
                confirmaImagem(url)
            }
            .setNegativeButton("Cancelar") { _, _ ->
            }
            .show()
    }
}