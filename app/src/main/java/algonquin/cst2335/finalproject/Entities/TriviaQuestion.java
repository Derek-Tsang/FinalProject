package algonquin.cst2335.finalproject.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "trivia_questions")
public class TriviaQuestion {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;
    @ColumnInfo(name = "category")
    protected String category;
    @ColumnInfo(name = "type")
    protected String type;
    @ColumnInfo(name = "difficulty")
    protected String difficulty;
    @ColumnInfo(name = "question")
    protected String question;
    @ColumnInfo(name = "correct_answer")
    protected String correctAnswer;

    @ColumnInfo(name = "incorrect_answers")
    protected List<String> IncorrectAnswers = new ArrayList<>(3);

    /**
     * no-ary constructor
     */
    public TriviaQuestion(){

    }

    /**
     * parametered contructor
     * @param category
     * @param type
     * @param difficulty
     * @param question
     * @param correctAnswer
     * @param IncorrectAnswers
     */
    public TriviaQuestion(String category, String type, String difficulty, String question, String correctAnswer, List<String> IncorrectAnswers)
    {

        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.IncorrectAnswers = IncorrectAnswers;
    }

    public long getId(){return id;}

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getType(){
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty(){
        return difficulty;
    }

    public void setDifficulty(String difficulty){
        this.difficulty = difficulty;
    }

    public String getQuestion(){
        return question;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer){
        this.correctAnswer = correctAnswer;
    }

    public List<String> getIncorrectAnswers(){
        return IncorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswer){
        this.IncorrectAnswers = incorrectAnswer;
    }
}
