package apps

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url($(c(regex('/getApp/(.+)')), p('/getApp/2')))
    }

    response {
        status OK()
        body(
                "name": $(regex(nonBlank())),
                "running": $(regex(anyBoolean())),
                "id": $(regex(nonBlank()))
        )
        headers {
            contentType('application/json')
        }
    }
}