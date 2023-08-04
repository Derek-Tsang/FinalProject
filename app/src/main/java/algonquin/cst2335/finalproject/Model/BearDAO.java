package algonquin.cst2335.finalproject.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import algonquin.cst2335.finalproject.Entities.Bear;


@Dao
public interface BearDAO {
    @Insert
    long insertImage(Bear m);

    @Query ("Select * from Bear")
    List<Bear> getAllImage();

    @Delete
    void deleteImage(Bear m);
}

