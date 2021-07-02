package com.rocbillow.component.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.rocbillow.component.R
import com.rocbillow.component.databinding.ItemSampleBinding
import com.rocbillow.core.base.adapter.BaseAdapter
import com.rocbillow.core.base.adapter.BaseViewHolder
import com.rocbillow.core.binding.dataBinding
import com.rocbillow.core.extension.setOnThrottleClickListener

class SampleAdapter(private val onClick: ((Sample) -> Unit)? = null) :
    BaseAdapter<Sample, SampleViewHolder>(Sample.CALLBACK) {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): SampleViewHolder {
        val itemView = inflater.inflate(R.layout.item_sample, parent, false)
        return SampleViewHolder(itemView, onClick)
    }
}

class SampleViewHolder(itemView: View, onClick: ((Sample) -> Unit)? = null) :
    BaseViewHolder<Sample>(itemView) {

    private val dataBinding by dataBinding<ItemSampleBinding>()
    private lateinit var item: Sample

    init {
        dataBinding.tvSample.setOnThrottleClickListener {
            onClick?.invoke(item)
        }
    }

    override fun bind(item: Sample, position: Int) {
        this.item = item
        dataBinding.sample = item
    }
}

data class Sample(val name: String) {
    companion object {
        val CALLBACK: DiffUtil.ItemCallback<Sample> = object : DiffUtil.ItemCallback<Sample>() {

            override fun areItemsTheSame(oldItem: Sample, newItem: Sample): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Sample, newItem: Sample): Boolean =
                oldItem.name == newItem.name
        }
    }
}
