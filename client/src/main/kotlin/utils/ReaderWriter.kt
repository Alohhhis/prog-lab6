package utils

interface ReaderWriter {
    fun readLine(): String

    fun writeLine(text: String)

    fun write(text: String)

    fun getValidatedValue(message: String, validator: (String) -> Boolean): String
}