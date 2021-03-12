package com.rocbillow.doraemon.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.rocbillow.core.extension.setOnThrottleClickListener
import com.rocbillow.doraemon.R

/**
 * @author rocbillow
 * @date 2020-09-03
 */
class ModuleItemViewBinder(private val onClick: (item: Module) -> Unit) :
  ItemViewBinder<Module, ModuleItemViewBinder.ViewHolder>() {

  override fun onCreateViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
  ): ViewHolder {
    val itemView = inflater.inflate(R.layout.item_module, parent, false)
    return ViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: ViewHolder, item: Module) {
    holder.tvName.text = item.name
    holder.item = item
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvName: TextView = itemView.findViewById(R.id.tvName)
    lateinit var item: Module

    init {
      tvName.setOnThrottleClickListener {
        onClick(item)
      }
    }
  }
}
