package algonquin.cst2335.finalproject.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import algonquin.cst2335.finalproject.Entities.Bear;


public class BearModel extends ViewModel {

    public MutableLiveData<List<Bear>> Images = new MutableLiveData<java.util.List<Bear>>();
    public static MutableLiveData<Bear> selectedImage = new MutableLiveData<>();
}
