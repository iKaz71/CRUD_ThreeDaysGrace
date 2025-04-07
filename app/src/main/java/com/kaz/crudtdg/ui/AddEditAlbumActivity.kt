package com.kaz.crudtdg.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.viewModels
import com.kaz.crudtdg.R
import com.kaz.crudtdg.databinding.ActivityAddEditAlbumBinding
import com.kaz.crudtdg.model.AlbumEntity
import com.kaz.crudtdg.viewmodel.AlbumViewModel
import java.util.Calendar

class AddEditAlbumBottomSheet : BottomSheetDialogFragment() {

    private var _binding: ActivityAddEditAlbumBinding? = null
    private val binding get() = _binding!!
    private val albumViewModel: AlbumViewModel by viewModels()
    private var editingAlbum: AlbumEntity? = null

    private val titulos by lazy { resources.getStringArray(R.array.titulos_album).toList() }
    private val tipos by lazy { resources.getStringArray(R.array.tipos_album).toList() }


    private val anios by lazy {
        val anioActual = Calendar.getInstance().get(Calendar.YEAR)
        (1990..anioActual).map { it.toString() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityAddEditAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tituloAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, titulos)
        binding.spinnerTitulo.setAdapter(tituloAdapter)

        val tipoAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, tipos)
        binding.spinnerTipo.setAdapter(tipoAdapter)

        val anioAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, anios)
        binding.spinnerAnio.setAdapter(anioAdapter)

        binding.spinnerTitulo.setOnClickListener { binding.spinnerTitulo.showDropDown() }
        binding.spinnerTipo.setOnClickListener { binding.spinnerTipo.showDropDown() }
        binding.spinnerAnio.setOnClickListener { binding.spinnerAnio.showDropDown() }

        editingAlbum = arguments?.getSerializable("album") as? AlbumEntity
        editingAlbum?.let {
            binding.spinnerTitulo.setText(it.titulo, false)
            binding.spinnerAnio.setText(it.anio.toString(), false)
            binding.spinnerTipo.setText(it.tipo, false)
        }

        binding.btnGuardar.setOnClickListener {
            val titulo = binding.spinnerTitulo.text.toString()
            val anioText = binding.spinnerAnio.text.toString()
            val tipo = binding.spinnerTipo.text.toString()

            if (titulo.isBlank() || tipo.isBlank() || anioText.isBlank()) {
                Snackbar.make(binding.root, getString(R.string.campo_requerido), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val anio = anioText.toIntOrNull()
            val anioActual = Calendar.getInstance().get(Calendar.YEAR)
            if (anio == null || anio < 1990 || anio > anioActual) {
                Snackbar.make(binding.root, getString(R.string.anio_invalido), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val album = AlbumEntity(
                id = editingAlbum?.id ?: 0,
                titulo = titulo,
                anio = anio,
                tipo = tipo,
                favorito = editingAlbum?.favorito ?: false
            )

            if (editingAlbum == null) {
                albumViewModel.insert(album)
            } else {
                albumViewModel.update(album)
            }
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(album: AlbumEntity? = null): AddEditAlbumBottomSheet {
            val fragment = AddEditAlbumBottomSheet()
            val args = Bundle()
            args.putSerializable("album", album)
            fragment.arguments = args
            return fragment
        }
    }
}
