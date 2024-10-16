package org.example.client

class Client {
    fun perform(code: Int, body: String?) = ResponseActions(code, body)
}
