package com.example.myapitest.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.myapitest.R
import com.google.accompanist.permissions.*

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(
    permission: String,
    rationaleMessage: String,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(permission)
    LaunchedEffect(permissionState.status) {
        when {
            permissionState.status.isGranted -> {
                onPermissionGranted()
            }
//            permissionState.status.shouldShowRationale -> {
//                Toast.makeText(context, rationaleMessage, Toast.LENGTH_LONG).show()
//                permissionState.launchPermissionRequest()
//            }
//            permissionState.status.isPermanentlyDenied() -> {
//                Toast.makeText(
//                    context,
//                    "",
//                    Toast.LENGTH_LONG
//                ).show()
//
//                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
//                    data = Uri.fromParts("package", context.packageName, null)
//                }
//                context.startActivity(intent)
//            }
            else -> {
                permissionState.launchPermissionRequest()
        }

        }
    }
}

//@OptIn(ExperimentalPermissionsApi::class)
//fun PermissionStatus.isPermanentlyDenied(): Boolean {
//    return this is PermissionStatus.Denied && !this.shouldShowRationale
//}
