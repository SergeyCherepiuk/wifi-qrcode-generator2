package com.example.wifiqrcodesgenerator

import com.example.wifiqrcodesgenerator.utils.getEndIndex
import com.example.wifiqrcodesgenerator.utils.getStartIndex
import org.junit.Test

class DotsIndicatorIndexesTests {
    @Test
    fun `start and end indexes for indicator with 0 items`() {
        val startIndex = getStartIndex(currentPageIndex = 0, itemsCount = 0)
        val endIndex = getEndIndex(currentPageIndex = 0, itemsCount = 0)
        assert(startIndex == 0 && endIndex == 0)
    }

    @Test
    fun `start and end indexes for indicator with 1 item`() {
        val startIndex = getStartIndex(currentPageIndex = 0, itemsCount = 1)
        val endIndex = getEndIndex(currentPageIndex = 0, itemsCount = 1)
        assert(startIndex == 0 && endIndex == 1)
    }

    @Test
    fun `start and end indexes for indicator with 5 items and first selected`() {
        val startIndex = getStartIndex(currentPageIndex = 0, itemsCount = 5)
        val endIndex = getEndIndex(currentPageIndex = 0, itemsCount = 5)
        assert(startIndex == 0 && endIndex == 5)
    }

    @Test
    fun `start and end indexes for indicator with 5 items and 3rd selected`() {
        val startIndex = getStartIndex(currentPageIndex = 2, itemsCount = 5)
        val endIndex = getEndIndex(currentPageIndex = 2, itemsCount = 5)
        assert(startIndex == 0 && endIndex == 5)
    }

    @Test
    fun `start and end indexes for indicator with 5 items and last selected`() {
        val startIndex = getStartIndex(currentPageIndex = 4, itemsCount = 5)
        val endIndex = getEndIndex(currentPageIndex = 4, itemsCount = 5)
        assert(startIndex == 0 && endIndex == 5)
    }

    @Test
    fun `start and end indexes for indicator with 10 items and first selected`() {
        val startIndex = getStartIndex(currentPageIndex = 0, itemsCount = 10)
        val endIndex = getEndIndex(currentPageIndex = 0, itemsCount = 10)
        assert(startIndex == 0 && endIndex == 5)
    }

    @Test
    fun `start and end indexes for indicator with 10 items and 6th selected`() {
        val startIndex = getStartIndex(currentPageIndex = 5, itemsCount = 10)
        val endIndex = getEndIndex(currentPageIndex = 5, itemsCount = 10)
        assert(startIndex == 3 && endIndex == 8)
    }

    @Test
    fun `start and end indexes for indicator with 10 items and last selected`() {
        val startIndex = getStartIndex(currentPageIndex = 9, itemsCount = 10)
        val endIndex = getEndIndex(currentPageIndex = 9, itemsCount = 10)
        assert(startIndex == 5 && endIndex == 10)
    }
}