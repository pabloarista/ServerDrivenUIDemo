package com.pabloarista.serverdrivenuidemo

import android.app.Application
import com.pabloarista.serverdrivenuidemo.data.repositories.ServerDrivenUIRepository
import com.pabloarista.serverdrivenuidemo.data.viewmodels.ServerDrivenUIViewModel

class ServerDrivenUIApplication: Application() {
    companion object {
        val serverDrivernUIViewModel = ServerDrivenUIViewModel(ServerDrivenUIRepository())
    }
}