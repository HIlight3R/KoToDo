package io.github.hilight3r.kotodo.factory

import org.koin.core.annotation.Single

@Single
class SimpleTextFactory {
    fun getSimpleText(): String = "Simple text from dependency"
}