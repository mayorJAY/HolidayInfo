package com.josycom.mayorjay.holidayinfo.view.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.josycom.mayorjay.holidayinfo.R
import com.josycom.mayorjay.holidayinfo.databinding.HolidayItemViewBinding
import com.josycom.mayorjay.holidayinfo.model.local.HolidayLocal
import com.josycom.mayorjay.holidayinfo.model.util.getFormattedDate
import com.josycom.mayorjay.holidayinfo.model.util.getJoinedString
import java.text.SimpleDateFormat
import java.util.Locale

class HolidayAdapter : ListAdapter<HolidayLocal, HolidayAdapter.HolidayViewHolder>(DiffCallBack()) {

    private class DiffCallBack : DiffUtil.ItemCallback<HolidayLocal>() {
        override fun areItemsTheSame(oldItem: HolidayLocal, newItem: HolidayLocal) = oldItem.countryCode == newItem.countryCode

        override fun areContentsTheSame(oldItem: HolidayLocal, newItem: HolidayLocal) = oldItem.name == newItem.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolidayViewHolder {
        val binding = HolidayItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HolidayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HolidayViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class HolidayViewHolder(private val binding: HolidayItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(holiday: HolidayLocal) {
            binding.apply {
                tvName.text = binding.root.context.getString(R.string.name, holiday.name)
                val date = holiday.date.getFormattedDate(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()), SimpleDateFormat("MMM dd yyyy", Locale.getDefault()))
                tvDate.text = binding.root.context.getString(R.string.date, date)
                val types = holiday.types.getJoinedString()
                if (types.isNotBlank()) {
                    tvType.text = binding.root.context.getString(R.string.type, binding.root.context.getString(R.string.holiday, types))
                } else {
                    tvType.visibility = View.GONE
                }
                val regions = holiday.regions.getJoinedString()
                if (regions.isNotBlank()) {
                    tvRegion.text = binding.root.context.getString(R.string.region, regions)
                } else {
                    tvRegion.visibility = View.GONE
                }
            }
        }
    }
}