package com.permana.xsisassessment.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.permana.xsisassessment.core.data.api.model.ResultsItem
import com.permana.xsisassessment.databinding.ItemMovieBinding

class MovieAdapter(private val onItemClick: (ResultsItem) -> Unit) :
 ListAdapter<ResultsItem, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageUrl = "https://image.tmdb.org/t/p/w500"

        fun bind(movie: ResultsItem) {
            binding.tvTitle.text = movie.title
            Glide.with(binding.root.context).load(imageUrl+movie.posterPath).into(binding.ivPoster)
            itemView.setOnClickListener { onItemClick(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<ResultsItem>() {
        override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
            return oldItem == newItem
        }
    }
}
