package newjeans.bunnies.auth

object Constant {
    private val numberRegex = Regex("""^[0-9]*$""")

    private val passwordRegex =
        Regex("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\\d)(?=.*[~․!@#\$%^&*()_\\-+=|\\\\;:‘“<>,.?/]).{10,20}\$")

    private val idRegex = Regex("^([a-zA-Z0-9]){1,12}\$")

    fun passwordPattern(input: String): Boolean {
        return passwordRegex.matches(input)
    }

    fun numberPattern(input: String): Boolean {
        return numberRegex.matches(input)
    }

    fun idPattern(input: String): Boolean {
        return idRegex.matches(input)
    }
}