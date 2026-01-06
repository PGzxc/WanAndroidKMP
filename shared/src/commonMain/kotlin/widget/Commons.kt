package widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LabelTextButton(
        text: String,
        modifier: Modifier = Modifier,
        isSelect: Boolean = true,
        specTextColor: Color? = null,
        cornerValue: Dp = 25.dp / 2,
        isLoading: Boolean = false,
        onClick: (() -> Unit)? = null,
        onLongClick: (() -> Unit)? = null
) {
    Text(
        text = text,
        modifier = modifier
            .height(28.dp)
            .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(3.dp) )
            //.background(color = if (isSelect && !isLoading) themeColor else PurpleGrey40,)
            .padding(horizontal = 5.dp, vertical = 3.dp)
            .combinedClickable(
                enabled = !isLoading,
                onClick = { onClick?.invoke() },
                onLongClick = { onLongClick?.invoke() }
            )
            //.placeholder(visible = isLoading, color = PurpleGrey40)
        ,
        fontSize = 13.sp,
        textAlign = TextAlign.Center,
        //color = specTextColor ?: if (isSelect) White else PurpleGrey40,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

@Composable
fun ListTitle(
        modifier: Modifier = Modifier,
        title: String,
        subTitle: String = "",
        isLoading: Boolean = false,
        onSubtitleClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(color = Color.Gray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = modifier.padding(horizontal = 10.dp),
            fontSize = 16.sp,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun TextInput(
        text: String,
        label: String = "",
        leadingIcon: ImageVector  ,
        keyboardType: KeyboardType = KeyboardType.Text,
        isPassword: Boolean = false,
        onValueChanged: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(!isPassword) }
    var hasFocus by remember { mutableStateOf(false) }

    Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .wrapContentHeight()
    ) {
        TextField(
            value = text,
            onValueChange = onValueChanged,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    hasFocus = it.hasFocus
                },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            label = {
                androidx.compose.material3.Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = hasFocus && text.trim().isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Row {
                        if (isPassword) {
                            // Temporarily replaced image with Text due to painterResource issues
                            Text(
                                text = if (passwordVisible) "👁" else "👁‍🗨",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(25.dp)
                                    .clickable{
                                        passwordVisible = !passwordVisible
                                    },
                            )
                        }
                        // Temporarily replaced image with Text due to painterResource issues
                        Text(
                            text = "×",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    onValueChanged("")
                                }
                        )
                    }
                }
            },
            shape = RectangleShape,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        )

        Divider(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        )
    }
}



