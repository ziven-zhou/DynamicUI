package com.example.dynamic.ui.demo

data class DataBean(
    val key: String,
    val type: String,
    val content: String,
    val action: String,
    val packageName: String? = null,
    val className: String? = null,
    val deepLink: String? = null,
    val image: String? = null,
)
