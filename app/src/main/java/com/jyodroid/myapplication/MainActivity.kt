package com.jyodroid.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jyodroid.myapplication.data.SampleData
import com.jyodroid.myapplication.data.model.Message
import com.jyodroid.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
//                MessageCard(Message("Android", "Hello you"))
                Conversation(messages = SampleData.conversationSample)
            }
        }
    }

    @Composable
    fun MessageCard(message: Message) {
        //Column helps you arrange view elements vertically
        //Rows helps you arrange view elements horizontally
        //Box helps you stack elements
        //To decorate or configure a composable we use a modifier
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                    .background(color = Color.Blue)
            )
            Spacer(Modifier.width(8.dp))

            // We keep track if the message is expanded or not in this
            // variable
            var isExpanded by remember { mutableStateOf(false) }

            // surfaceColor will be updated gradually from one color to the other
            val surfaceColor by animateColorAsState(if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.secondaryVariant)

            // We toggle the isExpanded variable when we click on this Column
            Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
                Text(
                    text = message.author,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(modifier = Modifier.width(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    elevation = 1.dp,
                    // surfaceColor color will be changing gradually from primary to surface
                    color = surfaceColor
                ) {
                    Text(
                        text = message.body,
                        // animateContentSize will change the Surface size gradually
//                        modifier = Modifier.padding(all = 4.dp),
                        modifier = Modifier
                            .animateContentSize()
                            .padding(1.dp),
                        // If the message is expanded, we display all its content
                        // otherwise we only display the first line
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }

    //Must be on a composite function that does not take in parameters
    @Preview(name = "Light mode")
    @Preview(
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true,
        name = "Dark Mode"
    )
    @Composable
    fun PreviewMessageCard() {
        MyApplicationTheme {
            MessageCard(Message("Android", "Hello you"))
        }
    }

    //LazyColumn and LazyList: renders only the elements that are visible on screen
    @Composable
    fun Conversation(messages: List<Message>) {
        LazyColumn {
            items(messages) { message -> MessageCard(message) }
        }
    }

    @Preview(name = "Conversation")
    @Composable
    fun PreviewConversation() {
        MyApplicationTheme {
            Conversation(messages = SampleData.conversationSample)
        }
    }

    // Composable functions can store local state in memory by using "remember" and track changes to
    // the value passed to "mutableStateOf". recomposition:  https://developer.android.com/jetpack/compose/mental-model#recomposition
}