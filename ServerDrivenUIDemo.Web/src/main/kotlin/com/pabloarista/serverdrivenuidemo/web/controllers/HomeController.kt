package com.pabloarista.serverdrivenuidemo.web.controllers

import com.pabloarista.serverdrivenuidemo.shared.data.models.Component
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.io.*


@RestController
class HomeController {
    @GetMapping("/")
    fun index(): Component? = getComponent("main.xml")

    @GetMapping("/test")
    fun test(): Component? = getComponent("test.xml")

    @GetMapping("/sample")
    fun sample(): Component = Component.testData()

    private fun getComponent(fileName: String): Component? {
        var component: Component?
        try {
            val rootPath = File("").absoluteFile
            //instead of grabbing it from the resource file we grab it directly from the file system.
            // This way the file can be modified and the web server does NOT need to be restarted.
            val completePath = "$rootPath/ServerDrivenUIDemo.Web/src/main/resources/$fileName"
            FileInputStream(completePath).use { inputStream ->
                val serializer: Serializer = Persister()
                component = serializer.read(Component::class.java, inputStream)
            }
        } catch (ex: IOException) {
            println(ex)
            component = null
        }
        return component
    }
}