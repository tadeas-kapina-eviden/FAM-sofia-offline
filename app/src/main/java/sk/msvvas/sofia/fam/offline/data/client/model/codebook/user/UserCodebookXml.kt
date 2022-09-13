package sk.msvvas.sofia.fam.offline.data.client.model.codebook.user

import com.thoughtworks.xstream.annotations.XStreamAlias

data class UserCodebookXml(
    @XStreamAlias("d:Pernr")
    val id: String,
    @XStreamAlias("d:PernrText")
    val fullName: String,
)
