package com.example.wifiqrcodesgenerator.utils

fun getStartIndex(currentPageIndex: Int, itemsCount: Int): Int {
    return Integer.max(0, Integer.min(currentPageIndex-2, itemsCount-5))
}

fun getEndIndex(currentPageIndex: Int, itemsCount: Int): Int {
    return Integer.min(itemsCount, Integer.max(currentPageIndex+3, 5))
}