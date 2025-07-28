package com.example.orb_ed.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.orb_ed.R
import com.example.orb_ed.presentation.theme.LightPurpleBackgroundColor
import com.example.orb_ed.presentation.theme.PrimaryColor

@Preview(showBackground = true)
@Composable()
fun SearchBarPreview() {
    Box(
        Modifier
            .fillMaxSize()
            .background(LightPurpleBackgroundColor)
            .padding(20.dp)
    ) {
        SearchBar(
            query = "",
            onQueryChange = { },
            onSearch = { /* TODO: Handle search */ },
            onFilterClick = { /* TODO: Handle filter */ },
            modifier = Modifier.fillMaxWidth()
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search Here...",
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {


        CustomTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .shadow(2.dp, RoundedCornerShape(8.dp)),
            hint = placeholder,
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .background(
                            color = PrimaryColor,
                            shape = RoundedCornerShape(5.dp)
                        )
                ) {
                    Icon(
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.Center),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                        contentDescription = "Search Button",
                        tint = White
                    )
                }
            },
            borderColor = Transparent,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch() }
            ),
            paddingValues = PaddingValues(12.dp)
        )

        Box(
            modifier = Modifier
                .shadow(2.dp, RoundedCornerShape(8.dp))
                .fillMaxHeight()
                .aspectRatio(1f)
                .background(
                    color = White,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onFilterClick() }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(25.dp)
                    .background(
                        color = PrimaryColor,
                        shape = RoundedCornerShape(5.dp)
                    )
            ) {
                Icon(
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.Center),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_filter),
                    contentDescription = "Search Button",
                    tint = White
                )
            }

        }
    }
}
