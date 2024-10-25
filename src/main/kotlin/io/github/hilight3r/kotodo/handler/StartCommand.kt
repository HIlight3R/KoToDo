package io.github.hilight3r.kotodo.handler

import io.github.dehuckakpyt.telegrambot.factory.input.input
import io.github.dehuckakpyt.telegrambot.factory.template.TemplateFactory.property
import io.github.dehuckakpyt.telegrambot.handling.BotHandling

fun BotHandling.startCommand() {
    val startText by property()

    command("/start") {
        sendPhoto(input("/images/avatar.webp"), caption = startText, parseMode = "HTML")
    }
}
