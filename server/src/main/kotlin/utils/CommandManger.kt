package utils

import Command
import commands.*
import exceptions.CommandException
import org.koin.core.component.KoinComponent

/**
 * Класс используется для ссылки на команды
 */
class CommandManager: KoinComponent {
    val commands = mapOf<String, Command>(
        "help" to Help(),
        "info" to Info(),
        "show" to Show(),
        "update" to Update(),
        "clear" to Clear(),
        "remove_greater" to RemoveGreater(),
        "remove_head" to RemoveHead(),
        "clear" to Clear(),
        "remove_by_id" to RemoveByKey(),
        "isert" to Insert()
    )
    /**
     * Проверка, существует ли такая команда
     */
    fun getCommand(name: String): Command {
        val command = commands[name] ?: throw CommandException("Такой команды не существует")
        return command
    }
}