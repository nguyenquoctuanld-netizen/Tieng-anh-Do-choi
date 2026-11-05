package com.toywords.app

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Locale

/** Bọc TextToSpeech của Android: đọc tiếng Anh (giọng Mỹ), có thể đọc chậm. */
class TtsHelper(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)

    var ready by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    override fun onInit(status: Int) {
        if (status != TextToSpeech.SUCCESS) {
            error = "Không khởi động được bộ đọc giọng nói. Hãy kiểm tra Cài đặt > Ngôn ngữ > Chuyển văn bản thành giọng nói."
            return
        }
        val result = tts?.setLanguage(Locale.US)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            error = "Máy chưa có giọng đọc tiếng Anh. Vào Cài đặt > Chuyển văn bản thành giọng nói > chọn Google > tải dữ liệu giọng English (US)."
        } else {
            ready = true
        }
    }

    fun speak(text: String, rate: Float = 1f) {
        val engine = tts ?: return
        if (!ready) return
        engine.setSpeechRate(rate)
        engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "toy-" + System.nanoTime())
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
