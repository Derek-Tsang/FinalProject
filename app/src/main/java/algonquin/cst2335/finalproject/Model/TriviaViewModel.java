package algonquin.cst2335.finalproject.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.Entities.TriviaQuestion;

public class TriviaViewModel extends ViewModel {

    public MutableLiveData<List<TriviaQuestion>> questions = new MutableLiveData<>();
    public MutableLiveData<TriviaQuestion> selectQuestion = new MutableLiveData< >();
}
