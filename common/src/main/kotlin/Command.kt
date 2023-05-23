abstract class Command {
    abstract fun execute(args: Array<Any>): CommandResult

    abstract fun getDescription(): String

    abstract fun getArgumentType(): Array<ArgumentType>
}