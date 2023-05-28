package utils

import ArgumentType
import ClientMain
import data.*
import exceptions.CommandFileException
import org.koin.core.component.KoinComponent

/**
 * Executes commands from the file
 *
 * @param interactor delegates to this most of the methods
 * @param lines have all lines from the command file
 * @throws [CommandFileException] if exception occurred
 */
class FileInteractor(
    private val interactor: Interactor,
    private val lines: List<String>
) : KoinComponent, Interactor by interactor, Validator {
    private var index = 0
    private var lastArgument: String? = null
    private lateinit var clientMain: ClientMain

    /**
     * Starts executing commands from file
     *
     * @param clientApp the current client connected to the server
     */
    override fun start(clientApp: ClientMain) {
        this.clientMain = clientApp
        while (hasNext()) {
            interact(next())
        }
    }

    /**
     * Stops executing file commands
     */
    override fun exit() {
        interactor.exit()
        index = lines.count()
    }

    /**
     * Takes commands one by one and arguments in the line. Tries to execute command
     *
     * @param stringCommand the string with the command
     */
    private fun interact(stringCommand: String) {
        val input = stringCommand.trim().split(" ")
        if (input.count() > 2) {
            throw CommandFileException("Слишком много аргументов в строке")
        }
        try {
            val command = input[0]
            lastArgument = if (input.count() == 2) input[1] else null
            executeCommand(command)
        } catch (e: CommandFileException) {
            throw e
        } catch (e: Throwable) {
            throw CommandFileException(e.message)
        }
    }

    /**
     * Gets arguments from files by checking last argument or next line
     *
     * @param argTypes array with [ArgumentType]
     * @return Any array filled with needed arguments
     */
    override fun getArgs(argTypes: Array<ArgumentType>): Array<Any> {
        val args = arrayListOf<Any>()
        argTypes.forEach {
            args.add(
                when (it) {
                    ArgumentType.INT -> getInt()
                    ArgumentType.STRING -> getString()
                    ArgumentType.VEHICLE_TYPE -> getVehicleType()
                    ArgumentType.FUEL_TYPE -> getVehicle()
                }
            )
        }
        return args.toArray()
    }

    override fun getString(): String = lastArgument ?: throw CommandFileException("Нет аргумента")
    override fun getInt(): Int = lastArgument?.toIntOrNull() ?: throw CommandFileException("Не Int")
    override fun getVehicleType(): VehicleType= lastArgument?.let { VehicleType.valueOfOrNull(it) } ?: throw CommandFileException("Не тип топлива")

    override fun getVehicle(): Vehicle {
        val name = next()
        val coordinates = Coordinates(
            next().toFloatOrNull() ?: throw CommandFileException("Не Float"),
            next().toDoubleOrNull() ?: throw CommandFileException("Не Double")
        )
        val enginePower = next().toIntOrNull() ?: throw CommandFileException("Не Int")
        val distanceTravelled = next().toLongOrNull()

        val fuelType = FuelType.valueOfOrNull(next()) ?: throw CommandFileException("Не Fuel type`")
        val vehicleType = VehicleType.valueOfOrNull(next()) ?: throw CommandFileException("Не Vehicle Type`")

        return Vehicle(
            name = name,
            coordinates = coordinates,
            enginePower = enginePower,
            distanceTravelled = distanceTravelled,
            fuelType = fuelType,
            vehicleType = vehicleType
        )
    }

    /**
     * @return next line of the command file
     */
    fun next(): String {
        if (hasNext()) return lines[index++]
        throw CommandFileException("Недостаточно строк")
    }

    /**
     * Check if there is next line of the command file
     *
     * @return true if it is not last line yet
     */
    fun hasNext(): Boolean = (index < lines.count())
}