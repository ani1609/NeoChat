package com.bera.inclusive_chat_app.presentation.chat

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.bera.inclusive_chat_app.ui.theme.Pink40
import com.bera.inclusive_chat_app.ui.theme.Purple40
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    navigateBackToProfile: () -> Unit,
) {
    val messages = viewModel.messages
    val userType = viewModel.userType
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var chatBoxValue by rememberSaveable { mutableStateOf("") }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            uris.forEach { uri ->
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                coroutineScope.launch {
                    context.contentResolver.takePersistableUriPermission(uri, flag)
                    context.contentResolver.openInputStream(uri)?.use { inputStream ->
                        // Create a temporary file and copy the content of the InputStream to it
                        val tempFile = File.createTempFile("pickedImage", null, context.cacheDir)
                        tempFile.outputStream().use { fileOut ->
                            inputStream.copyTo(fileOut)
                        }
                        viewModel.sendFile(tempFile, FileType.Image)
                    }
                }
            }
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { navigateBackToProfile() },
                    model = viewModel.user.photoUrl,
                    loading = { CircularProgressIndicator(strokeCap = StrokeCap.Round) },
                    contentDescription = "profile photo"
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = chatBoxValue,
                    onValueChange = { newText ->
                        chatBoxValue = newText
                    },
                    placeholder = {
                        Text(text = "Type something")
                    },
                    leadingIcon = {
                        IconButton(
                            onClick = {
                                photoPicker.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }
                        ) {
                            Icon(imageVector = Icons.Rounded.Image, contentDescription = "image")
                        }
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { viewModel.sendMessage(chatBoxValue) }
                        ) {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = null)
                        }
                    },
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                IconButton(
                    onClick = {
                        if (!viewModel.recordState)
                            viewModel.onRecordAudio()
                        else {
                            viewModel.onStopRecord()
                        }
                    },
                    colors = IconButtonDefaults
                        .iconButtonColors(
                            Purple40
                        )
                ) {
                    if (!viewModel.recordState)
                        Icon(
                            imageVector = Icons.Filled.Mic,
                            contentDescription = null,
                            tint = Color.White
                        )
                    else
                        Icon(
                            imageVector = Icons.Filled.Stop,
                            contentDescription = null,
                            tint = Color.White
                        )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(top = 4.dp),
            reverseLayout = true,
        ) {
            items(items = messages, key = { it.id }) { message ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement =
                    if (message.isFromMe) {
                        Arrangement.End
                    } else {
                        Arrangement.Start
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                            .background(
                                color = if (message.isFromMe) {
                                    Purple40
                                } else {
                                    Pink40
                                },
                                shape = RoundedCornerShape(
                                    topStart = 24f,
                                    topEnd = 24f,
                                    bottomStart = if (message.isFromMe) 24f else 0f,
                                    bottomEnd = if (message.isFromMe) 0f else 24f
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .widthIn(120.dp, 240.dp)
                                .padding(8.dp)
                        ) {
                            viewModel.findUser(message.uid)?.also {
                                Spacer(modifier = Modifier.height(6.dp))
                                if (it.name?.isNotEmpty() == true) {
                                    Text(
                                        text = it.name.substringBefore(" "),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                            message.photoUrl.also {
                                if (it.isNotEmpty()) {
                                    val colorFilter = ColorFilter.colorMatrix(
                                        ColorMatrix(
                                            when (userType) {
                                                UserType.Protanopia -> floatArrayOf(
                                                    0.567F, 0.433F, 0F, 0F, 0F,
                                                    0.558F, 0.442F, 0F, 0F, 0F,
                                                    0F, 0.242F, 0.758F, 0F, 0F,
                                                    0F, 0F, 0F, 1F, 0F,
                                                    0F, 0F, 0F, 0F, 1F
                                                )

                                                UserType.Protanomaly -> floatArrayOf(
                                                    0.817F, 0.183F, 0F, 0F, 0F,
                                                    0.333F, 0.667F, 0F, 0F, 0F,
                                                    0F, 0.125F, 0.875F, 0F, 0F,
                                                    0F, 0F, 0F, 1F, 0F,
                                                    0F, 0F, 0F, 0F, 1F
                                                )

                                                UserType.Deuteranopia -> floatArrayOf(
                                                    0.625F, 0.375F, 0F, 0F, 0F,
                                                    0.7F, 0.3F, 0F, 0F, 0F,
                                                    0F, 0.3F, 0.7F, 0F, 0F,
                                                    0F, 0F, 0F, 1F, 0F,
                                                    0F, 0F, 0F, 0F, 1F
                                                )

                                                UserType.Deuteranomaly -> floatArrayOf(
                                                    0.8F, 0.2F, 0F, 0F, 0F,
                                                    0.258F, 0.742F, 0F, 0F, 0F,
                                                    0F, 0.142F, 0.858F, 0F, 0F,
                                                    0F, 0F, 0F, 1F, 0F,
                                                    0F, 0F, 0F, 0F, 1F
                                                )

                                                UserType.Tritanopia -> floatArrayOf(
                                                    0.95F, 0.05F, 0F, 0F, 0F,
                                                    0F, 0.433F, 0.567F, 0F, 0F,
                                                    0F, 0.475F, 0.525F, 0F, 0F,
                                                    0F, 0F, 0F, 1F, 0F,
                                                    0F, 0F, 0F, 0F, 1F
                                                )

                                                UserType.Tritanomaly -> floatArrayOf(
                                                    0.967F, 0.033F, 0F, 0F, 0F,
                                                    0F, 0.733F, 0.267F, 0F, 0F,
                                                    0F, 0.183F, 0.817F, 0F, 0F,
                                                    0F, 0F, 0F, 1F, 0F,
                                                    0F, 0F, 0F, 0F, 1F
                                                )

                                                UserType.Achromatopsia -> floatArrayOf(
                                                    0.299F, 0.587F, 0.114F, 0F, 0F,
                                                    0.299F, 0.587F, 0.114F, 0F, 0F,
                                                    0.299F, 0.587F, 0.114F, 0F, 0F,
                                                    0F, 0F, 0F, 1F, 0F,
                                                    0F, 0F, 0F, 0F, 1F
                                                )

                                                UserType.Achromatomaly -> floatArrayOf(
                                                    0.618F, 0.32F, 0.062F, 0F, 0F,
                                                    0.163F, 0.775F, 0.062F, 0F, 0F,
                                                    0.163F, 0.32F, 0.516F, 0F, 0F,
                                                    0F, 0F, 0F, 1F, 0F,
                                                    0F, 0F, 0F, 0F, 1F
                                                )

                                                else -> floatArrayOf(
                                                    1F, 0F, 0F, 0F, 0F,
                                                    0F, 1F, 0F, 0F, 0F,
                                                    0F, 0F, 1F, 0F, 0F,
                                                    0F, 0F, 0F, 1F, 0F,
                                                    0F, 0F, 0F, 0F, 1F
                                                )
                                            }
                                        )
                                    )
                                    SubcomposeAsyncImage(
                                        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                                        model = it,
                                        loading = {
                                            CircularProgressIndicator(strokeCap = StrokeCap.Round)
                                        },
                                        error = {
                                            Text(
                                                text = "Resend image",
                                                fontStyle = FontStyle.Italic,
                                                fontSize = 12.sp,
                                                color = Color.White
                                            )
                                        },
                                        contentDescription = "image",
                                        colorFilter = colorFilter
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                            message.audioPath.also {
                                if (it.isNotEmpty()) {
                                    IconButton(onClick = {
                                        if (viewModel.playerState) {
                                            viewModel.onStopPlay()
                                        } else {
                                            viewModel.onPlayAudio(it)
                                        }
                                    }) {
                                        if (viewModel.playerState) {
                                            Icon(
                                                imageVector = Icons.Rounded.Stop,
                                                tint = Color.White,
                                                contentDescription = "stop audio"
                                            )
                                        } else {
                                            Icon(
                                                imageVector = Icons.Rounded.PlayArrow,
                                                tint = Color.White,
                                                contentDescription = "stop audio"
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                            message.text.also {
                                if (it.isNotEmpty()) {
                                    when (userType) {
                                        UserType.Blindness -> Button(onClick = {
                                            viewModel.textToSpeech(
                                                it,
                                                context
                                            )
                                        }) {
                                            Text(text = "Hear text", color = Color.White)
                                        }
                                        else -> Text(
                                            text = it,
                                            color = Color.White,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                            Text(
                                modifier = Modifier.align(Alignment.End),
                                text = message.formattedTime,
                                color = Color.White,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Light,
                                fontSize = 8.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
