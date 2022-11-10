# nyris SDK for Android
[![Build Status](https://travis-ci.org/nyris/SDK.Android.svg?branch=master)](https://travis-ci.org/nyris/SDK.Android)
[![Download](https://api.bintray.com/packages/nyris/maven/SDK.Android/images/download.svg) ](https://bintray.com/nyris/maven/SDK.Android/_latestVersion)

![](nyris_logo.png)

Introduction
------
nyris is a high performance visual product search, object detection and visual recommendations engine 
for retail and industry. 

For more information please see [nyris.io](https://nyris.io/)

We provide a new SDK with better error handling and reactive programming support.
The SDK is written in [Kotlin](https://kotlinlang.org/) and compatible with [Java](https://docs.oracle.com/javase/8/docs/technotes/guides/language/index.html). 

Why new SDK ?
-----
The old [SDK](https://github.com/nyris/Nyris.IMX.Android) is offering only image matching services
and based on asynchronous callback system. This new SDK offers:

* Support of Reactive programming paradigm
* No more asynchronous callback/asyncTask
* Better multithreading handling
* Better error handling
* Type-safe HTTP client
* Unified response
* All the different nyris services 

nyris services 
-----
We offer : 
* Visual search
* Similarity search by image
* Object detection
* Manual matching
* Text Search
* Similarity search by SKU
* Feedback API

Requirements
----- 
* Android >= 4.0
* Images in **JPEG** format
* The minimum dimensions of the image are `512x512 px`
* The maximum size of the image is less than or equal to `500 KB` 
* RxJava and RxAndroid

Installation
-----
### Java Gradle
Add the dependencies
```groovy
repositories {
    maven {
        url  "https://maven.nyris.io/maven2/maven/"
    }
}

dependencies {
    implementation 'io.nyris:sdk:1.6.0'
    implementation "io.reactivex.rxjava2:rxandroid:$rxandroidVersion"
    implementation "android.arch.lifecycle:extensions:1.x.x" //Optional
}
```

Get Started
-----
### Jump To

* [Get instance](#get-instance)
* [Destroy the instance](#destroy-the-instance)
* [Match your first image](#match-your-first-image)
* [Extract objects from your image](#extract-objects-from-your-image)
* [Mark sent image as not found](#mark-sent-image-as-not-found)
* [Text Match Search](#text-match-search)
* [Send user feedback](#send-user-feedback)

### Get instance 
First, initialize an instance of `INyris` with your API Key :
 
```kotlin
class DemoApp : Application(){
    private var nyris : INyris    
    override fun onCreate() {
        super.onCreate()
        nyris = Nyris.createInstance(BuildConfig.API_KEY)
        // OR
        nyris = Nyris.createInstance("YOUR_API_KEY", NyrisConfig(isDebug = true))
    }
}
```
### Destroy the instances 
You can easily free all the created instances by adding `nyris` to the lifecyle of your main activity
or by calling `destroy()` of the sdk

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        lifecycle.addObserver(nyris)
    }
    
    // OR
    override fun onDestroy(){
        super.onDestroy()
        nyris.destroy()    
    }
}
```

**NOTE** : to make the SDK fully compatible with lifecycle-aware components you will need to add the missing dependecy 
```groovy
dependencies {    
    implementation "android.arch.lifecycle:extensions:1.x.x"
}
```

### Match your first image 
#### Basic way to match an image :
```kotlin
    val imageByteArray : ByteArray = /*Your byte array*/
    nyris
        .imageMatching()
        .match(imageByteArray)
        .subscribe({/*it:OfferResponse*/        
            // Handle your response
            val offers = it.offers
            
        },{/* it:Throwable */
            when (it) {
                is HttpException -> {
                    ...
                }
                is IOException -> {
                    ...
                }
                else -> {
                    // Unknown
                }
            }
        })
```

#### Advanced way to match an image : 

```kotlin
    //For more details about available feed attributes please check our documentation : http://docs.nyris.io/#available-feed-attributes.
    val imageByteArray : ByteArray = /* Your byte array */
    nyris
        .imageMatching()
        .outputFormat("PROVIDED_OUTPUT_FORMAT") // Set the desired OUTPUT_FORMAT
        .language("de") // Return only offers with language "de".
        .exact({
            enabled = false // disable exact matching
        })
        .similarity({//Performs similarity matching
            threshold = 0.5F // The lower limit of confidences to be considered good from similarity
            limit = 10 // The upper limit for the number of results to be returned from similarity
        })
        .ocr({//Performs optical character recognition on the images
            enabled = false // disable OCR
        })
        .regroup({
            enabled = false // This mode enables regrouping of the items
            threshold = 0.5F // The lower limit of confidences to be considered good from similarity
        })
        .recommendations() // Enables recommendation type searches that return all discovered results regardless of their score.
        .categoryPrediction({
            enabled = true // Enables the output of predicted categories.
            threshold = 0.5F // Sets the cutoff threshold for category predictions (range 0..1).
            limit = 10 // Limits the number of categories to return.
        })
        .limit(10) // Limit returned offers
        .match(imageTestBytes)
        .subscribe({/* it:OfferResponse */
            // Handle your response
            val offers = it.offers
            
        },{/* it:Throwable */
            ...
        })
```
The response is an object of type `OfferResponse` that contains list of `offers`. 

* If you specified a custom output format, you should use this call to get response as `JSON` format :

```kotlin
    val imageByteArray : ByteArray = /*Your byte array*/
    nyris
        .imageMatching()
        .match<JsonResponseBody>(imageByteArray, JsonResponseBody::class.java)
        .subscribe({/* it:JsonResponseBody */

            // Handle your response
            val json = it.json
        },{/*it:Throwable*/            
            ...
        })
```

### Extract objects from your image 
find objects in an image

```kotlin
    nyris
        .objectProposal()
        .regions(imageByteArray)
        .subscribe({/* it:ObjectList */
            // Handle your response
            val objects = it
        },{/* it:Throwable */
            ...
        })
```
Returned response is a List of objects. 

The extracted object has:
* `confidence` is the probability of the top item. Value range between : `0-1`.
* `region` is a Bounding box. It represents the location and the size of the object in the sent image. 

### Mark sent image as not found
It may happen that our service can't recognize or match an image. This is why we provide you a service to notify us
about the unrecognized image.

Before you mark an image as not found, you will need to extract the `requestId`. 

```kotlin   
    nyris
        .imageMatching()
        .match<OfferResponse>(imageByteArray)
        .subscribe({/* it:OfferResponse */
            // Handle your response
            requestId = it.requestId
            
        },{/* it:Throwable */
            ...
        })
```

After getting the `requestId` you can mark the image as not found. 

```kotlin
    nyris
        .notFoundMatching()
        .markAsNotFound(requestId)
        .subscribe({/* it:ResponseBody */
            ...
        },{/* it:Throwable */
            ...
        })
```

### Text Match Search
nyris offers a service that helps you to search offers by text, SKU, barcode, etc.

you can use the text search service the same way as [image matching service](#match-your-first-image)

#### Basic way to search : 

```kotlin
    nyris
        .textSearch()
        .searchOffers("YOUR_TEXT")
        .subscribe({/*it:OfferResponseBody*/        
            // Handle your response
            val offers = it.offers
            
        },{/* it:Throwable */
            ...
        })
```

#### Advanced way to search : 

```kotlin
    nyris
        .textSearch()
        .outputFormat("PROVIDED_OUTPUT_FORMAT") // Set the desired OUTPUT_FORMAT
        .language("de") // Return only offer with language "de"
        .regroup({
            enabled = true // This mode enables regrouping of the items
            threshold = 0.5F // The lower limit of confidences for the regroup
        })
        .limit(10) // Limit returned offers
        .searchOffers<JsonResponseBody>("YOUR_TEXT", JsonResponseBody::class.java)
        .subscribe({/* it:JsonResponseBody */
            //Handle your response
            val json = it.json
            
        },{/* it:Throwable */
            ...
        })
```

### Send user feedback

Before you send a feedback event, you will need to get the `requestId` and the `sessionId`.

```kotlin   
    nyris
        .imageMatching()
        .match<OfferResponse>(imageByteArray)
        .subscribe({/* it:OfferResponse */
            // Handle your response
            requestId = it.requestId
            sessionId = it.sessionId
            
        },{/* it:Throwable */
            ...
        })
```
#### Send click event
```kotlin
    nyris
        .feedback()
        .send(
            Event.Click(
                requestId = "REQUEST_ID",
                sessionId = "SESSION_ID",
                poductIds = listof("1","2","3"), //A list of string values, containing the Product IDs
                position = listof(0,2) // A list of int values describing the zero-based index of the result returned by our Find API.
            )
        )
        .subscribe({}, {}) //Completable
```

### Send Conversion event
```kotlin
    nyris
        .feedback()
        .send(
            Event.Conversion(
                requestId = "REQUEST_ID",
                sessionId = "SESSION_ID",
                poductIds = listof("1","2","3"), //A list of string values, containing the Product IDs
                position = listof(0,2) // A list of int values describing the zero-based index of the result returned by our Find API.
            )
        )
        .subscribe({}, {}) //Completable
```

### Send feedback event
```kotlin
    nyris
        .feedback()
        .send(
            Event.Feedback(
                requestId = "REQUEST_ID",
                sessionId = "SESSION_ID",
                success = true, //A boolean resembling general business success of a result.
                comment = "a comment" // An optional string containing verbatim user feedback.
            )
        )
        .subscribe({}, {}) //Completable
```

### Send region event
```kotlin
    nyris
        .feedback()
        .send(
            Event.Region(
                requestId = "REQUEST_ID",
                sessionId = "SESSION_ID",
                lefy = 1, // The left coordinate of the region as a fraction of the image width (range 0..1 ).
                top = 1, // The top coordinate of the region as a fraction of the image height (range 0..1 ).
                width = 1, // The width of the region as a fraction of the image width (range 0..1 ). 
                height = 1 // The height of the region as a fraction of the image height (range 0..1 ).
            )
        )
        .subscribe({}, {}) //Completable
```
License
=======
    Copyright 2018 nyris GmbH
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.