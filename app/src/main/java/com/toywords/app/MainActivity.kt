package com.toywords.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    private lateinit var tts: TtsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TtsHelper(this)
        setContent { ToyApp(tts) }
    }

    override fun onDestroy() {
        tts.shutdown()
        super.onDestroy()
    }
}

@Composable
fun ToyApp(tts: TtsHelper) {
    var slow by remember { mutableStateOf(false) }
    var playing by remember { mutableStateOf<String?>(null) }
    val rate = if (slow) 0.6f else 1f

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBEF))
            .statusBarsPadding()
    ) {
        // Thanh tiêu đề
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFFE74C3C))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text("TOY WORDS", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                Text("Chạm vào đồ chơi để nghe và đọc theo", color = Color(0xFFFFE9E6), fontSize = 12.sp)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Đọc chậm", color = Color.White, fontSize = 12.sp)
                Switch(checked = slow, onCheckedChange = { slow = it })
            }
        }

        tts.error?.let {
            Text(
                it,
                color = Color(0xFF922B21),
                fontSize = 13.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFDEDEC))
                    .padding(12.dp)
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(toys) { toy ->
                ToyCard(
                    toy = toy,
                    active = playing == toy.en,
                    onWord = {
                        playing = toy.en
                        tts.speak(toy.en, rate)
                    },
                    onExample = {
                        playing = toy.en
                        tts.speak(toy.example, rate)
                    }
                )
            }
        }
    }
}

@Composable
fun ToyCard(toy: Toy, active: Boolean, onWord: () -> Unit, onExample: () -> Unit) {
    val accent = Color(toy.color)
    val shape = RoundedCornerShape(22.dp)
    Column(
        modifier = Modifier
            .clip(shape)
            .background(lerp(Color(toy.bg), Color.White, 0.5f))
            .border(
                if (active) BorderStroke(3.dp, accent) else BorderStroke(0.dp, Color.Transparent),
                shape
            )
            .clickable(onClick = onWord)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(toy.drawable),
            contentDescription = toy.en,
            modifier = Modifier.size(120.dp)
        )
        Text(
            toy.en,
            color = accent,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )
        Text("🔊  " + toy.ipa, color = Color(0xFF7F8C8D), fontSize = 13.sp)
        Text(
            toy.vi,
            color = Color(0xFF2D3436),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        // Chạm vào câu ví dụ để nghe cả câu
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.75f))
                .clickable(onClick = onExample)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "▶ " + toy.example,
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF34495E),
                textAlign = TextAlign.Center
            )
            Text(
                toy.exampleVi,
                fontSize = 11.sp,
                color = Color(0xFF7F8C8D),
                textAlign = TextAlign.Center
            )
        }
    }
}
