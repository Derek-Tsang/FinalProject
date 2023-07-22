package algonquin.cst2335.finalproject.Entities;

import java.util.ArrayList;
import java.util.List;

/**
 * This class representing a single trivia question.
 */
public class TriviaQuestion {

    /**
     * The category of the trivia question.
     */
    protected String category;

    /**
     * The type of the trivia question
     */
    protected String type;

    /**
     * The difficulty level of the trivia question.
     */
    protected String difficulty;

    /**
     * The actual question text.
     */
    protected String question;

    /**
     * The correct answer for the trivia question.
     */
    protected String correctAnswer;

    /**
     * List of incorrect answers for the trivia question.
     */
    protected List<String> IncorrectAnswers = new ArrayList<>(3);

    /**
     * no-ary constructor
     */
    public TriviaQuestion(){}

    /**
     * Get the category of the trivia question.
     *
     * @return The category of the trivia question.
     */
    public String getCategory(){
        return category;
    }

    /**
     * Set the category of the trivia question.
     *
     * @param category The category to set for the trivia question.
     */
    public void setCategory(String category){
        this.category = category;
    }

    /**
     * Get the type of the trivia question.
     *
     * @return The type of the trivia question.
     */
    public String getType(){
        return type;
    }

    /**
     * Set the type of the trivia question.
     *
     * @param type The type to set for the trivia question.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the difficulty level of the trivia question.
     *
     * @return The difficulty level of the trivia question.
     */
    public String getDifficulty(){
        return difficulty;
    }

    /**
     * Set the difficulty level of the trivia question.
     *
     * @param difficulty The difficulty level to set for the trivia question.
     */
    public void setDifficulty(String difficulty){
        this.difficulty = difficulty;
    }


    /**
     * Get the actual question text of the trivia question.
     *
     * @return The question text of the trivia question.
     */
    public String getQuestion(){
        return question;
    }

    /**
     * Set the actual question text of the trivia question.
     *
     * @param question The question text to set for the trivia question.
     */
    public void setQuestion(String question){
        this.question = question;
    }

    /**
     * Get the correct answer for the trivia question.
     *
     * @return The correct answer for the trivia question.
     */
    public String getCorrectAnswer(){
        return correctAnswer;
    }

    /**
     * Set the correct answer for the trivia question.
     *
     * @param correctAnswer The correct answer to set for the trivia question.
     */
    public void setCorrectAnswer(String correctAnswer){
        this.correctAnswer = correctAnswer;
    }


    /**
     * Get the list of incorrect answers for the trivia question.
     *
     * @return The list of incorrect answers for the trivia question.
     */
    public List<String> getIncorrectAnswers(){
        return IncorrectAnswers;
    }

    /**
     * Set the list of incorrect answers for the trivia question.
     *
     * @param incorrectAnswer The list of incorrect answers to set for the trivia question.
     */
    public void setIncorrectAnswers(List<String> incorrectAnswer){
        this.IncorrectAnswers = incorrectAnswer;
    }
}
