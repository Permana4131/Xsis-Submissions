package com.permana.xsisassessment.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.permana.xsisassessment.core.data.api.model.ResultsItem
import com.permana.xsisassessment.databinding.CarouselItemBinding

class CarouselAdapter(
    private val onItemClick: (ResultsItem) -> Unit,
    private val items: List<ResultsItem>
) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    val imageUrl = "https://image.tmdb.org/t/p/w500"

    inner class CarouselViewHolder(private val binding: CarouselItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ResultsItem) {
            with(binding) {
                Glide.with(imageView.context).load(imageUrl + item.posterPath).into(imageView)

                titleTextView.text = item.title
                descTextView.text = item.overview
                itemView.setOnClickListener {
                    onItemClick.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding =
            CarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

