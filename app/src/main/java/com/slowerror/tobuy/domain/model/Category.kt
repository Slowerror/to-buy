package com.slowerror.tobuy.domain.model


data class Category(
    val id: String,
    val name: String
) {
    companion object {
        const val DEFAULT_CATEGORY_ID = "NONE"

        fun getDefaultCategory(): Category = Category(DEFAULT_CATEGORY_ID, "NONE")
    }
}