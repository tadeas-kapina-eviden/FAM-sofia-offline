package sk.msvvas.sofia.fam.offline.data.client

import com.thoughtworks.xstream.XStream
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.data.client.structure.ContentXml
import sk.msvvas.sofia.fam.offline.data.client.structure.FeedXml

object Client {

    private const val HOST = "sofiafioritest.iedu.sk"
    private val PROTOCOL = URLProtocol.HTTPS
    private const val basePath = "sap/opu/odata/vvs/ZFAMFIORI_SRV"

    private val mapper: XStream = XStream()

    init {
        mapper.processAnnotations(FeedXml::class.java)
        mapper.processAnnotations(ContentXml::class.java)
        mapper.processAnnotations(InventoryXml::class.java)
        mapper.allowTypes(arrayOf(FeedXml::class.java))
    }

    suspend fun validateLogin(username: String, password: String, clientId: String): Boolean {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            url {
                protocol = PROTOCOL
                host = HOST
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
        client.close()
        return response.status.isSuccess()
    }

    suspend fun getInventories(): List<InventoryEntity> {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            url {
                protocol = PROTOCOL
                host = HOST
                path("sap", "opu", "odata", "vvs", "ZFAMFIORI_SRV", "getInventoryGeneralSet")
                parameters.append("sap-client", ClientData.client)
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
                username = ClientData.username,
                password = ClientData.password
            )
        }
        client.close()

        val output = mapper.fromXML(response.bodyAsText()) as FeedXml
        return output.entries.map { entry ->
            entry.content.inventory.let {
                InventoryEntity(
                    id = it.id,
                    createdAt = it.date,
                    createdBy = it.createdBy,
                    note = it.note,
                    countAll = it.counts.split("/")[1].toInt(),
                    countProcessed = it.counts.split("/")[0].toInt(),
                )
            }
        }
    }
}