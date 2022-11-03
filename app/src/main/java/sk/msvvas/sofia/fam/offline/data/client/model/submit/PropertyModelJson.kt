package sk.msvvas.sofia.fam.offline.data.client.model.submit

/**
 * JSON property model for batch submitting inventory
 */
data class PropertyModelJson(
    var inventoryId: String,
    var inventNumber: String,
    var serialNumber: String,
    var client: String,
    var propertyNumber: String,
    var subnumber: String,
    var textMainNumber: String,
    var recordStatus: Char,
    var werks: String,
    var locality: String,
    var localityNew: String,
    var room: String,
    var roomNew: String,
    var personalNumber: String,
    var personalNumberNew: String,
    var center: String,
    var centerNew: String,
    var workplace: String,
    var workplaceNew: String,
    var fixedNote: String,
    var variableNote: String,
    var isManual: Boolean,
) {
    fun toJSON(): String {
        return "\t\t\t{\n" +
                "\t\t\t\t\"Inven\":\"$inventoryId\",\n" +
                "\t\t\t\t\"Invnr\":\"$inventNumber\",\n" +
                "\t\t\t\t\"Sernr\":\"$serialNumber\",\n" +
                "\t\t\t\t\"Zstattext\":\"${
                    when (recordStatus) {
                        'X' -> "Nespracovaný"
                        'C' -> "Chýbajúci"
                        'S' -> "Spracovaný"
                        'Z' -> "Zmenený"
                        'N' -> "Nový"
                        else -> "Nespracovaný"
                    }
                }\",\n" +
                "\t\t\t\t\"Mandt\":\"$client\",\n" +
                "\t\t\t\t\"Bukrs\":\"1000\",\n" +
                "\t\t\t\t\"Anln1\":\"$propertyNumber\",\n" +
                "\t\t\t\t\"Anln2\":\"$subnumber\",\n" +
                "\t\t\t\t\"Txt50\":\"${textMainNumber.replace("\"", "\\\"")}\",\n" +
                "\t\t\t\t\"Zstat\":\"$recordStatus\",\n" +
                "\t\t\t\t\"Werks\":\"$werks\",\n" +
                "\t\t\t\t\"Werksn\":\"$werks\",\n" +
                "\t\t\t\t\"Stort\":\"$locality\",\n" +
                "\t\t\t\t\"Stortn\":\"$localityNew\",\n" +
                "\t\t\t\t\"Raumn\":\"$room\",\n" +
                "\t\t\t\t\"Raumnn\":\"$roomNew\",\n" +
                "\t\t\t\t\"Pernr\":\"$personalNumber\",\n" +
                "\t\t\t\t\"Pernrn\":\"$personalNumberNew\",\n" +
                "\t\t\t\t\"Kostl\":\"$center\",\n" +
                "\t\t\t\t\"Kostln\":\"$centerNew\",\n" +
                "\t\t\t\t\"Anlue\":\"$workplace\",\n" +
                "\t\t\t\t\"Anluen\":\"$workplaceNew\",\n" +
                "\t\t\t\t\"Mengeo\":\"0.000\",\n" +
                "\t\t\t\t\"Mengen\":\"0.000\",\n" +
                "\t\t\t\t\"Xnote\":\"${if (fixedNote.isEmpty()) "" else fixedNote.first()}\",\n" +
                "\t\t\t\t\"Fnote\":\"$variableNote\",\n" +
                "\t\t\t\t\"Manua\":\"${
                    if (isManual) "Y"
                    else "N"
                }\"\n" +
                "\t\t\t}"
    }
}
