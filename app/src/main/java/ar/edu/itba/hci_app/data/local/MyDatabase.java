package ar.edu.itba.hci_app.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ar.edu.itba.hci_app.data.local.room.LocalRoom;
import ar.edu.itba.hci_app.data.local.room.RoomDao;
import ar.edu.itba.hci_app.data.local.room.LocalDevice;
import ar.edu.itba.hci_app.data.local.room.DeviceDao;

@Database(
        entities = {
                LocalRoom.class,
                LocalDevice.class
        },
        exportSchema = false, version = 1
)
public abstract class MyDatabase extends RoomDatabase {

    abstract public RoomDao roomDao();

    abstract public DeviceDao deviceDao();
}