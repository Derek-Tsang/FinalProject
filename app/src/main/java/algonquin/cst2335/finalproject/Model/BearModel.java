package algonquin.cst2335.finalproject.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import algonquin.cst2335.finalproject.Entities.Bear;

/**
 * ViewModel class for managing Bear-related data.
 * @author min
 * @version 1.0
 */
public class BearModel extends ViewModel {
    /**
     * MutableLiveData to hold a list of Bear images.
     */
    public MutableLiveData<List<Bear>> Images = new MutableLiveData<java.util.List<Bear>>();
    /**
     * MutableLiveData to hold the currently selected Bear image.
     */
    public static MutableLiveData<Bear> selectedImage = new MutableLiveData<>();
}
