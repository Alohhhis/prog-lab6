package utils

import ClientMain

interface Interactor {
    /**
     * Завершает взаимодействие с пользователем
     */
    fun exit()

    /**
     * Начинает взаимодействие с пользователем
     */
    fun start(clientMain: ClientMain)

    /**
     * Получает аргументы от пользователя
     *
     * @param
     */
    fun getArgs()

    /**
     * Выполняет команду
     *
     * @param
     */
    fun executeCommand(command: String)

    /**
     * Возвращает текущий клиент, подключенный к серверу
     *
     * @return клиент, подключенный к серверу
     */
    fun getClient(): ClientMain
}