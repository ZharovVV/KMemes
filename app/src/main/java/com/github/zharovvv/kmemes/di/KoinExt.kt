package com.github.zharovvv.kmemes.di

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.zharovvv.kmemes.di.ui.KoinViewModelFactory

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.koinViewModels(
    noinline extrasProducer: (() -> CreationExtras)? = null
): Lazy<VM> = viewModels(
    extrasProducer = extrasProducer,
    factoryProducer = { KoinViewModelFactory }
)

@Composable
inline fun <reified VM : ViewModel> koinComposeViewModel(): VM =
    viewModel(factory = KoinViewModelFactory)

