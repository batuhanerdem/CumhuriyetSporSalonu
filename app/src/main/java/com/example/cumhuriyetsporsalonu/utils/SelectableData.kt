package com.example.cumhuriyetsporsalonu.utils

data class SelectableData<T>(val data: T, var isSelected: Boolean) {
    fun getReversed(): SelectableData<T> {
        return SelectableData(this.data, !this.isSelected)
    }

    companion object {
        fun <T> MutableList<T>.toSelectable(): MutableList<SelectableData<T>> {
            val newList = mutableListOf<SelectableData<T>>()
            this.map {
                newList.add(SelectableData(it, false))
            }
            return newList
        }

        fun <T> T.toSelectable(): SelectableData<T> {
            return SelectableData(this, false)
        }
    }
}