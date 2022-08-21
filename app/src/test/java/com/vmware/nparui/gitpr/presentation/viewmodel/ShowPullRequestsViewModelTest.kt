package com.vmware.nparui.gitpr.presentation.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import com.vmware.nparui.gitpr.RobolectricTestCase
import com.vmware.nparui.gitpr.data.entities.PullRequestInfo
import com.vmware.nparui.gitpr.domain.PullRequestUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

import org.junit.Assert
import org.junit.Test

class ShowPullRequestsViewModelTest : RobolectricTestCase() {

    private lateinit var showPullRequestsViewModel: ShowPullRequestsViewModel

    private lateinit var pullRequestUseCase: PullRequestUseCase

    override fun start() {
        pullRequestUseCase = mockk()
        showPullRequestsViewModel = ShowPullRequestsViewModel(pullRequestUseCase)
    }

    @Test
    fun testGetAdapter() {
        Assert.assertNotNull(showPullRequestsViewModel.adapter)
    }
    @Test
    fun testOnStart() {
        val ld = MutableLiveData<List<PullRequestInfo>>()
        val owner = mockk<LifecycleOwner>()
        val lifecycle = LifecycleRegistry(owner)
        every { owner.lifecycle } returns lifecycle
        every { pullRequestUseCase.startFetch() } returns ld
        showPullRequestsViewModel.onStart(owner)
        verify { pullRequestUseCase.startFetch() }
    }
    @Test
    fun testNextPage() {
        every { pullRequestUseCase.nextPage() } returns Unit
        showPullRequestsViewModel.nextPage()
        verify { pullRequestUseCase.nextPage() }
    }
    @Test
    fun testPrevPage() {
        every { pullRequestUseCase.prevPage() } returns Unit
        showPullRequestsViewModel.prevPage()
        verify { pullRequestUseCase.prevPage() }
    }
    @Test
    fun testShouldShowNext() {
        every { pullRequestUseCase.showNextPage() } returns true
        Assert.assertTrue(showPullRequestsViewModel.shouldShowNext())
        every { pullRequestUseCase.showNextPage() } returns false
        Assert.assertFalse(showPullRequestsViewModel.shouldShowNext())
    }
    @Test
    fun testShouldShowPrev() {
        every { pullRequestUseCase.showPrevPage() } returns true
        Assert.assertTrue(showPullRequestsViewModel.shouldShowPrev())
        every { pullRequestUseCase.showPrevPage() } returns false
        Assert.assertFalse(showPullRequestsViewModel.shouldShowPrev())
    }
    @Test
    fun testOnStop() {
        val owner = mockk<LifecycleOwner>()
        val lifecycle = LifecycleRegistry(owner)
        every { owner.lifecycle } returns lifecycle
        every { pullRequestUseCase.stop() } returns Unit
        showPullRequestsViewModel.onStop(owner)
        verify { pullRequestUseCase.stop() }
    }

    override fun tearDown() {

    }
}