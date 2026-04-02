package eu.darken.octi.main.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ChevronRight
import androidx.compose.material.icons.twotone.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.darken.octi.R
import eu.darken.octi.sync.core.ConnectorId
import eu.darken.octi.sync.core.ConnectorIssue
import eu.darken.octi.sync.core.IssueSeverity

@Composable
fun IssuesSummarySheet(
    severity: IssueSeverity,
    issues: List<ConnectorIssue>,
    connectorLabels: Map<ConnectorId, String>,
    onConnectorClick: (ConnectorId) -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            val title = when (severity) {
                IssueSeverity.ERROR -> stringResource(R.string.sync_issues_errors_title)
                IssueSeverity.WARNING -> stringResource(R.string.sync_issues_warnings_title)
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(16.dp))

            val context = LocalContext.current
            val byConnector = issues.groupBy { it.connectorId }
            byConnector.forEach { (connectorId, connectorIssues) ->
                val label = connectorLabels[connectorId] ?: connectorId.idString.take(12)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onConnectorClick(connectorId) }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.Warning,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = when (severity) {
                            IssueSeverity.ERROR -> MaterialTheme.colorScheme.error
                            IssueSeverity.WARNING -> MaterialTheme.colorScheme.tertiary
                        },
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.titleSmall,
                        )
                        val summary = connectorIssues
                            .groupBy { it.label.get(context) }
                            .map { (typeLabel, group) -> "${group.size} $typeLabel" }
                            .joinToString(", ")
                        Text(
                            text = summary,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Icon(
                        imageVector = Icons.TwoTone.ChevronRight,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
