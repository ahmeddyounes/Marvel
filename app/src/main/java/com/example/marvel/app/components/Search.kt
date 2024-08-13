package com.example.marvel.app.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.marvel.R

@Composable
fun SearchPlaceholder(
    placeholder: String = stringResource(id = R.string.search_placeholder),
    onClick: () -> Unit = {}
) {
    TextField(
        value = "",
        onValueChange = { },
        colors = OutlinedTextFieldDefaults.colors(
            disabledContainerColor = Color.White,
            disabledBorderColor = Color.Transparent,
            disabledPlaceholderColor = Color.Gray,
            disabledLeadingIconColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .border(
                width = 1.dp, // Border thickness
                color = Color.DarkGray, // Border color
                shape = RoundedCornerShape(8.dp) // Border shape (rounded corners)
            ),
        enabled = false,
        placeholder = { Text(placeholder) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        singleLine = true,
    )
}

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit = {},
    onClear: () -> Unit = {}
) {
    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    // Automatically request focus when the composable is first launched
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val text = remember {
        mutableStateOf(value)
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = text.value,
            onValueChange = { v ->
                text.value = v
                onValueChange(v)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                focusedBorderColor = Color.Transparent,
                focusedPlaceholderColor = Color.Gray,
                unfocusedContainerColor = Color.White,
                unfocusedBorderColor = Color.Transparent,
                unfocusedPlaceholderColor = Color.Gray,
                unfocusedTrailingIconColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedTrailingIconColor = Color.Black
            ),

            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester)
                .border(
                    width = 1.dp, // Border thickness
                    color = Color.DarkGray, // Border color
                    shape = RoundedCornerShape(8.dp) // Border shape (rounded corners)
                ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            placeholder = { Text(stringResource(id = R.string.search_text)) },
            trailingIcon = {
                IconButton(onClick = onClear) {
                    Icon(Icons.Filled.Close, contentDescription = "Clear")
                }
            },
            singleLine = true,
        )

    }
}