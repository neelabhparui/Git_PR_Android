package com.vmware.nparui.gitpr.domain

import androidx.lifecycle.MutableLiveData
import com.vmware.nparui.gitpr.RobolectricTestCase
import com.vmware.nparui.gitpr.data.entities.BaseInfo
import com.vmware.nparui.gitpr.data.entities.HeadInfo
import com.vmware.nparui.gitpr.data.entities.PullRequestInfo
import com.vmware.nparui.gitpr.data.entities.UserInfo
import com.vmware.nparui.gitpr.data.repository.PRRepository
import io.mockk.every
import io.mockk.mockk

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class PullRequestUseCaseTest : RobolectricTestCase() {

    private lateinit var prRepository: PRRepository

    private lateinit var pullRequestUseCase: PullRequestUseCase

    override fun start() {
        prRepository = mockk()
        pullRequestUseCase = PullRequestUseCase(prRepository, Dispatchers.Default)
    }

    @Test
    fun testStartFetch() {
        val list = MutableLiveData<List<PullRequestInfo>>()
        var polls = 0
        every { prRepository.prList } returns list
        every { prRepository.poll(any()) } answers { polls++ }
        Assert.assertEquals(list, pullRequestUseCase.startFetch())
        runBlocking { delay(100) }
        Assert.assertEquals(1, polls)
    }

    @Test
    fun testNextPage() {
        val list = MutableLiveData<List<PullRequestInfo>>()
        every { prRepository.prList } returns list
        var polls = 0
        every { prRepository.poll(2) } answers { polls++ }
        pullRequestUseCase.nextPage()
        runBlocking { delay(100) }
        Assert.assertEquals(1, polls)
    }

    @Test
    fun testPrevPage_not_first() {
        val list = MutableLiveData<List<PullRequestInfo>>()
        every { prRepository.prList } returns list
        var polls = 0
        every { prRepository.poll(1) } answers { polls++ }
        pullRequestUseCase.setPage(2)
        pullRequestUseCase.prevPage()
        runBlocking { delay(100) }
        Assert.assertEquals(1, polls)
    }

    @Test
    fun testPrevPage_first() {
        val list = MutableLiveData<List<PullRequestInfo>>()
        every { prRepository.prList } returns list
        var polls = 0
        every { prRepository.poll(1) } answers { polls++ }
        pullRequestUseCase.setPage(1)
        pullRequestUseCase.prevPage()
        runBlocking { delay(100) }
        Assert.assertEquals(0, polls)
    }

    @Test
    fun testShowNext() {
        val listLD = MutableLiveData<List<PullRequestInfo>>()
        val prInfo = PullRequestInfo(0,"", "",  UserInfo("sa", "", null), "", "", HeadInfo(""), BaseInfo(""))
        val list = arrayListOf(prInfo, prInfo, prInfo, prInfo, prInfo)
        listLD.value = list
        every { prRepository.prList } returns listLD
        Assert.assertTrue(pullRequestUseCase.showNextPage())
        listLD.value = null
        Assert.assertFalse(pullRequestUseCase.showNextPage())
    }

    @Test
    fun testShowPrev() {
        pullRequestUseCase.setPage(2)
        Assert.assertTrue(pullRequestUseCase.showPrevPage())
        pullRequestUseCase.setPage(1)
        Assert.assertFalse(pullRequestUseCase.showPrevPage())
    }

    override fun tearDown() {

    }
}