package com.example.lambda_test_project

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory
import com.amazonaws.regions.Regions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create an instance of CognitoCachingCredentialsProvider
        // Create an instance of CognitoCachingCredentialsProvider
        val cognitoProvider = CognitoCachingCredentialsProvider(
            this.applicationContext, "identity-pool-id", Regions.US_WEST_2
        )

// Create LambdaInvokerFactory, to be used to instantiate the Lambda proxy.

// Create LambdaInvokerFactory, to be used to instantiate the Lambda proxy.
        val factory = LambdaInvokerFactory(
            this.applicationContext,
            Regions.US_WEST_2, cognitoProvider
        )

// Create the Lambda proxy object with a default Json data binder.
// You can provide your own data binder by implementing
// LambdaDataBinder.

// Create the Lambda proxy object with a default Json data binder.
// You can provide your own data binder by implementing
// LambdaDataBinder.
        val myInterface = factory.build(MyInterface::class.java)

        val request = RequestClass("John", "Doe")
// The Lambda function invocation results in a network call.
// Make sure it is not called from the main thread.
//非同期処理部分だができていない



//非同期処理（別のスレッドをつくる）↓
            fun doInBackground(vararg params: RequestClass): ResponseClass? {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                return try {
                    myInterface.AndroidBackendLambdaFunction(params[0])
                } catch (lfe: LambdaFunctionException) {
                    Log.e("Tag", "Failed to invoke echo", lfe)
                    null
                }
            }
//ここまで↑
//doInBackGroudが終わった後にメインスレッドで実行される↓
            fun onPostExecute(result: ResponseClass?) {
                if (result == null) {
                    return
                }
//ここまで↑
                // Do a toast
                Toast.makeText(this@MainActivity, result.getGreetings(), Toast.LENGTH_LONG).show()
            }





        //非同期処理の実行
//        CoroutineRepository().executeAwait()

        //非同期処理の部分
//        object : AsyncTask<RequestClass?, Void?, ResponseClass?>() {
//            protected fun doInBackground(vararg params: RequestClass): ResponseClass? {
//                // invoke "echo" method. In case it fails, it will throw a
//                // LambdaFunctionException.
//                return try {
//                    myInterface.AndroidBackendLambdaFunction(params[0])
//                } catch (lfe: LambdaFunctionException) {
//                    Log.e("Tag", "Failed to invoke echo", lfe)
//                    null
//                }
//            }
//
//            override fun onPostExecute(result: ResponseClass?) {
//                if (result == null) {
//                    return
//                }
//
//                // Do a toast
//                Toast.makeText(this@MainActivity, result.getGreetings(), Toast.LENGTH_LONG).show()
//            }
//        }.
         // CoroutineRepository().execute()

    }

}
