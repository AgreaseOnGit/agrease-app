package com.bangkit.capstone.agreaseapp.ui.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.ui.component.respond.LoadingIndicator
import com.bangkit.capstone.agreaseapp.ui.screen.ViewModelFactory
import com.bangkit.capstone.agreaseapp.ui.state.UiState

data class Message(val sender: String, val content: String, val isMe: Boolean = false)

@Composable
fun ChatScreen(
    redirectToWelcome: (String) -> Unit,
    viewModel: ChatViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    var message by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(generateDummyData()) }
    val listState = rememberLazyListState()

    LaunchedEffect(messages) {
        listState.scrollToItem(messages.size - 1)
    }

    viewModel.user.collectAsState(initial = UiState.Loading).value.let { user ->
        when (user) {
            is UiState.Loading -> {
                LoadingIndicator()
                viewModel.getUser()
            }

            is UiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f),
                        state = listState
                    ) {
                        items(messages) { chat ->
                            ChatItem(chat, user.data)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = message,
                            onValueChange = { message = it },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp),
                            label = { Text("Type a message") },
                            keyboardActions = KeyboardActions(
                                onSend = {
                                    if (message.isNotEmpty()) {
                                        val newMessage = Message("You", message, true)
                                        messages = messages + newMessage
                                        message = ""
                                    }
                                }
                            ),
                        )

                        IconButton(
                            onClick = {
                                if (message.isNotEmpty()) {
                                    if (message == "Hai"){
                                        val newMessage = Message("You", message, true)
                                        messages = messages + newMessage
                                        message = ""
                                        val botMessage = Message("Agrease Bot", "Hai Mr.Vincent, Apakah ada yang bisa saya bantu seputar produk pertanian untuk ladang anda?", false)
                                        messages = messages + botMessage
                                    } else {
                                        val newMessage = Message("You", message, true)
                                        messages = messages + newMessage
                                        message = ""
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send"
                            )
                        }
                    }
                }
            }

            is UiState.Error -> {
                redirectToWelcome(user.errorMessage)
            }

            is UiState.Unauthorized -> {
                redirectToWelcome("Unauthorized")
            }
        }
    }
}

@Composable
fun ChatItem(message: Message, user: UserModel) {
    Column(
        horizontalAlignment = if (message.isMe) Alignment.End else Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(
            text = message.sender,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = if (message.isMe) Modifier.padding( end = 60.dp) else Modifier.padding(start = 60.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            horizontalArrangement = if (message.isMe) Arrangement.End else Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!message.isMe) {
                AsyncImage(
                    model = "https://miro.medium.com/v2/resize:fit:1024/0*LGm5TYiNCOZAv2Xj.jpg",
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(42.dp)
                        .clip(CircleShape)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(MaterialTheme.shapes.small)
                    .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
                    .shadow(1.dp)
                    .background(if (message.isMe) Color(0xFFEEEEEE) else Color.White)
            ) {
                Column(
                    horizontalAlignment = if (message.isMe) Alignment.End else Alignment.Start,
                    modifier = Modifier
                        .padding(8.dp)
                ) {

                    Text(
                        text = message.content,
                        color = Color.Black,
                        textAlign = if (message.isMe) TextAlign.End else TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            if (message.isMe) {
                AsyncImage(
                    model = user.imageUrl,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(42.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}

fun generateDummyData(): List<Message> {
    val contents = listOf(
        Message(
            "Agrease Bot",
            "Halo, bagaimana perkembangan tanaman di ladang anda?",
        ),
//        Message(
//            "You",
//            "Saya khawatir karena hasil panen terakhir terlihat kurang optimal.",
//            true
//        ),
//        Message(
//            "Agrease Bot",
//            "Nah itu, ambil nasi pak vincent \uD83C\uDF59",
//            "10 minutes ago",
//        ),
//        Message(
//            "You",
//            "Dua lah! \uD83C\uDF59 \uD83C\uDF59",
//            "5 minutes ago",
//            true
//        ),
//        Message(
//            "Agrease Bot",
//            "Satu! ☝\uD83C\uDFFB",
//            "3 minutes ago",
//        ),
//        Message(
//            "You",
//            "Dua lahhhh ✌\uD83C\uDFFB",
//            "3 minutes ago",
//            true
//        ),
//        Message(
//            "Agrease Bot",
//            "Hm Ngeyel, gak bisa \uD83D\uDE20 \uD83D\uDC4A",
//            "2 minutes ago",
//        ),
//        Message(
//            "You",
//            "Kan saya kan, orang dua! ✌\uD83C\uDFFB \uD83D\uDE01",
//            "2 minutes ago",
//            true
//        ),
//        Message(
//            "Agrease Bot",
//            "Sama siapa!? \uD83E\uDD28",
//            "2 minutes ago",
//        ),
//        Message(
//            "You",
//            "Sama konci satu ☝\uD83C\uDFFB \uD83D\uDE0B",
//            "1 minute ago",
//            true
//        ),
//        Message(
//            "Agrease Bot",
//            "...",
//            "1 minute ago",
//        ),
    )

    val messages = mutableListOf<Message>()
    contents.map {
        messages.add(it)
    }

    return messages
}