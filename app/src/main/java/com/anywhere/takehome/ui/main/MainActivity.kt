package com.anywhere.takehome.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.anywhere.takehome.databinding.ActivityMainBinding
import com.anywhere.takehome.ui.util.SlidingPaneLayoutOnBackPressedCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var windowInfoTracker: WindowInfoTracker

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        windowInfoTracker = WindowInfoTracker.getOrCreate(this@MainActivity)

        onWindowLayoutInfoChange()
        viewModel.load()

        onBackPressedDispatcher.addCallback(this,
            SlidingPaneLayoutOnBackPressedCallback(binding.slidingPaneLayout))
    }


    private fun onWindowLayoutInfoChange() {

        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                windowInfoTracker.windowLayoutInfo(this@MainActivity)
                    .collect { value ->
                        updateUI(value)
                    }
            }
        }
    }

    private fun updateUI(newLayoutInfo: WindowLayoutInfo) {
        if (newLayoutInfo.displayFeatures.isNotEmpty()) {
            // If the device is in a dual-screen posture, the SlidingPaneLayout is slideable.
            binding.slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_UNLOCKED
        }
        // Single screen device
        else {
            binding.slidingPaneLayout.closePane()
            // If the device is in a single-screen posture, the SlidingPaneLayout is not slideable.
            binding.slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED
        }
    }

    fun openPane() {
        binding.slidingPaneLayout.openPane()
    }
}
