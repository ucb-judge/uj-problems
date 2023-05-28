package ucb.judge.ujproblems.utils

class TokenUtils {

    companion object {
        /**
         * Method that returns the value of a field in a token
         * @param token Token to be analyzed in the form of "Bearer <token>"
         * @param field Field to be returned
         * @return Value of the field
         */
        fun getField(token: String, field: String): String {
            val tokenParts = token.split(" ")
            val tokenBody = tokenParts[1]
            val tokenBodyParts = tokenBody.split(".")
            val tokenBodyJson = String(java.util.Base64.getDecoder().decode(tokenBodyParts[1]))
            val tokenBodyJsonParts = tokenBodyJson.split(",")
            val tokenBodyJsonField = tokenBodyJsonParts.find { it.contains(field) }
            val tokenBodyJsonFieldParts = tokenBodyJsonField!!.split(":")
            val tokenBodyJsonFieldValue = tokenBodyJsonFieldParts[1]
            return tokenBodyJsonFieldValue.substring(1, tokenBodyJsonFieldValue.length - 1)
        }
    }
}
