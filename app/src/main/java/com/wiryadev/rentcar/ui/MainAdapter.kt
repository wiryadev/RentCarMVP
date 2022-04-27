package com.wiryadev.rentcar.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wiryadev.rentcar.R
import com.wiryadev.rentcar.databinding.ItemContentBinding
import com.wiryadev.rentcar.model.GetAllCarResponseItem

class MainAdapter(
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<MainAdapter.CarViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<GetAllCarResponseItem>() {
        override fun areItemsTheSame(
            oldItem: GetAllCarResponseItem,
            newItem: GetAllCarResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetAllCarResponseItem,
            newItem: GetAllCarResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemContentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitData(newData: List<GetAllCarResponseItem>) {
        differ.submitList(newData)
    }

    interface OnClickListener {
        fun onItemClicked(data: GetAllCarResponseItem)
    }

    inner class CarViewHolder(
        private val binding: ItemContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: GetAllCarResponseItem) {
            with(binding) {
                ivCar.load(data.image) {
                    error(R.drawable.ic_round_broken_image_24)
                }
                tvTitle.text = data.name
                tvPrice.text = data.price.toString()

                root.setOnClickListener {
                    onClickListener.onItemClicked(data)
                }
            }
        }
    }
}