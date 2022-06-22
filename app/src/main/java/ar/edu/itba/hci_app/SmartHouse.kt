package ar.edu.itba.hci_app

import android.app.Application
import androidx.room.Room
import ar.edu.itba.hci_app.data.AppExecutors
import ar.edu.itba.hci_app.data.DeviceRepository
import ar.edu.itba.hci_app.data.RoomRepository
import ar.edu.itba.hci_app.data.RoutineRepository
import ar.edu.itba.hci_app.data.local.MyDatabase
import ar.edu.itba.hci_app.data.remote.ApiClient
import ar.edu.itba.hci_app.data.remote.device.ApiDeviceService
import ar.edu.itba.hci_app.data.remote.device.speaker.ApiSpeakerService
import ar.edu.itba.hci_app.data.remote.room.ApiRoomService
import ar.edu.itba.hci_app.data.remote.routine.ApiRoutineService
import ar.edu.itba.hci_app.data.repository.SpeakerRepository

class SmartHouse : Application() {
    companion object {
        const val DATABASE_NAME = "my-db"
    }

    private lateinit var appExecutors: AppExecutors
    private lateinit var roomRepository: RoomRepository
    private lateinit var deviceRepository: DeviceRepository
    private lateinit var routineRepository: RoutineRepository

    private lateinit var speakerRepository: SpeakerRepository

    fun getRoomRepository() = roomRepository
    fun getDeviceRepository() = deviceRepository
    fun getRoutineRepository() = routineRepository

    fun getSpeakerRepository() = speakerRepository

    override fun onCreate() {
        super.onCreate()

        appExecutors = AppExecutors()

        var roomService: ApiRoomService = ApiClient.create(ApiRoomService::class.java)
        var deviceService: ApiDeviceService = ApiClient.create(ApiDeviceService::class.java)
        var routineService: ApiRoutineService = ApiClient.create(ApiRoutineService::class.java)

        var speakerService: ApiSpeakerService = ApiClient.create(ApiSpeakerService::class.java)

        var database: MyDatabase =
            Room.databaseBuilder(this, MyDatabase::class.java, DATABASE_NAME).build()

        roomRepository = RoomRepository(appExecutors, roomService, database)
        deviceRepository = DeviceRepository(appExecutors, deviceService, database)
        routineRepository = RoutineRepository(appExecutors, routineService, database)

        speakerRepository = SpeakerRepository(appExecutors, speakerService, database)
    }
}