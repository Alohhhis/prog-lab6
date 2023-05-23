import exceptions.FileException
import java.io.File
import java.util.Scanner

class FileManager {


    /**
     * Построчно читает файл по заданному пути
     *
     * @throws [FileException] если файл не удаётся открыть
     * @param путь до файла
     */
    fun readFile(path: String): String{ //чтение данных из файла по указанному пути
    // создается новый объект класса File, инициализированный с помощью параметра path.
    // Затем вызывается метод canRead() на этом объекте.
        if (!File(path).canRead()){
            throw FileException("Файл не открывается")
        }
        val scanner = Scanner(File(path))
        return buildString {
            while (scanner.hasNextLine()){
                append(scanner.nextLine())
                if (scanner.hasNextLine()) append("\n")
            }
        }
    }

    fun writeFile(path: String, text:String){

    }
}