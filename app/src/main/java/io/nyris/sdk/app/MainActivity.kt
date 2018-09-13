package io.nyris.sdk.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.nyris.sdk.disposable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val demoApp = application as DemoApp
        val nyris = demoApp.nyris
        lifecycle.addObserver(nyris)

        setContentView(R.layout.activity_main)

        val inputStream = assets.open("test_image.jpg")
        val byteArray = ByteArray(inputStream.available())
        inputStream.read(byteArray)
        inputStream.close()

        tvResults.text = "Matching ..."
        nyris.
            imageMatching()
            .recommendations()
            .match(byteArray)
            .subscribe({
                tvResults.text = "Matched offers : ${it.offers.size} \n Predicted categories : ${it.predictedCategories}"
            },{
                tvResults.text = it.message
            }).disposable()
    }
}
