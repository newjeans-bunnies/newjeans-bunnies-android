package newjeans.bunnies.auth

object Constant {
    private val numberRegex = Regex("""^[0-9]*$""")
    private val passwordPattern =
        Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~․!@#\$%^&*()_\\-+=|\\\\;:‘“<>,.?/]).{10,20}\$")

    fun passwordPattern(input: String): Boolean {
        return passwordPattern.matches(input)
    }

    fun numberPattern(input: String): Boolean {
        return numberRegex.matches(input)
    }
}