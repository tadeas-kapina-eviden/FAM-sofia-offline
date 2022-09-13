package sk.msvvas.sofia.fam.offline.data.client.model.codebook.user

import com.thoughtworks.xstream.annotations.XStreamAlias

data class UserCodebookContentXml(
    @XStreamAlias("m:properties")
    val user: UserCodebookXml
)
