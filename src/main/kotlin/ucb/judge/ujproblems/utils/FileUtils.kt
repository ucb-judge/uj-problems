package ucb.judge.ujproblems.utils

import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile

class FileUtils {
    companion object {
        @JvmStatic
        fun createProblemFile(description: String, input: String, output: String): MultipartFile {
            val problemString = StringBuilder()
                .append("\\section{Description}\n\n")
                .append(description)
                .append("\n\n\\section{Input}\n\n")
                .append(input)
                .append("\n\n\\section{Output}\n\n")
                .append(output)
                .toString()
            val bytes = problemString.toByteArray()
            return MockMultipartFile("problem.txt", "problem.txt", "text/plain", bytes)
        }

        @JvmStatic
        fun createTextFile(text: String, filename: String): MultipartFile {
            val bytes = text.toByteArray()
            return MockMultipartFile("${filename}.txt", "${filename}.txt", "text/plain", bytes)
        }
    }
}