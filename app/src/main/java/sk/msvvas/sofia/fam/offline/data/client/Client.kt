package sk.msvvas.sofia.fam.offline.data.client

import com.thoughtworks.xstream.XStream
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import sk.msvvas.sofia.fam.offline.data.application.entities.InventoryEntity
import sk.msvvas.sofia.fam.offline.data.application.entities.PropertyEntity
import sk.msvvas.sofia.fam.offline.data.client.model.inventory.ContentInventoryXml
import sk.msvvas.sofia.fam.offline.data.client.model.inventory.FeedInventoryXml
import sk.msvvas.sofia.fam.offline.data.client.model.property.FeedPropertyXml
import sk.msvvas.sofia.fam.offline.data.client.model.transformator.InventoryTransformator
import sk.msvvas.sofia.fam.offline.data.client.model.transformator.PropertyTransformator

object Client {

    private const val HOST = "sofiafioritest.iedu.sk"
    private val PROTOCOL = URLProtocol.HTTPS
    private const val basePath = "sap/opu/odata/vvs/ZFAMFIORI_SRV"

    private val mapper: XStream = XStream()

    init {
        mapper.processAnnotations(FeedInventoryXml::class.java)
        mapper.processAnnotations(ContentInventoryXml::class.java)
        mapper.processAnnotations(InventoryXml::class.java)
        mapper.allowTypes(arrayOf(FeedInventoryXml::class.java))
    }

    suspend fun validateLogin(username: String, password: String, clientId: String): Boolean {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            buildGetRequest(
                this,
                getPath = "getInventoryGeneralSet",
                username = username,
                password = password,
                clientId = clientId
            )
        }
        client.close()
        return response.status.isSuccess()
    }

    suspend fun getInventories(): List<InventoryEntity> {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            buildGetRequest(
                this,
                getPath = "getInventoryGeneralSet"
            )
        }
        client.close()

        val output = mapper.fromXML(response.bodyAsText()) as FeedInventoryXml
        return InventoryTransformator.inventoryListFromInventoryFeed(output)
    }

    suspend fun getPropertiesByInventoryID(): List<PropertyEntity> {
        val client = HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 500000
            }
        }

        val additionalParameters = HashMap<String, String>()
        additionalParameters["\$filter"] =
            "Inven eq '350' and Zstat eq '' and Stort eq '' and Raumn eq '' and Pernr eq '' and Anlue eq '' and Kostl eq ''"

        val response: HttpResponse = client.get {
            buildGetRequest(
                this,
                getPath = "GetInventoryItemsSet",
                additionalParameters = additionalParameters
            )
        }
        client.close()

        val output = mapper.fromXML(response.bodyAsText()) as FeedPropertyXml
        return PropertyTransformator.propertyListFromPropertyFeed(output)
    }

    private fun buildGetRequest(
        builder: HttpRequestBuilder,
        getPath: String,
        username: String = ClientData.username,
        password: String = ClientData.password,
        clientId: String = ClientData.client,
        additionalParameters: HashMap<String, String> = HashMap()
    ) {
        builder.let {
            it.url {
                buildUrl(
                    this,
                    getPath = getPath,
                    clientId = clientId,
                    additionalParameters = additionalParameters
                )
            }
            authorize(it, username = username, password = password)
        }
    }

    private fun appendParameters(
        parameters: ParametersBuilder,
        clientId: String = ClientData.client,
        additionalParameters: HashMap<String, String>
    ) {
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
        additionalParameters.forEach {
            parameters.append(it.key, it.value)
        }
    }

    private fun buildUrl(
        builder: URLBuilder,
        getPath: String,
        clientId: String = ClientData.client,
        additionalParameters: HashMap<String, String>
    ) {
        builder.let {
            it.host = HOST
            it.protocol = PROTOCOL
            it.path(basePath, getPath)
            appendParameters(
                it.parameters,
                clientId = clientId,
                additionalParameters = additionalParameters
            )
        }
    }

    private fun authorize(
        builder: HttpRequestBuilder,
        username: String = ClientData.username,
        password: String = ClientData.password
    ) {
        builder.basicAuth(
            username = username,
            password = password
        )
    }

}