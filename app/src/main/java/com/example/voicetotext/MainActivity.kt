package com.example.voicetotext
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.R.attr
import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import com.example.voicetotext.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var flashLightStatus: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startDictation.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"uz")
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", arrayOf("uz"))

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.speech_prompt))

            startActivityForResult(intent,1)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1 ->{
                if (resultCode == RESULT_OK && null != attr.data){
                    val result: ArrayList<String> = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!!

                    binding.textOutput.setText(result[0])
                    val thetext:String = binding.textOutput.text.toString().lowercase()
                    if (thetext == "chiroqni yoq" || thetext == "chiroq yonsin" ||thetext == "выключай фонарики" || thetext == "включай фонар" || thetext == "включай фонара" || thetext == "включай спичку" || thetext=="svetni yoq"){
                        openFlash()
                    }else if (thetext == "chiroqni ochib" || thetext == "chiroqni ochish" || thetext == "chiroqni o`chir" || thetext == "отключай спичку" || thetext == "отключайте спичку" || thetext == "спичку отключай" || thetext=="svetni ochib"){
                        closeFlash()
                    }
                }
            }
        }
    }

    // flashlight turn on
    private fun openFlash(){
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
        if (!flashLightStatus) {
            try {
                cameraManager.setTorchMode(cameraId, true)
                flashLightStatus = true

            } catch (e: CameraAccessException) {
            }
        } else {
            try {
                cameraManager.setTorchMode(cameraId, false)
                flashLightStatus = false
            } catch (e: CameraAccessException) {
            }
        }
    }

    // flashlight turn off
    private fun closeFlash(){
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
        if (!flashLightStatus) {
            try {
                cameraManager.setTorchMode(cameraId, false)
                flashLightStatus = false

            } catch (e: CameraAccessException) {
            }
        } else {
            try {
                cameraManager.setTorchMode(cameraId, false)
                flashLightStatus = false
            } catch (e: CameraAccessException) {
            }
        }
    }
}