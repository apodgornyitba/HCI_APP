package ar.edu.itba.hci_app.ui.devices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ar.edu.itba.hci_app.data.*
import ar.edu.itba.hci_app.model.Device
import ar.edu.itba.hci_app.ui.RepositoryViewModel

class DeviceViewModel constructor(repository: DeviceRepository) :
    RepositoryViewModel<DeviceRepository>(repository) {

    private val devices = MediatorLiveData<Resource<List<Device>>>()
    private val deviceId = MutableLiveData<String>()
    private var device: LiveData<Resource<Device>>? = null

    init {
        super.repository

        device = Transformations.switchMap(
            deviceId
        ) { deviceId: String? ->
            if (deviceId == null) {
                return@switchMap AbsentLiveData.create<Resource<Device>>()
            } else {
                return@switchMap repository.getDevice(deviceId)
            }
        }
    }

    fun getDevices(): LiveData<Resource<List<Device>>> {
        loadDevices()
        return devices
    }

    fun getDevice(): LiveData<Resource<Device>>? {
        return device
    }

    fun addDevice(device: Device?): LiveData<Resource<Device>>? {
        return repository.addDevice(device)
    }

    fun modifyDevice(device: Device?): LiveData<Resource<Device>>? {
        return repository.modifyDevice(device)
    }

    fun deleteDevice(device: Device?): LiveData<Resource<Void>>? {
        return repository.deleteDevice(device)
    }

    fun setDeviceId(deviceId: String) {
        if (this.deviceId.value != null && deviceId == this.deviceId.value) {
            return
        }
        this.deviceId.value = deviceId
    }

    private fun loadDevices() {
        devices.addSource(repository.devices) { resource: Resource<List<Device>> ->
            if (resource.status == Status.SUCCESS) {
                devices.setValue(
                    Resource.success(
                        resource.data
                    )
                )
            } else {
                devices.setValue(resource)
            }
        }
    }
}