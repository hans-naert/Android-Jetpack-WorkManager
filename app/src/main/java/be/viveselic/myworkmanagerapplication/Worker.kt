import android.content.Context
import android.provider.ContactsContract
import androidx.work.Data

import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val message = inputData.getString("key_name") ?: "Default Value"

        // Simulate some background work
        val outputData = Data.Builder()
            .putString("result_key", "Processed: $message")
            .build()

        return Result.success(outputData) // Send result back
    }
}