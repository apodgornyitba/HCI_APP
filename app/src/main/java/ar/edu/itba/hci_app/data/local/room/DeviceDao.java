package ar.edu.itba.hci_app.data.local.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class DeviceDao {

    @Query("SELECT * FROM Device")
    public abstract LiveData<List<LocalDevice>> findAll();

    @Query("SELECT * FROM Device LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<LocalDevice>> findAll(int limit, int offset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(LocalDevice... device);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<LocalDevice> devices);

    @Update
    public abstract void update(LocalDevice device);

    @Delete
    public abstract void delete(LocalDevice device);

    @Query("DELETE FROM Device WHERE id = :id")
    public abstract void delete(String id);

    @Query("DELETE FROM Device WHERE id IN (SELECT id FROM Device LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);

    @Query("DELETE FROM Device")
    public abstract void deleteAll();

    @Query("SELECT * FROM Device WHERE id = :id")
    public abstract LiveData<LocalDevice> findById(String id);
}