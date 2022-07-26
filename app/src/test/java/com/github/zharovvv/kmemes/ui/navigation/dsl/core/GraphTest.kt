package com.github.zharovvv.kmemes.ui.navigation.dsl.core

import org.junit.Test

internal class GraphTest {

    @Test
    fun `test graph`() {
        val rootGraph = Graph("root").apply {
            plusAssign(SimpleDestination("Simple1"))
            plusAssign(DialogDestination("Dialog1"))
            plusAssign(
                Graph("Graph1").apply {
                    plusAssign(SimpleDestination("Simple2"))
                    plusAssign(SimpleDestination("Simple3"))
                }
            )
        }
        rootGraph.forEach {
            println(it)
        }

        println("---------------------------")

        val dslRootGraph = createGraph("root") {
            destination("Simple1")
            dialog("Dialog1")
            graph("Graph1") {
                destination("Simple2")
                destination("Simple3")
            }
        }
        dslRootGraph.forEach {
            println(it)
        }
    }
}