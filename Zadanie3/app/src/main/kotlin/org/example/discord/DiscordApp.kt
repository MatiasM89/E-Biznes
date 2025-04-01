package org.example.discord

import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import kotlinx.coroutines.runBlocking

lateinit var kord: Kord

fun main() {
    runBlocking {
        val token = "MTM1NjY2NTY1MzQwMjE0MDcxMg.GqBGD_.mbJKL9jgCKWsMir39EBnelMC67ZKkJPyzO48F0"
        kord = Kord(token)
        val myUserId = "336905972138246144"

        sendMessage(myUserId, "Just some random message")
        receiveMessages()
        kord.login()
    }
}

suspend fun sendMessage(userId: String, message: String) {
    val user = kord.getUser(Snowflake(userId))
    val dmChannel = user?.getDmChannelOrNull()
    dmChannel?.createMessage(message) ?: println("Unable to send message")
}

fun receiveMessages() {
    kord.on<MessageCreateEvent> {
        val message = this.message
        if (message.author?.isBot == true) return@on

        val content = message.content
        if (content == "!ping") {
            message.channel.createMessage("Responding to ping")
        }
    }
}