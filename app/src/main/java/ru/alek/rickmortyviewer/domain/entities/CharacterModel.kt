package ru.alek.rickmortyviewer.domain.entities

data class CharacterModel (
    val id : Int,
    val name : String,
    val status : String,
    val species : String,
    val type : String,
    val gender : String,
    val origin : Origin,
    val location : Location,
    val image : String,
    val episode : List<String>,
){

    var episodesChild : EpisodeModel? = null

    init {
        //episodesChild = EpisodeModel(id = id)
        //bind(null)
    }

    data class Location (
        val name : String,
        val url : String
    )
    data class Origin (
        val name : String,
        val url : String
    )

    private var listener: ((episodesChild: List<EpisodeModel.Episode>?) -> Unit)? = null

    fun bindListener(listener: ((episodesChild: List<EpisodeModel.Episode>?) -> Unit)?){
        this.listener = listener
    }

    fun bind(episodes: List<EpisodeModel.Episode>?){
             episodesChild?.apply {
                 this.episodes = episodes
                 listener?.invoke(episodesChild?.episodes)
             }
    }

}
