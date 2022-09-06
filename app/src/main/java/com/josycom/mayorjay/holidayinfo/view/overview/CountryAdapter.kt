package com.josycom.mayorjay.holidayinfo.view.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.josycom.mayorjay.holidayinfo.R
import com.josycom.mayorjay.holidayinfo.databinding.CountryItemViewBinding
import com.josycom.mayorjay.holidayinfo.data.model.Country
import javax.inject.Inject

class CountryAdapter @Inject constructor() : ListAdapter<Country, CountryAdapter.CountryViewHolder>(DiffCallBack()) {

    companion object {
        private var clickListener: View.OnClickListener? = null
    }

    private class DiffCallBack : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country) = oldItem.code == newItem.code

        override fun areContentsTheSame(oldItem: Country, newItem: Country) = oldItem.name == newItem.name
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

        fun bind(country: Country) {
            binding.apply {
                root.tag = country
                tvCode.text = binding.root.context.getString(R.string.code, country.code)
                tvName.text = binding.root.context.getString(R.string.name, country.name)
            }
        }
    }
}