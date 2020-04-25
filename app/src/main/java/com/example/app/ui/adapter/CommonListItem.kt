package com.example.app.ui.adapter

interface CommonListItem {

    fun id(): Long

    fun type(factory: CommonTypeFactory): Int
}
