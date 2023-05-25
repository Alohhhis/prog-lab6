import org.koin.core.component.KoinComponent

/**
 * Абстрактный класс, определяющий команды.
 */
abstract class Command : KoinComponent{
    /**
     * Запускает выполнение команды
     *
     * @return [CommandResult] с именем команды и данными или исключением, возвращаемыми командой
     */
    abstract fun execute(args: Array<Any>): CommandResult

    /**
     * @возвращает описание команды.
     */
    abstract fun getDescription(): String

    /**
     * @возвращает массив допустимых значений [ArgumentType] для команды
     */
    abstract fun getArgumentType(): Array<ArgumentType>
}