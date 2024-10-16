package org.example
import org.example.client.*

fun main() {
    val mockClient = Client()
    val response1 = mockClient.perform(101, "OK")
        .andExpect {
            status {
                isBadRequest()
            }
            body {
                isNotNull()
            }
        }.andDo { response ->
            println(response)
        }.response

}


