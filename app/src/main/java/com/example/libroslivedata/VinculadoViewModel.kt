package com.example.libroslivedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class VinculadoViewModel : ViewModel() {
    private var new_item:Boolean=false
    private var _libros: MutableLiveData<MutableList<Libro>> = MutableLiveData(mutableListOf())
    val libros: LiveData<MutableList<Libro>>
        get() = _libros

    private var _selected = MutableLiveData<Libro>(Libro("", "", "", false))
    var selected = MutableLiveData<Libro>(Libro("", "", "", false))

    val actionbutton: LiveData<String> = selected.map { libro ->
        when (libro.nombre) {
            "" -> "Alta"
            else -> "Modificar "
        }
    }

    init {


        this._libros.value?.add(Libro("El Camino de los Reyes", "Brandon Sanderson", "304895734", true))
        this._libros.value?.add(Libro("Juramentada", "Brandon Sanderson", "467356675", true))
        this._libros.value?.add(Libro("Dragon Ball, n45", "Akira Toriyama", "346243267", true))
    }


    private fun updatelist() {
        var values = this._libros.value
        this._libros.value = values
    }

    private fun updateItem() {
        this._selected.value = this._selected.value?.copy()
    }

    fun settSelected(item: Libro) {
        this._selected.value = item
        this.selected.value = item.copy()
        this.new_item=false
    }

    fun settSelected(index: Int) {
        this._libros.value?.let {
            this._selected.value = it.get(index)
            this.selected.value = it.get(index).copy()
            this.new_item=false
        }

    }



    fun create_new() {
        this._selected.value = Libro("", "", "", false)
        this.selected.value = this._selected.value
        this.new_item=true
    }

    fun update() {
        if(new_item){
            this.new_item=false
            this.selected.value?.let {
                this._libros.value?.add(it)
                this.updatelist()
            }

        }else {
            this._selected.value?.let {
                it.nombre = selected.value?.let { it.nombre } ?: it.nombre
                it.autor = selected.value?.let { it.autor } ?: it.autor
                it.ISBN = selected.value?.let { it.ISBN } ?: it.ISBN
                it.disponible = selected.value?.let { it.disponible } ?: it.disponible
                this.updatelist()
                this.updateItem()
            }
        }


    }

    fun remove(p: Libro) {
        if(!this.new_item)
            this._libros.value = this._libros.value?.toMutableList().apply { remove(p) }
    }
}