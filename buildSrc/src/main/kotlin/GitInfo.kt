import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object GitInfo {

    private fun runGitCommand(vararg args: String): String = try {
        val process = ProcessBuilder(*args)
            .redirectErrorStream(true)
            .start()
        process.inputStream.bufferedReader().readText().trim()
    } catch (_: Exception) {
        ""
    }

    fun getGitCommitCount(): Int =
        runGitCommand("git", "rev-list", "--count", "HEAD").toIntOrNull() ?: 1

    fun getGitShortHash(): String =
        runGitCommand("git", "rev-parse", "--short", "HEAD").ifEmpty { "dev" }

    fun getVersionName(): String {
        val date = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(Date())
        val hash = getGitShortHash()
        return "$date-$hash"
    }
}
