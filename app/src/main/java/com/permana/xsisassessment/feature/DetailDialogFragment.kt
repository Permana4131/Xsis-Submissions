package com.permana.xsisassessment.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.permana.xsisassessment.core.data.XsisResponse
import com.permana.xsisassessment.core.data.api.model.ResultsItem
import com.permana.xsisassessment.core.utils.extenstion.collectFlow
import com.permana.xsisassessment.core.utils.extenstion.showToast
import com.permana.xsisassessment.databinding.DialogDetailBinding
import com.permana.xsisassessment.feature.adapter.MovieAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailDialogFragment : DialogFragment() {

    private lateinit var binding: DialogDetailBinding

    private lateinit var item: ResultsItem
    val movieList: ArrayList<ResultsItem> = arrayListOf()

    private val viewModel: MainViewModel by viewModel()

    val youtubeUrl = "https://www.youtube.com/watch?v="

    companion object {
        fun newInstance(item: ResultsItem, movieList: List<ResultsItem>) = DetailDialogFragment().apply {
            this.item = item
            this.movieList.clear()
            this.movieList.addAll(movieList)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Dialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            // Set the width and height to 90% of the screen dimensions
            setLayout(
                (resources.displayMetrics.widthPixels * 0.9).toInt(),
                (resources.displayMetrics.heightPixels * 0.9).toInt()
            )
            setBackgroundDrawableResource(android.R.color.transparent)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        binding = DialogDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            if (item.id != 0L) {
                viewModel.fetchVideoCode(item.id)
                tvTitle.text = item.title
                tvDescription.text = item.overview
            }
            ivClose.setOnClickListener {
                dismiss()
            }

            binding.rvLatestMovies.adapter = MovieAdapter(
                onItemClick = {
                    dismiss()
                    val detailDialog = newInstance(it, movieList)
                    detailDialog.show(parentFragmentManager, "DetailDialog")
                }
            ).apply {
                submitList(movieList)
            }
        }

        setFlowCollector()
    }

    private fun setFlowCollector() {
        collectFlow(viewModel.videoCode) { response ->
            when (response) {
                is XsisResponse.Error -> {
                    requireContext().showToast(response.message)
                }

                is XsisResponse.Success -> {
                    response.data.results.first().key?.let {
                        setupYouTubePlayer(binding.youtubePlayerView, youtubeUrl+it)
                    }
                }

                is XsisResponse.Empty -> {
                    binding.root.context.showToast("No data Found")
                }
                else -> Unit
            }
        }
    }

    private fun setupYouTubePlayer(youTubePlayerView: YouTubePlayerView, videoUrl: String) {
        lifecycle.addObserver(youTubePlayerView)

        val videoId = videoUrl.split("v=")[1] // Extract video ID from the YouTube URL
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })
    }
}
