package utils

import Command
import commands.Clear
import commands.Help
import commands.Info
import commands.Show
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
        "update" to Update(),
        "remove_greater" to RemoveGreater(),
        "clear" to Clear(),
    )
    /**
     * Проверка, существует ли такая команда
     */
    fun getCommand(name: String): Command {
        val command = commands[name] ?: throw CommandException("Такой команды не существует")
        return command
    }
}