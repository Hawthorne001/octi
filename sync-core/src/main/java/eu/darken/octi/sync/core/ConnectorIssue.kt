package eu.darken.octi.sync.core

import eu.darken.octi.common.ca.CaString

enum class IssueSeverity { WARNING, ERROR }

interface ConnectorIssue {
    val connectorId: ConnectorId
    val deviceId: DeviceId
    val severity: IssueSeverity
    val label: CaString
    val countLabel: CaString
}
