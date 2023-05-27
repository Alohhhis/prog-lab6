package commands

import Command
import data.Vehicle
import org.koin.core.component.inject
import utils.Storage
import java.util.LinkedHashMap


abstract class StorageCommand : Command() {
    val storage: Storage<LinkedHashMap<Int, Vehicle>, Int, Vehicle> by inject()
}