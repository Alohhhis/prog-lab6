package utils

import Command
import commands.Clear
import org.koin.core.component.KoinComponent

/**
 * Класс используется для ссылки на команды
 */
class CommandManger: KoinComponent {
    val commands = mapOf<String, Command>(
        "help" to Help(),
        "info" to Info(),
        "show" to Show(),
        "update" to Update(),
        "clear" to Clear(),
        "update" to Update(),
        "remove_greater" to RemoveGreater(),
        ""
    )

}