package com.sat.rfc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform