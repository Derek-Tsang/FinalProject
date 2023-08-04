package algonquin.cst2335.finalproject.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.Entities.Bear;


public class BearModel extends ViewModel {

    public MutableLiveData<ArrayList<Bear>> Images = new MutableLiveData<>();
    public static MutableLiveData<Bear> selectedImage = new MutableLiveData<>();
}