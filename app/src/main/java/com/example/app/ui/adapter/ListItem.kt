package com.example.app.ui.adapter

interface ListItem {

    fun id(): Long

    fun type(factory: ListTypeFactory): Int
}
