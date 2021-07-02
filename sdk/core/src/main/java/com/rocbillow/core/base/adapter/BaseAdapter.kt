package com.rocbillow.core.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<T, VH : BaseViewHolder<T>>(callback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, VH>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        onCreateViewHolder(LayoutInflater.from(parent.context), parent)

    abstract fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position), position)
    }
}