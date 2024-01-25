package com.example.libroslivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.example.libroslivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: VinculadoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setContentView(binding.root)
        binding.listado.layoutManager = GridLayoutManager(this, 2)
        binding.listado.adapter = viewModel.libros.value?.let {
            LibroRecyclerViewAdapter(it)
        }

        binding.delta = viewModel

        if (binding.listado.adapter is LibroRecyclerViewAdapter) {
            (binding.listado.adapter as LibroRecyclerViewAdapter).click = { position, persona ->
                run {
                    this.viewModel.settSelected(persona)
                }
            }
            viewModel.libros.observe(this, Observer { listado ->
                binding.listado.adapter?.notifyDataSetChanged()
            })

        }
        binding.actualizar.setOnClickListener() {
            this.viewModel.update()
        }
        binding.floatingActionButton.setOnClickListener{
            this.viewModel.create_new()
        }
    }

}