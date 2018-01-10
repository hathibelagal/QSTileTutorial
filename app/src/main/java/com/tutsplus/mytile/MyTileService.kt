package com.tutsplus.mytile

import android.content.Context
import android.content.Intent
import android.os.VibrationEffect
import android.os.Vibrator
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlin.concurrent.thread

class MyTileService: TileService() {
    override fun onTileAdded() {
        super.onTileAdded()
        qsTile.state = Tile.STATE_INACTIVE
        qsTile.updateTile()
    }

    override fun onClick() {
        super.onClick()
        if(qsTile.state == Tile.STATE_INACTIVE) {
            // Turn on
            qsTile.state = Tile.STATE_ACTIVE
            startVibrating()
        } else {
            // Turn off
            qsTile.state = Tile.STATE_INACTIVE
            stopVibrating()
        }
        // Update looks
        qsTile.updateTile()
    }

    fun startVibrating() {
        launch {
            while(qsTile.state == Tile.STATE_ACTIVE) {
                (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
                        .vibrate(1000)
                delay(1000)
            }
        }
    }

    fun stopVibrating() {
        (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).cancel()
    }
}
