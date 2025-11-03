package com.example.dynamic.ui.demo

data class DataBean(
    val key: String,
    val type: String,
    val title: String,
    val click: String,
    val packageName: String? = null,
    val className: String? = null,
    val deepLink: String? = null,
    val icon: Int? = null,
    val image: String? = null,
)
