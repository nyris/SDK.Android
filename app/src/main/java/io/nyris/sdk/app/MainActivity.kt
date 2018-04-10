package io.nyris.sdk.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val customCompositeDisposable = CustomCompositeDisposable()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val demoApp = application as DemoApp
        val nyris = demoApp.nyris
        lifecycle.addObserver(nyris)
        lifecycle.addObserver(customCompositeDisposable)

        setContentView(R.layout.activity_main)

        val inputStream = assets.open("test_image.jpg")
        val byteArray = ByteArray(inputStream.available())
        inputStream.read(byteArray)
        inputStream.close()

        tvResults.text = "Matching ..."
        customCompositeDisposable.add(nyris.
            imageMatching()
            .match(byteArray)
            .subscribe({
                tvResults.text = "Matched offers : ${it.offers.size}"
            },{
                tvResults.text = it.message
            }))
    }
}
