package algonquin.cst2335.finalproject.Entities;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a Bear entity with attributes for image information.
 *
 * This class is used in conjunction with Room database to store bear image data.
 * Each bear has an auto-generated unique ID, width and height values, and an image path.
 * @author Min
 * @version 1.0
 */
@Entity
public class Bear {

    /**
     * The unique identifier for the bear image.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "imageId")
    public int id;

    /**
     * The width value of the generated bear image in pixels.
     */
    @ColumnInfo(name = "widthGenerated")
    public int widthGenerated;

    /**
     * The height value of the generated bear image in pixels.
     */
    @ColumnInfo(name = "heightGenerated")
    public int heightGenerated;

    /**
     * The file path to the bear image.
     */
    @ColumnInfo(name = "imagePath")
    public String imagePath;

    /**
     * Default constructor. Required by Room.
     */
    public Bear() {
        // Empty constructor required by Room
    }

    /**
     * Parameterized constructor to create a Bear instance with specified attributes.
     *
     * @param w The width of the generated bear image.
     * @param h The height of the generated bear image.
     * @param imagePath The file path to the bear image.
     */
    public Bear(int w, int h, String imagePath) {
        this.widthGenerated = w;
        this.heightGenerated = h;
        this.imagePath = imagePath;
    }

    /**
     * Retrieves the width of the generated bear image.
     *
     * @return The width of the bear image in pixels.
     */
    public int getWidthGenerated() {
        return widthGenerated;
    }

    /**
     * Retrieves the height of the generated bear image.
     *
     * @return The height of the bear image in pixels.
     */
    public int getHeightGenerated() {
        return heightGenerated;
    }

    /**
     * Retrieves the file path to the bear image.
     *
     * @return The file path to the bear image.
     */
    public String getPath() {
        return imagePath;
    }
}


