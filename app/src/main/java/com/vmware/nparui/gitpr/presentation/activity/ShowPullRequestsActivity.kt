package com.vmware.nparui.gitpr.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vmware.nparui.gitpr.databinding.ActivityMainBinding
import com.vmware.nparui.gitpr.presentation.viewmodel.ShowPullRequestsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowPullRequestsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val showPullRequestsViewModel: ShowPullRequestsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(showPullRequestsViewModel)
        binding.viewModel = showPullRequestsViewModel
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = showPullRequestsViewModel.adapter
    }
}