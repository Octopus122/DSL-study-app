package org.example.client

class ResponseActions(code: Int, body: String?) {
    val response: Response
    init {
        response = Response(code, body)
    }

    fun andDo(function: (Response)->Unit):ResponseActions
    {
        function.invoke(response)
        return this
    }

    fun andExpect(function: ResponseMatcher.()->Unit): ResponseActions
    {
        ResponseMatcher().function()
        return this
    }
    inner class ResponseMatcher()
    {
        fun status(statusCheck: StatusResponseMatcher.()->Unit)
        {
            try {
                statusCheck(StatusResponseMatcher())
            }
            catch (e: StatusResponseMatchersException)
            {
                println(e)
            }
        }
        fun body(bodyCheck: BodyResponseMatcher.()->Unit)
        {
            bodyCheck(BodyResponseMatcher())
        }
        inner class StatusResponseMatcher()
        {
            fun isOk()
            {
                println(response.code != 200)
                if (response.code != 200)
                {
                    throw StatusResponseMatchersException("Status is not 200! It is ${response.code}")
                }
            }
            fun isBadRequest()
            {
                if (response.code == 400)
                {
                    return
                }
                throw StatusResponseMatchersException("Status is not 400! It is ${response.code}")
            }
            fun isInternalServerError()
            {
                if (response.code == 500)
                {
                    return
                }
                throw StatusResponseMatchersException("Status is not 500! It is ${response.code}")
            }
        }
        inner class BodyResponseMatcher()
        {
            fun isNull()
            {
                if (response.body == null)
                {
                    return
                }
                throw BodyResponseMatchersException("Body is not null!")
            }
            fun isNotNull()
            {
                if (response.body != null)
                {
                    return
                }
                throw BodyResponseMatchersException("Body is null!")
            }
        }
    }


}
sealed class ResponseMatchersException (message: String) : Exception(message)
class StatusResponseMatchersException (message: String) : ResponseMatchersException(message)
class BodyResponseMatchersException (message: String) : ResponseMatchersException(message)
