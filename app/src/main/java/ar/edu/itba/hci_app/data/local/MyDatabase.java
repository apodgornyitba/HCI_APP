package ar.edu.itba.hci_app.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ar.edu.itba.hci_app.data.local.room.LocalRoom;
import ar.edu.itba.hci_app.data.local.room.LocalRoutine;
import ar.edu.itba.hci_app.data.local.room.RoomDao;
import ar.edu.itba.hci_app.data.local.room.LocalDevice;
import ar.edu.itba.hci_app.data.local.room.DeviceDao;
import ar.edu.itba.hci_app.data.local.room.RoutineDao;
import ar.edu.itba.hci_app.data.local.room.speaker.LocalSpeaker;
import ar.edu.itba.hci_app.data.local.room.speaker.SpeakerDao;

@Database(
        entities = {
                LocalRoom.class,
                LocalDevice.class,
                LocalRoutine.class,
                LocalSpeaker.class
        },
        exportSchema = false, version = 1
)
public abstract class MyDatabase extends RoomDatabase {

    abstract public RoomDao roomDao();

    abstract public DeviceDao deviceDao();

    abstract public RoutineDao routineDao();

    abstract public SpeakerDao speakerDao();
}