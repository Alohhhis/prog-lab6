package commands

import Command

abstract class ClientCommand : Command() {
    val interactor: Interactor by inject()
}