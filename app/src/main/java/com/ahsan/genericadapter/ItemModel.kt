package com.ahsan.genericadapter
data class FirstItem(
    val id: Int,
    val name: String,
) : AdapterItem() {
    override val viewType: Int get() = FIRST_TYPE

    companion object {
        const val FIRST_TYPE = 1
    }
}

data class SecondItem(
    val id: Int,
    val name: String,
) : AdapterItem() {
    override val viewType: Int get() = FIRST_TYPE

    companion object {
        const val FIRST_TYPE = 1
    }
}