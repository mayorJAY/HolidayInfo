package com.josycom.mayorjay.holidayinfo.view.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.josycom.mayorjay.holidayinfo.R
import com.josycom.mayorjay.holidayinfo.databinding.CountryItemViewBinding
import com.josycom.mayorjay.holidayinfo.model.local.CountryLocal

class CountryAdapter : ListAdapter<CountryLocal, CountryAdapter.CountryViewHolder>(DiffCallBack()) {

    companion object {
        private var clickListener: View.OnClickListener? = null
    }

    private class DiffCallBack : DiffUtil.ItemCallback<CountryLocal>() {
        override fun areItemsTheSame(oldItem: CountryLocal, newItem: CountryLocal) = oldItem.code == newItem.code

        override fun areContentsTheSame(oldItem: CountryLocal, newItem: CountryLocal) = oldItem.name == newItem.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CountryItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    fun setListener(onClickListener: View.OnClickListener) {
        clickListener = onClickListener
    }

    class CountryViewHolder(private val binding: CountryItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener(clickListener)
        }

        fun bind(country: CountryLocal) {
            binding.apply {
                root.tag = country
                tvCode.text = binding.root.context.getString(R.string.code, country.code)
                tvName.text = binding.root.context.getString(R.string.name, country.name)
            }
        }
    }
}