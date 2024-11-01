package com.permana.xsisassessment.feature

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.permana.xsisassessment.core.data.XsisResponse
import com.permana.xsisassessment.core.data.api.model.ResultsItem
import com.permana.xsisassessment.core.utils.extenstion.collectFlow
import com.permana.xsisassessment.core.utils.extenstion.showToast
import com.permana.xsisassessment.databinding.DialogSearchBinding
import com.permana.xsisassessment.feature.adapter.MovieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchDialogFragment : DialogFragment() {

    private lateinit var binding: DialogSearchBinding
    private lateinit var movieAdapter: MovieAdapter
    val movieList: ArrayList<ResultsItem> = arrayListOf()

    private val viewModel: MainViewModel by viewModel()

    companion object {
        fun newInstance( movieList: List<ResultsItem>) = SearchDialogFragment().apply {
            this.movieList.clear()
            this.movieList.addAll(movieList)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        binding = DialogSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter(
            onItemClick = {
                val detailDialog = DetailDialogFragment.newInstance(it, movieList)
                detailDialog.show(parentFragmentManager, "DetailDialog")
            }
        )
        binding.rvSearchResults.adapter = movieAdapter

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            private val searchHandler = Handler(Looper.getMainLooper())
            private val searchRunnable = Runnable {
                viewModel.searchMovie(binding.etSearch.text.toString().trim())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {
                searchHandler.removeCallbacks(searchRunnable)

                searchHandler.postDelayed(searchRunnable, 2000)
            }
        })



        binding.icClose.setOnClickListener {
            dismiss()
        }
        setFlowCollector()
    }

    private fun setFlowCollector() {
        collectFlow(viewModel.searchMovie) { response ->
            when (response) {
                is XsisResponse.Error -> {
                    requireContext().showToast(response.message)
                }

                is XsisResponse.Success -> {
                    movieAdapter.submitList(null)
                    movieAdapter.submitList(response.data.results)
                }

                is XsisResponse.Empty -> {
                    binding.root.context.showToast("No data Found")
                }
                else -> Unit
            }
        }
    }
}
