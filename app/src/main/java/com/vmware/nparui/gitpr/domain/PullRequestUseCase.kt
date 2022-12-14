package com.vmware.nparui.gitpr.domain

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import com.vmware.nparui.gitpr.data.di.DispatcherIO
import com.vmware.nparui.gitpr.data.entities.PullRequestInfo
import com.vmware.nparui.gitpr.data.repository.PAGE_CAP
import com.vmware.nparui.gitpr.data.repository.PRRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val POLL_INTERVAL = 60000L //1 min
class PullRequestUseCase @Inject constructor(
    private val prRepository: PRRepository,
    @DispatcherIO private val dispatcher : CoroutineDispatcher){

    private var page = 1
    private lateinit var job: Job

    fun startFetch() : LiveData<PRData> {
        job = CoroutineScope(dispatcher).launch {
            while (isActive){
                prRepository.poll(page)
                delay(POLL_INTERVAL)
            }
        }
        return prRepository.prList
    }

    fun nextPage() {
        page++
        if (this::job.isInitialized)
            job.cancel()
        startFetch()
    }

    fun prevPage() {
        if (page>0) {
            page--
            if (this::job.isInitialized)
                job.cancel()
            startFetch()
        }
    }

    fun showPrevPage() = page > 1

    fun showNextPage() : Boolean {
        prRepository.prList.value?.data?.let {
            if (it.size == PAGE_CAP) return true
        }
        return false
    }

    fun stop() {
        prRepository.close()
        dispatcher.cancel()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setPage(p : Int) {
        page = p
    }
}