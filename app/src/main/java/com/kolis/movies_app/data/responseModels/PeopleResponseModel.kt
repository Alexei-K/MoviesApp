package com.kolis.movies_app.data.responseModels

import com.kolis.movies_app.data.dataModels.CastModel
import com.kolis.movies_app.data.dataModels.CrewModel

data class PeopleResponseModel(
    val id: Int,
    val cast: List<CastModel>?,
    val crew: List<CrewModel>?
)