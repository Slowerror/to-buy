package com.slowerror.tobuy.domain.model

data class Item(
    val id: String = "",
    val title: String = "",
    val description: String? = null,
    val priority: Int = 0,
    val createdAt: Long = 0L,
    val categoryId: String = ""
)
