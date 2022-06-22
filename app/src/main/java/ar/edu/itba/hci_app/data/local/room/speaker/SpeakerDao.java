package ar.edu.itba.hci_app.data.local.room.speaker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ar.edu.itba.hci_app.data.local.room.speaker.LocalSpeaker;

@Dao
public abstract class SpeakerDao {

    @Query("SELECT * FROM Speaker")
    public abstract LiveData<List<LocalSpeaker>> findAll();

    @Query("SELECT * FROM Speaker LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<LocalSpeaker>> findAll(int limit, int offset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(LocalSpeaker... device);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<LocalSpeaker> devices);

    @Update
    public abstract void update(LocalSpeaker device);

    @Delete
    public abstract void delete(LocalSpeaker device);

    @Query("DELETE FROM Speaker WHERE id = :id")
    public abstract void delete(String id);

    @Query("DELETE FROM Speaker WHERE id IN (SELECT id FROM Speaker LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);

    @Query("DELETE FROM Speaker")
    public abstract void deleteAll();

    @Query("SELECT * FROM Speaker WHERE id = :id")
    public abstract LiveData<LocalSpeaker> findById(String id);
}