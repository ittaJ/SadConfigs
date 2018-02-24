package com.jatti.config

import org.bukkit.configuration.file.YamlConfiguration
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

object ConfigurationManager {

    private var configs = ArrayList<Configuration>()

    fun getFieldValue(configuration: Configuration, name: String): Any? {
        if (configuration.values.isNotEmpty()) {

            configuration.values.keys
                    .filter { it.contains(name) }
                    .forEach { return configuration.values[it] }

        } else {
            throw ConfigurationException("Values list can't be empty!")
        }
        return null
    }

    fun getFieldName(configuration: Configuration, name: String): String? {
        if (configuration.values.isNotEmpty()) {

            configuration.values.keys.filter { it.contains(name) }.forEach { return it }

        } else {
            throw ConfigurationException("Values list can't be empty!")
        }
        return null
    }

    fun setField(configuration: Configuration, name: String, value: Any) {
        if (configuration.values.isNotEmpty()) {

            configuration.values.keys
                    .filter { it.contains(name) }
                    .forEach { configuration.values[it] = value }

        } else {
            throw ConfigurationException("Values list can't be empty!")
        }
    }

    fun registerNewConfiguration(configuration: Configuration) {
        if (configuration.values.isEmpty()) {
            throw ConfigurationException("Values list can't be empty!")
        }


        if (!configuration.folder.exists()) {
            configuration.folder.mkdir()
        }
        configuration.folder.listFiles()
                .filter { it.name == configuration.fileName }
                .forEach { throw ConfigurationException("There already is file with name ${configuration.fileName}") }

        configs.add(configuration)
        println("Registered new configuration ${configuration.fileName}!")

    }

    fun loadConfigurations() {

        for (conf in configs) {

            val file = File(conf.folder, "${conf.fileName}.yml")

            if (!file.exists()) {
                file.createNewFile()

                val writer = BufferedWriter(FileWriter(file))

                for (value in conf.values) {

                    if (value.key.startsWith("#")) {
                        val splitedKey = value.key.split('@')
                        writer.write("${splitedKey[0]}\n")

                        if (value.value !is String) {
                            writer.write("${splitedKey[1]}: ${value.value}\n")
                            writer.flush()
                        } else {
                            writer.write("${splitedKey[1]}: '${value.value}'")
                            writer.flush()
                        }

                    } else {
                        if (value.value !is String) {
                            writer.write("${value.key}: ${value.value}\n")
                            writer.flush()
                        } else {
                            writer.write("${value.key}: '${value.value}'\n")
                            writer.flush()
                        }
                    }

                }

            } else {
                val yml = YamlConfiguration.loadConfiguration(file)
                yml.getKeys(false).forEach { setField(conf, getFieldName(conf, it)!!, yml.get(it)) }
            }

        }

    }
}