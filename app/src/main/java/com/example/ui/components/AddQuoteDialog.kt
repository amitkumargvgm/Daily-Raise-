package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.QuoteCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuoteDialog(
    onDismiss: () -> Unit,
    onAddQuote: (text: String, author: String, category: String, hindiText: String?, language: String) -> Unit
) {
    var quoteTextEn by remember { mutableStateOf("") }
    var quoteTextHi by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(QuoteCategory.SUCCESS) }
    var expanded by remember { mutableStateOf(false) }

    val categories = QuoteCategory.entries.filter { it != QuoteCategory.ALL }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.testTag("add_quote_dialog"),
        shape = RoundedCornerShape(20.dp),
        title = {
            Text(
                text = "Add Inspiring Quote",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Add quotes in English, Hindi, or both! They will be saved to your local library.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedTextField(
                    value = quoteTextEn,
                    onValueChange = { quoteTextEn = it },
                    label = { Text("Quote in English") },
                    placeholder = { Text("e.g., Rise and shine with purposeful action.") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_quote_en"),
                    shape = RoundedCornerShape(12.dp),
                    minLines = 2,
                    maxLines = 4
                )

                OutlinedTextField(
                    value = quoteTextHi,
                    onValueChange = { quoteTextHi = it },
                    label = { Text("विचार हिंदी में (Hindi Quote - Optional)") },
                    placeholder = { Text("उदा. उठो, जागो और अपने सपनों को सच करो।") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_quote_hi"),
                    shape = RoundedCornerShape(12.dp),
                    minLines = 2,
                    maxLines = 4
                )

                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Author / Speaker") },
                    placeholder = { Text("e.g., Swami Vivekananda / Anonymous") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_quote_author"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                // Category selector
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = "${selectedCategory.titleEn} (${selectedCategory.titleHi})",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text("${cat.titleEn} (${cat.titleHi})") },
                                onClick = {
                                    selectedCategory = cat
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val lang = if (quoteTextEn.isNotBlank() && quoteTextHi.isNotBlank()) "BOTH"
                    else if (quoteTextHi.isNotBlank()) "HI"
                    else "EN"

                    onAddQuote(
                        quoteTextEn,
                        author,
                        selectedCategory.id,
                        quoteTextHi.ifBlank { null },
                        lang
                    )
                },
                enabled = quoteTextEn.isNotBlank() || quoteTextHi.isNotBlank(),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.testTag("save_custom_quote_button")
            ) {
                Text("Save Quote")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Cancel")
            }
        }
    )
}
