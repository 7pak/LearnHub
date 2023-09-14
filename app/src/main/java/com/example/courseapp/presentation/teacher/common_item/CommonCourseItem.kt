package com.example.courseapp.presentation.teacher.common_item

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditAbleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    singleLine: Boolean = false,
    maxLines: Int = 1,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    TextField(
        modifier = modifier.padding(vertical = 15.dp),
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onBackground,
            unfocusedIndicatorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.primaryVariant,
            backgroundColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.primaryVariant
        ),
        placeholder = {

            Text(
                text = placeHolder,
                color = MaterialTheme.colors.primaryVariant,
                style = LocalTextStyle.current.copy(textAlign = TextAlign.Left),
                fontSize = 15.sp
            )

        },
        singleLine = singleLine,
        maxLines = maxLines,
        trailingIcon = trailingIcon
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenuEditable(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    items: Map<Int,String>,
    onItemSelected: (String) -> Unit,
    placeHolder: String,
    onDismissRequest: () -> Unit
) {
    var selectedItem by remember {
        mutableStateOf("")
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier.fillMaxWidth(0.7f)
    ) {

        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            placeholder = {
                Text(
                    text = selectedItem.ifEmpty { placeHolder },
                    color = MaterialTheme.colors.primaryVariant
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background
            ),
            textStyle = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primaryVariant)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
        ) {
            items.values.forEach { item ->
                DropdownMenuItem(onClick = {
                    selectedItem = item
                    onItemSelected(selectedItem)
                    onDismissRequest()
                }, modifier = Modifier.background(MaterialTheme.colors.primary)) {
                    Text(text = item, color = MaterialTheme.colors.primaryVariant)
                }
            }
        }
    }
}

@Composable
fun AddMedia(
    modifier: Modifier = Modifier,
    text:String,
    visualMediaType: ActivityResultContracts.PickVisualMedia.VisualMediaType = ActivityResultContracts.PickVisualMedia.ImageOnly,
    onRetrieveUri: (Uri?) -> Unit

) {

    var hasAddedPic by remember {
        mutableStateOf(false)
    }
    val photoPicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                onRetrieveUri(uri)
                hasAddedPic = true
            }
        }

    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

        Box(
            modifier = Modifier.fillMaxWidth(0.6f)
                .border(
                    width = 1.dp,
                    color = if (hasAddedPic) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primary,
                    shape = CircleShape
                )
                .clickable {
                    photoPicker.launch(PickVisualMediaRequest(visualMediaType))
                }, contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(5.dp)
            ) {

                    Text(
                        text = if (hasAddedPic) "Added" else text,
                        textAlign = TextAlign.Center,
                        style =  MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primaryVariant) ,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                    )
                    Icon(
                        imageVector = if (hasAddedPic) Icons.Default.DoneOutline else Icons.Default.AddCircle,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colors.primaryVariant
                    )
            }
        }
    }
}
