package utils

interface Storage<T, V, K> {
    fun clear()
    fun getInfo(): String
    fun update(id: V, element: K)
    fun getCollection(predicate: Map.Entry<V, K>.() -> Boolean): T
}