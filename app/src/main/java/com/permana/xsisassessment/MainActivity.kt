package com.permana.xsisassessment

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.viewpager2.widget.ViewPager2
import com.permana.xsisassessment.core.component.BaseActivity
import com.permana.xsisassessment.core.data.XsisResponse
import com.permana.xsisassessment.core.data.api.model.ResultsItem
import com.permana.xsisassessment.core.utils.extenstion.collectFlow
import com.permana.xsisassessment.core.utils.extenstion.showToast
import com.permana.xsisassessment.databinding.ActivityMainBinding
import com.permana.xsisassessment.feature.DetailDialogFragment
import com.permana.xsisassessment.feature.MainViewModel
import com.permana.xsisassessment.feature.SearchDialogFragment
import com.permana.xsisassessment.feature.adapter.CarouselAdapter
import com.permana.xsisassessment.feature.adapter.MovieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()

    private lateinit var dotIndicator: LinearLayoutCompat
    private lateinit var carouselAdapter: CarouselAdapter
    private val listLatest: ArrayList<ResultsItem> = arrayListOf()

    override fun setLayout(): View = binding.root

    override fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        dotIndicator = binding.dotIndicator

        binding.icSearch.setOnClickListener {
            showSearchDialog()
        }
        viewModel.fetchMovie()
        viewModel.fetchMovieLatest()
        viewModel.fetchMoviePopular()
        setFlowCollector()
    }

    private fun setFlowCollector() {
        collectFlow(viewModel.movie) { response ->
            when (response) {
                is XsisResponse.Error -> {
                    showToast(response.message)
                }

                is XsisResponse.Success -> {
                    carouselAdapter = CarouselAdapter(
                        onItemClick = {
                            val detailDialog = DetailDialogFragment.newInstance(it, listLatest)
                            detailDialog.show(supportFragmentManager, "DetailDialog")
                        },
                        response.data.results
                    )
                    binding.vpVideoHome.adapter = carouselAdapter
                    setupIndicator(carouselAdapter.itemCount)

                    binding.vpVideoHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                            updateIndicator(position)
                        }
                    })
                }
                else -> Unit
            }
        }

        collectFlow(viewModel.movieLatest) { response ->
            when (response) {
                is XsisResponse.Error -> {
                    showToast(response.message)
                }

                is XsisResponse.Success -> {
                    listLatest.clear()
                    listLatest.addAll(response.data.results)
                    binding.rvLatestMovies.adapter = MovieAdapter(
                        onItemClick = {
                            val detailDialog = DetailDialogFragment.newInstance(it, listLatest)
                            detailDialog.show(supportFragmentManager, "DetailDialog")
                        }
                    ).apply {
                        submitList(response.data.results)
                    }
                }
                else -> Unit
            }
        }

        collectFlow(viewModel.moviePopular) { response ->
            when (response) {
                is XsisResponse.Error -> {
                    showToast(response.message)
                }

                is XsisResponse.Success -> {
                    binding.rvPopularMovies.adapter = MovieAdapter(
                        onItemClick = {
                            val detailDialog = DetailDialogFragment.newInstance(it, listLatest)
                            detailDialog.show(supportFragmentManager, "DetailDialog")
                        }
                    ).apply {
                        submitList(response.data.results)
                    }
                }
                else -> Unit
            }
        }
    }

    private fun setupIndicator(itemCount: Int) {
        dotIndicator.removeAllViews() // Clear existing dots

        for (i in 0 until itemCount) {
            val dot = ImageView(this).apply {
                setImageResource(R.drawable.dot_inactive) // Replace with your inactive dot drawable
                layoutParams = LinearLayoutCompat.LayoutParams(
                    resources.getDimensionPixelSize(R.dimen.carousel_dot),
                    resources.getDimensionPixelSize(R.dimen.carousel_dot)
                ).apply {
                    setMargins(8, 0, 8, 0) // Set margins between dots
                }
            }
            dotIndicator.addView(dot)
        }

        updateIndicator(0)
    }

    private fun updateIndicator(position: Int) {
        for (i in 0 until dotIndicator.childCount) {
            val dot = dotIndicator.getChildAt(i) as ImageView
            dot.setImageResource(if (i == position) R.drawable.dot_active else R.drawable.dot_inactive)
        }
    }

    private fun showSearchDialog() {
        SearchDialogFragment.newInstance(listLatest).show(supportFragmentManager, "SearchDialog")
    }
}