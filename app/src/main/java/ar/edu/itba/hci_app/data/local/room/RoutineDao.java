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
public abstract class RoutineDao {

    @Query("SELECT * FROM Routine")
    public abstract LiveData<List<LocalRoutine>> findAll();

    @Query("SELECT * FROM Routine LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<LocalRoutine>> findAll(int limit, int offset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(LocalRoutine... device);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<LocalRoutine> devices);

    @Update
    public abstract void update(LocalRoutine device);

    @Delete
    public abstract void delete(LocalRoutine device);

    @Query("DELETE FROM Routine WHERE id = :id")
    public abstract void delete(String id);

    @Query("DELETE FROM Routine WHERE id IN (SELECT id FROM Routine LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);

    @Query("DELETE FROM Routine")
    public abstract void deleteAll();

    @Query("SELECT * FROM Routine WHERE id = :id")
    public abstract LiveData<LocalRoutine> findById(String id);
}