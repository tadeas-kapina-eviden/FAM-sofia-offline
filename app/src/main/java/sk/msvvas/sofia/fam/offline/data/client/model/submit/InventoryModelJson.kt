package sk.msvvas.sofia.fam.offline.data.client.model.submit

/**
 * JSON inventory model for submitting inventory
 */
data class InventoryModelJson(
    val id: String,
    var createdAt: String,
    var createdAtFormatted: String,
    var createdBy: String,
    var note: String,
    var properties: List<PropertyModelJson>
) {
    fun toJSON(): String {
        val propertiesEntriesStringBuilder = StringBuilder()
        properties.forEach {
            propertiesEntriesStringBuilder.append(it.toJSON())
            propertiesEntriesStringBuilder.append(",\n")
        }
        propertiesEntriesStringBuilder.deleteCharAt(propertiesEntriesStringBuilder.lastIndex - 1)
        return "{\n" +
                "\t\"d\": {\n" +
                "\t\t\"Inven\":\"${id.treatSpecialSymbols()}\",\n" +
                "\t\t\"Ipozn\":\"${note.treatSpecialSymbols()}\",\n" +
                "\t\t\"Datin\":\"${createdAt.treatSpecialSymbols()}\",\n" +
                "\t\t\"Datinf\":\"${createdAtFormatted.treatSpecialSymbols()}\",\n" +
                "\t\t\"Xubname\":\"${createdBy.treatSpecialSymbols()}\",\n" +
                "\t\t\"Text20\":\"todo\",\n" +
                "\t\t\"GetInventoryItemsSet\":[\n" +
                propertiesEntriesStringBuilder.toString() +
                "\t\t]\n" +
                "\t}\n" +
                "}\n"
    }
    private fun String.treatSpecialSymbols(): String {
        return this.replace("\\", "\\\\").replace("\"", "\\\"").replace("\b", "\\b")
            .replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t")
    }
}

