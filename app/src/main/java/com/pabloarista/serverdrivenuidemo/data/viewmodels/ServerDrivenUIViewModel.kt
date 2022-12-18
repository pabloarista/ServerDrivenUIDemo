package com.pabloarista.serverdrivenuidemo.data.viewmodels

import com.pabloarista.serverdrivenuidemo.shared.data.models.Component
import com.pabloarista.serverdrivenuidemo.data.repositories.ServerDrivenUIRepository
import kotlinx.coroutines.*

class ServerDrivenUIViewModel(private val repository: ServerDrivenUIRepository
                            , private val dispatcher: CoroutineDispatcher = Dispatchers.Main) {
    //used for coroutines, so that we don't block the main UI thread
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + this.viewModelJob)
    //boolean flag that will be used to show if we are busy or NOT in the UI
    var isBusy: Boolean = false
        //setter is private since this will be set internally to this view model
        private set(value) { field = value }
    //used to determine if a "Load More" should be displayed
    var hasMoreResults: Boolean = true
        private set(value) { field = value }
    var callback: ((Component?) -> Unit)? = null
    val rooApiPath = ""
    val testApiPath = "test"
    val sampleApiPath = "sample"

    fun getInitialView() {
        getView(rooApiPath)
    }

    fun getView(apiPath: String) {
        if(isBusy) return

        isBusy = true
        //do NOT block the main UI thread
        this.viewModelScope.launch {
            val component = repository.getServerDrivenUi(apiPath)
            isBusy = false
            val callback = callback?: return@launch
            withContext(dispatcher) {
                callback(component)
            }
        }//launch
    }
}