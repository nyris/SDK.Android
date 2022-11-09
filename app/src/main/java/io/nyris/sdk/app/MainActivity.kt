package io.nyris.sdk.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.nyris.sdk.app.databinding.ActivityMainBinding
import io.nyris.sdk.disposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val demoApp = application as DemoApp
        val nyris = demoApp.nyris
        lifecycle.addObserver(nyris)

        setContentView(binding.root)

        val inputStream = assets.open("test_image.jpg")
        val byteArray = ByteArray(inputStream.available())
        inputStream.read(byteArray)
        inputStream.close()

        binding.tvResults.text = "Matching ..."

        nyris
            .imageMatching()
            .recommendations()
            .match(byteArray)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                binding.tvResults.text =
                    "Matched offers : ${it.offers?.size} \n " +
                    "Predicted categories : ${it.predictedCategories} \n" +
                    "Session: ${it.sessionId} \n" +
                    "Request: ${it.requestId} \n"
            }, {
                binding.tvResults.text = it.message
            })
            .disposable()
    }
}
