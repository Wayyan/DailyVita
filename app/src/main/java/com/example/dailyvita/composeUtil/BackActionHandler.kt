package com.example.dailyvita.composeUtil

import android.os.Build
import android.window.OnBackInvokedCallback
import android.window.OnBackInvokedDispatcher
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.fragment.app.Fragment
import com.example.dailyvita.feature.MainActivity

@Composable
fun BackActionHandler(
    handleBackHandler: Boolean = true,
    priority: Int = OnBackInvokedDispatcher.PRIORITY_DEFAULT,
    callback: () -> Unit = {}
) {
    BackHandler {
        callback()
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        BackInvokeHandler {
            callback()
        }
    }
}

@Composable
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun BackInvokeHandler(
    handleBackHandler: Boolean = true,
    priority: Int = OnBackInvokedDispatcher.PRIORITY_DEFAULT,
    callback: () -> Unit = {}
) {
    val backInvokedCallback = remember {
        OnBackInvokedCallback {
            callback()
        }
    }

    val activity = when (LocalLifecycleOwner.current) {
        is MainActivity -> LocalLifecycleOwner.current as MainActivity
        is Fragment -> (LocalLifecycleOwner.current as Fragment).requireActivity() as MainActivity
        else -> {
            val context = LocalContext.current
            if (context is MainActivity) {
                context
            } else {
                throw IllegalStateException("LocalLifecycleOwner is not MainActivity or Fragment")
            }
        }
    }

    if (handleBackHandler) {
        activity.onBackInvokedDispatcher.registerOnBackInvokedCallback(
            priority,
            backInvokedCallback
        )
    }

    LaunchedEffect(handleBackHandler) {
        if (!handleBackHandler) {
            activity.onBackInvokedDispatcher.unregisterOnBackInvokedCallback(backInvokedCallback)
        }
    }

    DisposableEffect(activity.lifecycle, activity.onBackInvokedDispatcher) {
        onDispose {
            activity.onBackInvokedDispatcher.unregisterOnBackInvokedCallback(backInvokedCallback)
        }
    }
}