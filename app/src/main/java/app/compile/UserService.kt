package app.compile

import android.os.RemoteException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import androidx.annotation.Keep

@Keep
public class UserService : IUserService.Stub() {

    @Throws(RemoteException::class)
    override fun destroy() {
        System.exit(0)
    }

    @Throws(RemoteException::class)
    override fun exit() {
        destroy()
    }

    @Throws(RemoteException::class)
    override fun execLine(command: String): String {
        return try {
            // 执行 shell 命令
            val process = Runtime.getRuntime().exec(command)
            // 读取执行结果
            readResult(process)
        } catch (e: IOException) {
            throw RemoteException()
        } catch (e: InterruptedException) {
            throw RemoteException()
        }
    }

    @Throws(RemoteException::class)
    override fun execArr(command: Array<String>): String {
        return try {
            // 执行 shell 命令
            val process = Runtime.getRuntime().exec(command)
            // 读取执行结果
            readResult(process)
        } catch (e: IOException) {
            throw RemoteException()
        } catch (e: InterruptedException) {
            throw RemoteException()
        }
    }

    /**
     * 读取执行结果，如果有异常会向上抛
     */
    @Throws(IOException::class, InterruptedException::class)
    private fun readResult(process: Process): String {
        val stringBuilder = StringBuilder()
        // 读取执行结果
        val inputStreamReader = InputStreamReader(process.inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line).append("\n")
        }
        inputStreamReader.close()
        process.waitFor()
        return stringBuilder.toString()
    }
}
