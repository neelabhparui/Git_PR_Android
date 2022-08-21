package com.vmware.nparui.gitpr.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vmware.nparui.gitpr.R
import com.vmware.nparui.gitpr.databinding.ActivityMainBinding
import com.vmware.nparui.gitpr.presentation.viewmodel.ShowPullRequestsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowPullRequestsActivity : AppCompatActivity(), ShowPullRequestsViewModel.PropertyChangeListener {

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
        showPullRequestsViewModel.register(this)
    }

    override fun notifyPropertyChanged() {
        runOnUiThread {
            binding.next.visibility = if (showPullRequestsViewModel.shouldShowNext()) View.VISIBLE else View.GONE
            binding.prev.visibility = if (showPullRequestsViewModel.shouldShowPrev()) View.VISIBLE else View.GONE
        }
    }

    override fun handleError(message: String?) {
        runOnUiThread {
            Toast.makeText(this, String.format(getString(R.string.error), message), Toast.LENGTH_LONG).show()
        }
    }
}