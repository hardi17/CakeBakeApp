package com.hardi.cakelist.ui.cakelist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hardi.cakelist.data.model.Cake
import com.hardi.cakelist.databinding.ActivityCakeListBinding
import com.hardi.cakelist.utils.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CakeListActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: CakeListAdapter

    private lateinit var viewModel: CakeViewModel

    private lateinit var binding: ActivityCakeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCakeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        setUpUI()
        setupResponse()
    }

    private fun setupResponse() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UIState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.erroLayout.visibility = View.GONE
                            renderList(it.data)
                            binding.cakeRcv.visibility = View.VISIBLE
                        }

                        is UIState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.erroLayout.visibility = View.GONE
                            binding.cakeRcv.visibility = View.GONE
                        }

                        is UIState.Error -> {
                            //Handle Error
                            binding.cakeRcv.visibility = View.GONE
                            binding.progressBar.visibility = View.GONE
                            binding.erroLayout.visibility = View.VISIBLE
                            binding.tvError.text = it.message
                            binding.btnRetry.setOnClickListener {
                                viewModel.fetchCakeList()
                            }
                        }
                    }
                }
            }
        }

    }

    private fun renderList(cakeItem: List<Cake>) {
        adapter.addData(cakeItem)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[CakeViewModel::class.java]
    }

    //Set recyclerview and add divider between item.
    private fun setUpUI() {
        val recyclerView = binding.cakeRcv
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }
}