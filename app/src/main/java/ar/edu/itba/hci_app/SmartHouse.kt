package ar.edu.itba.hci_app

import android.app.Application
import androidx.room.Room
import ar.edu.itba.hci_app.data.AppExecutors
import ar.edu.itba.hci_app.data.RoomRepository
import ar.edu.itba.hci_app.data.local.MyDatabase
import ar.edu.itba.hci_app.data.remote.ApiClient
import ar.edu.itba.hci_app.data.remote.room.ApiRoomService

class SmartHouse : Application() {
    companion object {
        const val DATABASE_NAME = "my-db"
    }

    private lateinit var appExecutors: AppExecutors
    private lateinit var roomRepository: RoomRepository

    fun getRoomRepository() = roomRepository

    override fun onCreate() {
        super.onCreate()

        appExecutors = AppExecutors()

        var roomService: ApiRoomService = ApiClient.create(ApiRoomService::class.java)

        var database: MyDatabase =
            Room.databaseBuilder(this, MyDatabase::class.java, DATABASE_NAME).build()

        roomRepository = RoomRepository(appExecutors, roomService, database)
    }
}