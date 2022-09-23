package sk.msvvas.sofia.fam.offline.data.client

import com.thoughtworks.xstream.XStream
import io.ktor.client.*
import io.ktor.client.engine.cio.*
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
import sk.msvvas.sofia.fam.offline.data.client.model.inventory.InventoryXml
import sk.msvvas.sofia.fam.offline.data.client.model.property.PropertyContentXml
import sk.msvvas.sofia.fam.offline.data.client.model.property.PropertyFeedXml
import sk.msvvas.sofia.fam.offline.data.client.model.property.PropertyXml
import sk.msvvas.sofia.fam.offline.data.transformator.InventoryTransformator
import sk.msvvas.sofia.fam.offline.data.transformator.PropertyTransformator
import sk.msvvas.sofia.fam.offline.data.transformator.codebook.*

/**
 * Object with functions used for making request to back-end
 */
object Client {
    /**
     * Domain of back-end server
     */
    private const val HOST = "sofiafioritest.iedu.sk"

    /**
     * URL protocol used in requests
     */
    private val PROTOCOL = URLProtocol.HTTPS

    /**
     * First part of URL path constant for all requests
     */
    private const val basePath = "sap/opu/odata/vvs/ZFAMFIORI_SRV"

    /**
     * Function that test connection with server with entered login data
     * @param username login name of user
     * @param password password of user
     * @param clientId id of school client
     * @return true if can connect to server (login data are valid), false if cannot connect (login data are invalid or device isn't connected to internet)
     */
    suspend fun validateLogin(username: String, password: String, clientId: String): Boolean {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            buildRequest(
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

    /**
     * Function to get list of all inventories from back-end for logged user
     * User login data are taken from ClientData object
     * @see ClientData
     * @return list of all inventories converted to entities for local database
     */
    suspend fun getInventories(): List<InventoryEntity> {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            buildRequest(
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

    /**
     * Function to get list of all locality codebooks from back-end for logged user
     * User login data are taken from ClientData object
     * @see ClientData
     * @return list of all locality codebooks converted to entities for local database
     */
    suspend fun getLocalityCodebooks(): List<LocalityCodebookEntity> {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            buildRequest(
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

    /**
     * Function to get list of all room codebooks from back-end for logged user
     * User login data are taken from ClientData object
     * @see ClientData
     * @return list of all room codebooks converted to entities for local database
     */
    suspend fun getRoomCodebooks(): List<RoomCodebookEntity> {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            buildRequest(
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

    /**
     * Function to get list of all place codebooks from back-end for logged user
     * User login data are taken from ClientData object
     * @see ClientData
     * @return list of all place codebooks converted to entities for local database
     */
    suspend fun getPlaceCodebooks(): List<PlaceCodebookEntity> {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            buildRequest(
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

    /**
     * Function to get list of all user codebooks from back-end for logged user
     * User login data are taken from ClientData object
     * @see ClientData
     * @return list of all user codebooks converted to entities for local database
     */
    suspend fun getUserCodebooks(): List<UserCodebookEntity> {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            buildRequest(
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

    /**
     * Function to get list of all note codebooks from back-end for logged user
     * User login data are taken from ClientData object
     * @see ClientData
     * @return list of all note codebooks converted to entities for local database
     */
    suspend fun getNoteCodebooks(): List<NoteCodebookEntity> {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get {
            buildRequest(
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

    /**
     * Function to get list of all properties from back-end for logged user for selected inventory
     * User login data are taken from ClientData object
     * @see ClientData
     * @return list of all properties converted to entities for local database
     */
    suspend fun getPropertiesByInventoryID(inventoryId: String): List<PropertyEntity> {
        val client = HttpClient(CIO)

        val additionalParameters = HashMap<String, String>()
        additionalParameters["\$filter"] =
            "Inven eq '$inventoryId' and Zstat eq 'W' and Stort eq '' and Raumn eq '' and Pernr eq '' and Anlue eq '' and Kostl eq ''"

        val responseUnprocessed: HttpResponse = client.get {
            buildRequest(
                this,
                getPath = "GetInventoryItemsSet",
                additionalParameters = additionalParameters
            )
        }

        additionalParameters["\$filter"] =
            "Inven eq '$inventoryId' and Zstat eq 'P' and Stort eq '' and Raumn eq '' and Pernr eq '' and Anlue eq '' and Kostl eq ''"

        val responseProcessed: HttpResponse = client.get {
            buildRequest(
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

        val result = mutableListOf<PropertyEntity>()
        result.addAll(
            PropertyTransformator.propertyListFromPropertyFeed(
                mapper.fromXML(
                    responseUnprocessed.bodyAsText()
                ) as PropertyFeedXml
            )
        )
        result.addAll(
            PropertyTransformator.propertyListFromPropertyFeed(
                mapper.fromXML(
                    responseProcessed.bodyAsText()
                ) as PropertyFeedXml
            )
        )
        return result
    }

    suspend fun submitProcessedProperties(
        inventoryEntity: InventoryEntity,
        properties: List<PropertyEntity>
    ) {

        val token = fetchTokenRequest()
        val client = HttpClient(CIO)
        client.post {
            buildRequest(
                this,
                getPath = "",
            )
            header("X-CSRF-TOKEN", token)
            setBody(
                //TODO
                InventoryTransformator.inventoryEntityToInventoryModelJson(
                    inventoryEntity,
                    properties
                )
            )
        }
    }

    /**
     * Build default get request with custom parameters parameters
     * @param builder builder of get request
     * @param getPath last part of request path
     * @param username user login name (is taken from ClientData by default)
     * @param password user password (is taken from ClientData by default)
     * @param clientId id of school client (is taken from ClientData by default)
     * @param additionalParameters additional url parameters (added to default parameters)
     * @see ClientData
     */
    private fun buildRequest(
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

    /**
     * Append base and additional parameters to get request
     * @param parameters parameter builder from get request
     * @param clientId school client id (is taken from ClientData by default)
     * @param additionalParameters additional url parameters (added to default parameters)
     * @see ClientData
     */
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

    /**
     * Build URL of request
     * @param builder url builder from request
     * @param getPath last part of request path
     * @param clientId id of school client (is taken from ClientData by default)
     * @param additionalParameters additional url parameters (added to default parameters)
     */
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

    /**
     * Function to fetch token from fever for sending locally processed data
     * @return X-CSRF-Token from backend
     */
    private suspend fun fetchTokenRequest(): String? {
        val client = HttpClient(CIO)
        return client.get {
            buildRequest(
                this,
                getPath = "",
            )
            header("X-CSRF-Token", "Fetch")
        }.headers["X-CSRF-Token"]
    }

    /**
     * Add basic authorization to request
     * @param builder builder of get request
     * @param username user login name (is taken from ClientData by default)
     * @param password user password (is taken from ClientData by default)
     * @see ClientData
     */
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