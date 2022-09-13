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
import sk.msvvas.sofia.fam.offline.data.application.entities.codebook.*
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.locality.LocalityCodebookContentXml
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.locality.LocalityCodebookFeedXml
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.locality.LocalityCodebookXml
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.note.NoteCodebookContentXml
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.note.NoteCodebookFeedXml
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.note.NoteCodebookXml
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.place.PlaceCodebookContentXml
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.place.PlaceCodebookFeedXml
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.place.PlaceCodebookXml
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.room.RoomCodebookContentXml
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.room.RoomCodebookFeedXml
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.room.RoomCodebookXml
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.user.UserCodebookContentXml
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.user.UserCodebookFeedXml
import sk.msvvas.sofia.fam.offline.data.client.model.codebook.user.UserCodebookXml
import sk.msvvas.sofia.fam.offline.data.client.model.inventory.InventoryContentXml
import sk.msvvas.sofia.fam.offline.data.client.model.inventory.InventoryFeedXml
import sk.msvvas.sofia.fam.offline.data.client.model.property.PropertyContentXml
import sk.msvvas.sofia.fam.offline.data.client.model.property.PropertyFeedXml
import sk.msvvas.sofia.fam.offline.data.client.model.property.PropertyXml
import sk.msvvas.sofia.fam.offline.data.client.model.transformator.InventoryTransformator
import sk.msvvas.sofia.fam.offline.data.client.model.transformator.PropertyTransformator
import sk.msvvas.sofia.fam.offline.data.client.model.transformator.codebook.*

object Client {

    private const val HOST = "sofiafioritest.iedu.sk"
    private val PROTOCOL = URLProtocol.HTTPS
    private const val basePath = "sap/opu/odata/vvs/ZFAMFIORI_SRV"

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

        val mapper = XStream()
        mapper.processAnnotations(InventoryFeedXml::class.java)
        mapper.processAnnotations(InventoryContentXml::class.java)
        mapper.processAnnotations(InventoryXml::class.java)
        mapper.allowTypes(arrayOf(InventoryFeedXml::class.java))

        val output = mapper.fromXML(response.bodyAsText()) as InventoryFeedXml
        return InventoryTransformator.inventoryListFromInventoryFeed(output)
    }

    suspend fun getLocalityCodebooks(): List<LocalityCodebookEntity> {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            buildGetRequest(
                this,
                getPath = "getAllValLocalitySet"
            )
        }
        client.close()

        val mapper = XStream()
        mapper.processAnnotations(LocalityCodebookFeedXml::class.java)
        mapper.processAnnotations(LocalityCodebookContentXml::class.java)
        mapper.processAnnotations(LocalityCodebookXml::class.java)
        mapper.allowTypes(arrayOf(LocalityCodebookFeedXml::class.java))

        val output = mapper.fromXML(response.bodyAsText()) as LocalityCodebookFeedXml
        return LocalityCodebookTransformator.localityCodebookListFromLocalityCodebookFeed(output)
    }

    suspend fun getRoomCodebooks(): List<RoomCodebookEntity> {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            buildGetRequest(
                this,
                getPath = "getAllValRoomsSet"
            )
        }
        client.close()

        val mapper = XStream()
        mapper.processAnnotations(RoomCodebookFeedXml::class.java)
        mapper.processAnnotations(RoomCodebookContentXml::class.java)
        mapper.processAnnotations(RoomCodebookXml::class.java)
        mapper.allowTypes(arrayOf(RoomCodebookFeedXml::class.java))

        val output = mapper.fromXML(response.bodyAsText()) as RoomCodebookFeedXml
        return RoomCodebookTransformator.roomCodebookListFromRoomCodebookFeed(output)
    }

    suspend fun getPlaceCodebooks(): List<PlaceCodebookEntity> {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            buildGetRequest(
                this,
                getPath = "getAllValPlacesSet"
            )
        }
        client.close()

        val mapper = XStream()
        mapper.processAnnotations(PlaceCodebookFeedXml::class.java)
        mapper.processAnnotations(PlaceCodebookContentXml::class.java)
        mapper.processAnnotations(PlaceCodebookXml::class.java)
        mapper.allowTypes(arrayOf(PlaceCodebookFeedXml::class.java))

        val output = mapper.fromXML(response.bodyAsText()) as PlaceCodebookFeedXml
        return PlaceCodebookTransformator.placeCodebookListFromPlaceCodebookFeed(output)
    }

    suspend fun getUserCodebooks(): List<UserCodebookEntity> {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            buildGetRequest(
                this,
                getPath = "getAllValUsersSet"
            )
        }
        client.close()

        val mapper = XStream()
        mapper.processAnnotations(UserCodebookFeedXml::class.java)
        mapper.processAnnotations(UserCodebookContentXml::class.java)
        mapper.processAnnotations(UserCodebookXml::class.java)
        mapper.allowTypes(arrayOf(UserCodebookFeedXml::class.java))

        val output = mapper.fromXML(response.bodyAsText()) as UserCodebookFeedXml
        return UserCodebookTransformator.userCodebookListFromUserCodebookFeed(output)
    }

    suspend fun getNoteCodebooks(): List<NoteCodebookEntity> {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            buildGetRequest(
                this,
                getPath = "getAllValNotesSet"
            )
        }
        client.close()

        val mapper = XStream()
        mapper.processAnnotations(NoteCodebookFeedXml::class.java)
        mapper.processAnnotations(NoteCodebookContentXml::class.java)
        mapper.processAnnotations(NoteCodebookXml::class.java)
        mapper.allowTypes(arrayOf(NoteCodebookFeedXml::class.java))

        val output = mapper.fromXML(response.bodyAsText()) as NoteCodebookFeedXml
        return NoteCodebookTransformator.noteCodebookListFromNoteCodebookFeed(output)
    }

    suspend fun getPropertiesByInventoryID(inventoryId: String): List<PropertyEntity> {
        val client = HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 500000
            }
        }

        val additionalParameters = HashMap<String, String>()
        additionalParameters["\$filter"] =
            "Inven eq '$inventoryId' and Zstat eq '' and Stort eq '' and Raumn eq '' and Pernr eq '' and Anlue eq '' and Kostl eq ''"

        val response: HttpResponse = client.get {
            buildGetRequest(
                this,
                getPath = "GetInventoryItemsSet",
                additionalParameters = additionalParameters
            )
        }
        client.close()

        val mapper = XStream()
        mapper.processAnnotations(PropertyFeedXml::class.java)
        mapper.processAnnotations(PropertyContentXml::class.java)
        mapper.processAnnotations(PropertyXml::class.java)
        mapper.allowTypes(arrayOf(PropertyFeedXml::class.java))

        val output = mapper.fromXML(response.bodyAsText()) as PropertyFeedXml
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