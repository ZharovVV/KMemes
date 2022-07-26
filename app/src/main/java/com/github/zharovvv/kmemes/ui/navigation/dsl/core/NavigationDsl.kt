package com.github.zharovvv.kmemes.ui.navigation.dsl.core

import androidx.annotation.StringRes

interface Destination {
    val route: String

    @get:StringRes
    val nameResourceId: Int?
        get() = null
}

class Graph(
    override val route: String,
    @StringRes
    override val nameResourceId: Int? = null
) : Destination, Iterable<Destination> {

    private val destinations: MutableList<Destination> = mutableListOf()

    operator fun plusAssign(destination: Destination) {
        destinations += destination
    }

    override fun iterator(): Iterator<Destination> = GraphIterator()

    override fun toString(): String {
        return "Graph(route=$route, nameResourceId=${nameResourceId})"
    }

    //TODO возможно, можно как-то лучше написать
    private inner class GraphIterator : Iterator<Destination> {

        private var destinationIndex = -1
        private var _childIterator: Iterator<Destination>? = null

        override fun hasNext(): Boolean {
            val childIterator = _childIterator
            if (childIterator != null) {
                if (childIterator.hasNext()) {
                    return true
                } else {
                    _childIterator = null
                }
            }
            return destinationIndex < destinations.size
        }

        override fun next(): Destination {
            if (destinationIndex == -1) {
                destinationIndex++
                return this@Graph
            }
            val childIterator = _childIterator
            if (childIterator != null) {
                return childIterator.next()
            }
            val next = destinations[destinationIndex++]
            if (next is Graph) {
                val nextIterator = next.iterator()
                _childIterator = nextIterator
                return nextIterator.next()
            }
            return next
        }
    }
}

data class SimpleDestination(
    override val route: String,
    override val nameResourceId: Int? = null
) : Destination

interface Dialog

data class DialogDestination(
    override val route: String,
    override val nameResourceId: Int? = null
) : Destination, Dialog

//region DSL
class GraphScope(val graph: Graph)

fun createGraph(
    route: String,
    @StringRes nameResourceId: Int? = null,
    block: GraphScope.() -> Unit
): Graph {
    val graph = Graph(route, nameResourceId)
    val scope = GraphScope(graph)
    scope.block()
    return graph
}

fun GraphScope.destination(route: String, @StringRes nameResourceId: Int? = null) {
    graph += SimpleDestination(route, nameResourceId)
}

fun GraphScope.dialog(route: String, @StringRes nameResourceId: Int? = null) {
    graph += DialogDestination(route, nameResourceId)
}

fun GraphScope.graph(
    route: String,
    @StringRes nameResourceId: Int? = null,
    block: GraphScope.() -> Unit
) {
    graph += createGraph(route, nameResourceId, block)
}
//endregion