package ru.alek.rickmortyviewer.Domain.Entities

data class SimpleFilter(
    var name: String? = null, //filter by the given name.
    var status: String? = null, //filter by the given status.
    var gender: String? = null //filter by the given gender.
    ){

    /** Id's of elements in dsPicker in BottomSheetDialog */
    var statusId = 0
    var genderId = 0
    /** ------------------------------------------------- */

    enum class Status(val value: String){
        ALIVE(SimpleFilter.ALIVE),
        DEAD(SimpleFilter.DEAD),
        UNKNOWN(SimpleFilter.UNKNOWN)
    }

    enum class Gender(val value: String){
        FEMALE(SimpleFilter.FEMALE),
        MALE(SimpleFilter.MALE),
        GENDERLESS(SimpleFilter.GENDERLESS),
        UNKNOWN(SimpleFilter.UNKNOWN)
    }

    private companion object {
        private const val ALIVE = "Alive"
        private const val DEAD = "Dead"
        private const val MALE = "Male"
        private const val FEMALE = "Female"
        private const val GENDERLESS = "Genderless"
        private const val UNKNOWN = "unknown"
    }

}