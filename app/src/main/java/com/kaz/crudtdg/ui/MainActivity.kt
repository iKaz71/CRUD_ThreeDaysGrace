package com.kaz.crudtdg.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaz.crudtdg.databinding.ActivityMainBinding
import com.kaz.crudtdg.model.AlbumEntity
import com.kaz.crudtdg.ui.adapter.AlbumAdapter
import com.kaz.crudtdg.viewmodel.AlbumViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val albumViewModel: AlbumViewModel by viewModels()
    private lateinit var albumAdapter: AlbumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        albumAdapter = AlbumAdapter(
            onEdit = { album ->
                AddEditAlbumBottomSheet.newInstance(album)
                    .show(supportFragmentManager, "EditAlbum")
            },
            onDelete = { albumViewModel.delete(it) },
            onToggleFavorite = { albumViewModel.update(it) }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = albumAdapter
        }

        binding.fabAdd.setOnClickListener {
            AddEditAlbumBottomSheet.newInstance()
                .show(supportFragmentManager, "AddAlbum")
        }

        albumViewModel.allAlbums.observe(this) {
            binding.textEmpty.visibility =
                if (it.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
            albumAdapter.submitList(it)
        }
    }
}
