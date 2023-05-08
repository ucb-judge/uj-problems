package ucb.judge.ujproblems.utils

class LatexSanitizer {
    companion object {
        @JvmStatic
        fun sanitizeLatex(input: String): String {
            // Replace backslashes with double backslashes
            var output = input.replace("\\", "\\\\")

            // Replace ampersands with "\&"
            output = output.replace("&", "\\&")

            // Replace angle brackets with "\langle" and "\rangle"
            output = output.replace("<", "\\langle ")
            output = output.replace(">", "\\rangle ")

            // Replace dollar signs with "\$"
            output = output.replace("$", "\\$")

            // Add escape characters to special characters
            output = output.replace("#", "\\#")
            output = output.replace("%", "\\%")
            output = output.replace("_", "\\_")
            output = output.replace("{", "\\{")
            output = output.replace("}", "\\}")

            return output
        }
    }
}