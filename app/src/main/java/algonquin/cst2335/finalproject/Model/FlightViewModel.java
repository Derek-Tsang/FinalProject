package algonquin.cst2335.finalproject.Model;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.Entities.FlightInfo;
/**
 * ViewModel class for managing flight information.
 */
public class FlightViewModel extends ViewModel {
    /**
     * LiveData object holding the list of flights.
     */
    public MutableLiveData<ArrayList<FlightInfo>> flights = new MutableLiveData<>();

    /**
     * LiveData object holding the list of favorite flights.
     */
    public MutableLiveData<ArrayList<FlightInfo>> favoriteFlights = new MutableLiveData<>();


    /**
     * LiveData object holding the String value of search keyword.
     */
    public MutableLiveData<String> searchKeyword = new MutableLiveData<>();

}
