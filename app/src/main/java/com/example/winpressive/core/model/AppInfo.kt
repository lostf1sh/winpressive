package com.example.winpressive.core.model

import android.graphics.drawable.Drawable

/**
 * Represents an installed application.
 */
data class AppInfo(
    val packageName: String,
    val label: String,
    val activityName: String,
    // Icon is usually loaded on demand via Coil, but we might cache a Drawable or key here if needed.
    // We stick to primitives for serialization.
    val user: Long = 0 // For multi-user support (future proofing)
)
