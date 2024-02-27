package com.example.courseapp.authentication.presentation.common_item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.courseapp.navigation.UserType


@Composable
fun UserTypeButtons(selectedType: UserType, onUserTypeSelected: (UserType) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        UserTypeButton(
            text = "As Student",
            userType = UserType.STUDENT,
            selectedType = selectedType,
            onUserTypeSelected = onUserTypeSelected
        )
        UserTypeButton(
            text = "As Teacher",
            userType = UserType.TEACHER,
            selectedType = selectedType,
            onUserTypeSelected = onUserTypeSelected
        )
    }
}

@Composable
fun UserTypeButton(
    text: String,
    userType: UserType,
    selectedType: UserType,
    onUserTypeSelected: (UserType) -> Unit
) {
    OutlinedButton(
        onClick = { onUserTypeSelected(userType) },
        modifier = Modifier
            .width(130.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (selectedType == userType) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primaryVariant.copy(
                alpha = 0.3f
            ),
            backgroundColor = Color.Transparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (selectedType == userType) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primaryVariant.copy(
                alpha = 0.3f
            )
        ),
        shape = RoundedCornerShape(25.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.body1
        )
    }
}



@Composable
fun OutlinedTextFieldWithLabel(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(25.dp),
        modifier = modifier
            .padding(vertical = 5.dp)
            .height(80.dp),
        label = {
            Text(
                text = label,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .padding(start = 15.dp)
                    .padding(vertical = 15.dp)
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.onBackground,
            unfocusedLabelColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.primary,
            focusedLabelColor = MaterialTheme.colors.primaryVariant,
            focusedBorderColor = MaterialTheme.colors.primaryVariant,
            cursorColor = MaterialTheme.colors.primaryVariant
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    )
}


@Composable
fun OutlinedTextFieldWithLabelAndValidation(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(25.dp),
        modifier = modifier
            .padding(vertical = 5.dp)
            .height(80.dp),
        label = {
            Text(
                text = label, color = MaterialTheme.colors.primaryVariant, modifier = Modifier
                    .padding(start = 15.dp)
                    .padding(vertical = 15.dp)
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.onBackground,
            unfocusedBorderColor = MaterialTheme.colors.primary,
            focusedBorderColor = MaterialTheme.colors.primaryVariant,
            unfocusedLabelColor = MaterialTheme.colors.primary,
            focusedLabelColor = MaterialTheme.colors.primaryVariant,
            cursorColor = MaterialTheme.colors.primaryVariant
        ),
        trailingIcon = trailingIcon,
        isError = isError,
        singleLine = true,
        textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onBackground),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )

    if (isError) {
        Text(
            text = errorMessage,
            fontSize = 16.sp,
            color = Color.Red,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}

@Composable
fun ConfirmButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean,
    buttonWidth:Dp = 300.dp,
    buttonHeight:Dp = 70.dp
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(buttonWidth)
            .height(buttonHeight),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (isEnabled) MaterialTheme.colors.onBackground else MaterialTheme.colors.primary.copy(
                alpha = 0.3f
            ),
            backgroundColor = if (isEnabled) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primary.copy(
                alpha = 0.3f
            ),
            disabledContentColor = MaterialTheme.colors.primary
        ),
        shape = RoundedCornerShape(25.dp),
        enabled = isEnabled
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6
        )
    }
}


@Composable
fun HyperLinkClickable(
    modifier: Modifier = Modifier,
    text: String,
    linkText: String,
    linkColor: Color,
    clickableText: () -> Unit
) {
    val tag = "Link"
    val annotatedString = buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = MaterialTheme.colors.onBackground,
                fontSize = MaterialTheme.typography.body2.fontSize
            )
        ) {
            append(text)
        }
        pushStringAnnotation(tag = tag, annotation = linkText)
        withStyle(
            style = SpanStyle(
                color = linkColor,
                fontSize = MaterialTheme.typography.body2.fontSize
            )
        ) {
            append(linkText)
        }
        pop()
    }

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.let { annotation ->
                if (annotation.tag == tag) {
                    clickableText()
                }
            }
        }
    )
}

@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
) {
        Box(
            modifier = modifier.fillMaxSize()
                .background(Color.Black.copy(alpha = ContentAlpha.disabled)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colors.primaryVariant)
        }

}
