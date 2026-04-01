package eu.darken.octi.sync.core

import eu.darken.octi.common.ca.CaString
import eu.darken.octi.common.ca.caString
import eu.darken.octi.sync.R
import java.time.Instant

sealed interface CommonIssue : ConnectorIssue {

    data class StaleDevice(
        override val connectorId: ConnectorId,
        override val deviceId: DeviceId,
        val lastSeen: Instant,
    ) : CommonIssue {
        override val severity: IssueSeverity = IssueSeverity.WARNING
        override val label: CaString = caString {
            val period = StalenessUtil.formatStalePeriod(it, lastSeen)
            it.getString(R.string.sync_device_stale_warning_text, period)
        }
        override val countLabel: CaString = caString { it.getString(R.string.sync_issues_type_stale_device) }
    }

    data class OutdatedVersion(
        override val connectorId: ConnectorId,
        override val deviceId: DeviceId,
        val version: String,
    ) : CommonIssue {
        override val severity: IssueSeverity = IssueSeverity.WARNING
        override val label: CaString = caString { it.getString(R.string.sync_device_outdated_version_warning, version) }
        override val countLabel: CaString = caString { it.getString(R.string.sync_issues_type_outdated_version) }
    }

    data class ClockSkew(
        override val connectorId: ConnectorId,
        override val deviceId: DeviceId,
    ) : CommonIssue {
        override val severity: IssueSeverity = IssueSeverity.WARNING
        override val label: CaString = caString { it.getString(R.string.sync_device_clock_skew_warning_text) }
        override val countLabel: CaString = caString { it.getString(R.string.sync_issues_type_clock_skew) }
    }
}
