package sk.msvvas.sofia.fam.offline.data.client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

object Client {
    suspend fun validateLogin(username: String, password: String, clientId:String): Boolean {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "sofiafioritest.iedu.sk"
                path("sap", "opu", "odata", "vvs", "ZFAMFIORI_SRV", "getInventoryGeneralSet")
                parameters.append("sap-client", clientId)
                parameters.append("sap-language", "SK")
                parameters.append(
                    "Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
                )
                parameters.append("Accept-Encoding", "gzip, deflate, br")
                parameters.append("Accept-Language", "sk")
                parameters.append("Cache-Contro", "max-age=0")
                parameters.append("Connection", "keep-alive")
            }
            basicAuth(
                username = username,
                password = password
            )
        }
        println(response.status)
        println()
        println(response.headers["Content-type"])
        client.close()
        return response.status.isSuccess()
    }
}