package be.viveselic.myworkmanagerapplication

import MyWorker
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.startWorkButton).setOnClickListener {
            val inputData = Data.Builder()
                .putString("key_name", "Hello from MainActivity")
                .build()

            val workRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setInputData(inputData)
                .build()

            val workManager = WorkManager.getInstance(this)

            workManager.enqueue(workRequest)

            workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state.isFinished) {
                    val result = workInfo.outputData.getString("result_key")
                    findViewById<TextView>(R.id.resultTextView).text=("Worker Result: $result")
                }
            })
        }



    }
}