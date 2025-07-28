package com.example.orb_ed.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.orb_ed.presentation.theme.GreyHintColor
import com.example.orb_ed.presentation.theme.PrimaryColor


@Preview(showBackground = true)
@Composable
fun CategoryTabsPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        var selectedTabIndex = 0
        CategoryTabs(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it },
            modifier = Modifier.fillMaxWidth(),
            tabs = listOf("All Courses (50)", "Enrolled (0)", "Unenrolled (0)")
        )
    }

}

@Composable
fun CategoryTabs(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    tabs: List<String>
) {
    ScrollableTabRow(
        edgePadding = 0.dp,
        selectedTabIndex = selectedTabIndex,
        modifier = modifier
            .selectableGroup(),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary,
        indicator =
            @Composable { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 1.dp
                )
            },
        divider = {}
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = selectedTabIndex == index

            Tab(
                modifier = Modifier.padding(vertical = 2.dp),
                selected = isSelected,
                onClick = { onTabSelected(index) },
                selectedContentColor = PrimaryColor,
                unselectedContentColor = GreyHintColor
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = title,
                        style = if (isSelected) MaterialTheme.typography.titleLarge.copy(
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        ) else MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 12.sp,
                            color = GreyHintColor
                        ),
                    )
                }

            }
        }
    }
}
