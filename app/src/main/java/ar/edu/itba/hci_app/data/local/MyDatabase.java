package ar.edu.itba.hci_app.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ar.edu.itba.hci_app.data.local.room.LocalRoom;
import ar.edu.itba.hci_app.data.local.room.RoomDao;

@Database(entities = {LocalRoom.class }, exportSchema = false, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    abstract public RoomDao roomDao();
}