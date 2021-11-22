package io.nyris.sdk.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.nyris.sdk.app.databinding.ActivityMainBinding
import io.nyris.sdk.disposable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
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
            .subscribe({
                binding.tvResults.text =
                    "Matched offers : ${it.offers.size} \n " +
                            "Predicted categories : ${it.predictedCategories}"
            },{
                binding.tvResults.text = it.message
            }).disposable()
    }
}
