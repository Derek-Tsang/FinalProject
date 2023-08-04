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
    public int widthGenerated;

    @ColumnInfo(name = "heightGenerated")
    public int heightGenerated;

    @ColumnInfo(name = "imagePath")
    public String imagePath;
    public Bear() {
        // Empty constructor required by Room
    }
    public Bear(int w, int h, String imagePath) {
        this.widthGenerated = w;
        this.heightGenerated = h;
        this.imagePath = imagePath;
    }
    public int getWidthGenerated() {
        return widthGenerated;
    }
    public int getHeightGenerated() {
        return heightGenerated;
    }

    public String getPath() {
        return imagePath;
    }

}

