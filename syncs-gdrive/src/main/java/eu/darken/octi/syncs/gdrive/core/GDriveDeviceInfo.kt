package eu.darken.octi.syncs.gdrive.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GDriveDeviceInfo(
    @SerialName("version") val version: String? = null,
    @SerialName("platform") val platform: String? = null,
    @SerialName("label") val label: String? = null,
)
