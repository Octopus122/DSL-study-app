package org.example
import org.example.client.*

fun main() {
    val mockClient = Client()
    val response = mockClient.perform(200, "OK")
        .andExpect {
            status {
                isOk()
            }
            body {
                isNotNull()
            }
        }.andDo { response ->
            println(response)
        }.response

}


