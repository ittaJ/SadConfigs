package com.jatti.config

import java.io.File

interface Configuration {

    val folder: File

    val fileName: String
        get() = "config"

    val values: HashMap<String, Any>

    infix fun String.comment(comment: String): String = "#$comment@$this"

}