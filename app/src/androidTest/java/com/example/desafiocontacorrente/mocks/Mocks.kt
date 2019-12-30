package com.example.desafiocontacorrente.mocks

class Mocks {
    val LOGIN_ERROR: String
        get() = """
            {
                "status": true
            }
        """.trimIndent()

    val LOGIN_SUCCESS: String
        get() = """
            {
                "status": false
            }
        """.trimIndent()

    val GET_USER_ERROR: String
        get() = """
            []
        """.trimIndent()

    val GET_USER_SUCCESS: String
        get() = """
            {
                "id": "2",
                "name": "Lamarques Oliveira",
                "email": "lamarques.oliveira@evosystems.com.br",
                "profile": "http://yourgps.com.br/public/img/profile.png",
                "balance": "1029"
            }
        """.trimIndent()

    val TRANSFER_ERROR: String
        get() = """
            {
                "status": false,
                "error": "Dados inválidos!"
            }
        """.trimIndent()

    val TRANSFER_SUCCESS: String
        get() = """
            {
                "status": true
            }
        """.trimIndent()

    val BANKSTATEMENT_ERROR: String
        get() = """
            []
        """.trimIndent()

    val BANKSTATEMENT_SUCCESS: String
        get() = """
                [
                    {
                        "id": "145",
                        "id_from": "2",
                        "id_to": "1",
                        "value": "1",
                        "data": "2019-12-27 11:42:30"
                    },
                    {
                        "id": "144",
                        "id_from": "2",
                        "id_to": "5",
                        "value": "1",
                        "data": "2019-12-27 11:40:03"
                    },
                    {
                        "id": "141",
                        "id_from": "1",
                        "id_to": "2",
                        "value": "10",
                        "data": "2019-12-26 16:35:26"
                    }
                ]
            """.trimIndent()
 }