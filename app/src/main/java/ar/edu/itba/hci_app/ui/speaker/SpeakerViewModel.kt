package ar.edu.itba.hci_app.ui.speaker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ar.edu.itba.hci_app.data.*
import ar.edu.itba.hci_app.data.repository.SpeakerRepository
import ar.edu.itba.hci_app.model.devices.Speaker
import ar.edu.itba.hci_app.ui.RepositoryViewModel

class SpeakerViewModel constructor(repository: SpeakerRepository) :
    RepositoryViewModel<SpeakerRepository>(repository) {

    private val speakerId = MutableLiveData<String>()
    private var speaker: LiveData<Resource<Speaker>>? = null

    init {
        super.repository

        speaker = Transformations.switchMap(
            speakerId
        ) { speakerId: String? ->
            if (speakerId == null) {
                return@switchMap AbsentLiveData.create<Resource<Speaker>>()
            } else {
                return@switchMap repository.getSpeaker(speakerId)
            }
        }
    }

    fun getSpeaker(): LiveData<Resource<Speaker>>? {
        return getSpeaker(false);
    }

    fun getSpeaker(forceAPICall: Boolean): LiveData<Resource<Speaker>>? {
        loadSpeaker(forceAPICall)
        return speaker
    }

    fun modifySpeaker(speaker: Speaker?): LiveData<Resource<Speaker>>? {
        return repository.modifySpeaker(speaker)
    }

    private fun loadSpeaker(forceAPICall: Boolean) {
        speaker = repository.getSpeaker(speakerId.toString(), forceAPICall)
    }
}
