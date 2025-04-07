package com.kaz.crudtdg.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kaz.crudtdg.R
import com.kaz.crudtdg.databinding.ItemAlbumBinding
import com.kaz.crudtdg.model.AlbumEntity

class AlbumAdapter(
    private val onEdit: (AlbumEntity) -> Unit,
    private val onDelete: (AlbumEntity) -> Unit,
    private val onToggleFavorite: (AlbumEntity) -> Unit
) : ListAdapter<AlbumEntity, AlbumAdapter.AlbumViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AlbumViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: AlbumEntity) {
            binding.textTitulo.text = album.titulo
            binding.textAnio.text = binding.root.context.getString(R.string.anio_con_valor, album.anio)

            binding.textTipo.text = album.tipo

            val imgRes = when (album.titulo.lowercase()) {
                "one-x" -> R.drawable.one_x
                "life starts now" -> R.drawable.life_starts_now
                "transit of venus" -> R.drawable.transit_of_venus
                "human" -> R.drawable.human
                "outsider" -> R.drawable.outsider
                "explosions" -> R.drawable.explosions
                else -> R.drawable.ic_default_album
            }
            binding.imageTipo.setImageResource(imgRes)

            val iconRes = when (album.tipo) {
                "Álbum de estudio" -> R.drawable.ic_album
                "Álbum en vivo" -> R.drawable.ic_live
                "EP" -> R.drawable.ic_ep
                "Compilatorio" -> R.drawable.ic_compilation
                else -> R.drawable.ic_help
            }
            binding.iconTipo.setImageResource(iconRes)

            val favRes = if (album.favorito) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            binding.btnFavorito.setImageResource(favRes)

            binding.btnFavorito.setOnClickListener {
                onToggleFavorite(album.copy(favorito = !album.favorito))
            }

            binding.btnEditar.setOnClickListener { onEdit(album) }

            binding.btnEliminar.setOnClickListener {
                AlertDialog.Builder(binding.root.context)
                    .setTitle(binding.root.context.getString(R.string.eliminar))
                    .setMessage(binding.root.context.getString(R.string.seguro_eliminar, album.titulo))
                    .setPositiveButton(binding.root.context.getString(R.string.si)) { _, _ ->
                        onDelete(album)
                    }
                    .setNegativeButton(binding.root.context.getString(R.string.cancelar), null)
                    .show()

            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<AlbumEntity>() {
        override fun areItemsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: AlbumEntity, newItem: AlbumEntity) = oldItem == newItem
    }
}
