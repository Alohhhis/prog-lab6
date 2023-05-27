package utils

interface Storage<T, V, K> {
    fun clear()
    fun getInfo(): String
    fun getCollection()
}