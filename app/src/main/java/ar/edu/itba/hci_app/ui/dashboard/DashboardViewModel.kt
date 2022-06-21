package ar.edu.itba.hci_app.ui.dashboard

import androidx.lifecycle.*
import ar.edu.itba.hci_app.data.AbsentLiveData
import ar.edu.itba.hci_app.data.Resource
import ar.edu.itba.hci_app.data.RoutineRepository
import ar.edu.itba.hci_app.data.Status
import ar.edu.itba.hci_app.model.Routine
import ar.edu.itba.hci_app.ui.RepositoryViewModel

class DashboardViewModel constructor(repository: RoutineRepository) :
    RepositoryViewModel<RoutineRepository>(repository) {

    private val routines = MediatorLiveData<Resource<List<Routine>>>()
    private val routineId = MutableLiveData<String>()
    private var routine: LiveData<Resource<Routine>>? = null

    init {
        super.repository

        routine = Transformations.switchMap(
            routineId
        ) { routineId: String? ->
            if (routineId == null) {
                return@switchMap AbsentLiveData.create<Resource<Routine>>()
            } else {
                return@switchMap repository.getRoutine(routineId)
            }
        }
    }

    fun getRoutines(): LiveData<Resource<List<Routine>>> {
        loadRoutines(false)
        return routines
    }

    fun getRoutines(forceAPICall: Boolean): LiveData<Resource<List<Routine>>> {
        loadRoutines(forceAPICall)
        return routines
    }

    fun getRoutine(): LiveData<Resource<Routine>>? {
        return routine
    }

    fun addRoutine(routine: Routine?): LiveData<Resource<Routine>>? {
        return repository.addRoutine(routine)
    }

    fun modifyRoutine(routine: Routine?): LiveData<Resource<Routine>>? {
        return repository.modifyRoutine(routine)
    }

    fun deleteRoutine(routine: Routine?): LiveData<Resource<Void>>? {
        return repository.deleteRoutine(routine)
    }

    fun setRoutineId(routineId: String) {
        if (this.routineId.value != null && routineId == this.routineId.value) {
            return
        }
        this.routineId.value = routineId
    }

    private fun loadRoutines(forceAPICall: Boolean) {
        routines.addSource(repository.getRoutines(forceAPICall)) { resource: Resource<List<Routine>> ->
            if (resource.status == Status.SUCCESS) {
                routines.setValue(
                    Resource.success(
                        resource.data
                    )
                )
            } else {
                routines.setValue(resource)
            }
        }
    }
}