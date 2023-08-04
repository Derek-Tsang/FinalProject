package algonquin.cst2335.finalproject.Entities;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bear {
    @PrimaryKey(autoGenerate = true) //increment the ids for us
    @ColumnInfo(name = "imageId")
    public int id;
    @ColumnInfo(name = "widthGenerated")
    public String widthGenerated;
    @ColumnInfo(name = "heightGenerated")
    public String heightGenerated;
    public Bear() {
        // Empty constructor required by Room
    }
    public Bear(String w, String h) {
        this.widthGenerated = w;
        this.heightGenerated = h;
    }
    public String getWidthGenerated() {
        return widthGenerated;
    }
    public String getHeightGenerated() {
        return heightGenerated;
    }
}

