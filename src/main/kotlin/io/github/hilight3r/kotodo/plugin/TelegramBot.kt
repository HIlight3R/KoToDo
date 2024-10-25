package io.github.hilight3r.kotodo.plugin

import io.github.dehuckakpyt.telegrambot.plugin.TelegramBot
import io.github.dehuckakpyt.telegrambot.source.callback.CallbackContentSource
import io.github.dehuckakpyt.telegrambot.source.chain.ChainSource
import io.github.dehuckakpyt.telegrambot.source.message.MessageSource
import io.github.dehuckakpyt.telegrambot.ext.source.inDatabase
import io.github.hilight3r.kotodo.handler.startCommand
import io.ktor.server.application.*

fun Application.configureTelegramBot() {
    install(TelegramBot) {
        messageSource = { MessageSource.inDatabase }

        receiving {
            longPolling {
                limit = 10
                timeout = 25
            }

            callbackContentSource = {
                CallbackContentSource.inDatabase(
                    maxCallbackContentsPerUser = 2
                )
            }
            chainSource = { ChainSource.inDatabase }

            handling {
                startCommand()
            }
        }
    }
}