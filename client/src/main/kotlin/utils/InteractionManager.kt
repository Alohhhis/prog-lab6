package utils

import data.VehicleType
import ArgumentType
import ClientMain
import CommandResult
import FileManager
import data.Vehicle
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException

/**
 * Implements [Interactor] interface. Executes load command on start
 *
 * @param userManager used to show messages to user and get input from user
 * @param fileManager used to execute command file
 * @param commandManager manages the commands
 */
class InteractionManager(
    private val userManager: ReaderWriter,
    private val fileManager: FileManager,
    private val commandManager: CommandManager
) : KoinComponent, Interactor {

    private val validator: Validator by inject()
    private val invitation = ">>>"
    private var isActive = true
    private var lastArgument: String? = null
    private val executingFiles = ArrayDeque<String>()
    private lateinit var clientApp: ClientMain

    override fun getClient(): ClientMain = clientApp

    override fun start(clientApp: ClientMain) {
        this.isActive = true
        this.clientApp = clientApp
        userManager.writeLine("Кто прочитат, тот холл\n" +
                "Для вывода списка команд введите help")
        //executeCommand("update_commands")
        while (isActive) {
            userManager.write(invitation)
            interact(userManager.readLine())
        }
    }

    override fun getArgs(argTypes: Array<ArgumentType>): Array<Any> {
        val args = arrayListOf<Any>()
        argTypes.forEach {
            args.add(when (it) {
                ArgumentType.INT -> lastArgument?.toIntOrNull() ?: validator.getInt()
                ArgumentType.STRING -> lastArgument ?: validator.getString()
                ArgumentType.VEHICLE_TYPE -> lastArgument?.let { VehicleType.valueOfOrNull(it) } ?: validator.getVehicleType()
                ArgumentType.FUEL_TYPE -> validator.getVehicle()
            })
        }
        return args.toArray()
    }

    override fun exit() {
        isActive = false
        clientApp.stop()
    }

    override fun executeCommandFile(path: String) {
        val text = fileManager.readFile(path)
        if (path in executingFiles) {
            userManager.writeLine("Предотвращение зацикливания!")
            return
        }
        executingFiles.add(path)
        FileInteractor(this, text.lines()).start(clientApp)
        executingFiles.removeLast()
    }

    /**
     * Handles the string with command and possible argument
     *
     * @param text line from the user with the name of the command
     */
    private fun interact(text: String) {
        val input = text.trim().split(" ")
        if (input.count() > 2) {
            userManager.writeLine("Слишком много аргументов в строке")
            return
        }
        try {
            val command = input[0]
            lastArgument = if (input.count() == 2) input[1] else null
            executeCommand(command)
        } catch (e: IOException) {
            exit()
        } catch (e: Throwable) {
            userManager.writeLine(e.toString())
            userManager.writeLine(e.message ?: "")
        } finally {
            executingFiles.clear()
        }
    }

    override fun executeCommand(command: String) {
        val argTypes = commandManager.getArgs(command)
        val args = getArgs(argTypes)

        when (val result = commandManager.executeCommand(clientApp, command, args)) {
            is CommandResult.Failure -> userManager.writeLine("Команда ${result.commandName} завершилась ошибкой: ${result.throwable.message}")
            is CommandResult.Success -> {
                userManager.writeLine("Команда ${result.commandName} исполнена.")
                result.message?.let { userManager.writeLine(it) }
            }

            null -> userManager.writeLine("Сервер вернул непотребщину")
        }

    }


}