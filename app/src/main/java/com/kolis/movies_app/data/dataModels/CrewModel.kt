package com.kolis.movies_app.data.dataModels

data class CrewModel(val name: String, val job: String) {

    fun isDirector(): Boolean {
        return job == "Director"
    }

}