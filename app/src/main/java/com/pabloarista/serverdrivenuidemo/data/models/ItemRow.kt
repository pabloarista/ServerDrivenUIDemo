package com.pabloarista.serverdrivenuidemo.data.models

import com.pabloarista.serverdrivenuidemo.data.models.enumerations.*

class ItemRow(
    val imageMetric     : ImageMetric
    , title             : String
    , titleHexString    : String
    , description       : String
    , detailOptions     : ItemRowDetailOptions
    , padding           : List<PaddingMetric>
) {

}