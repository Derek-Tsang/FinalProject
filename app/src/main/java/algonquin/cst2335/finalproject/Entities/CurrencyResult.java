package algonquin.cst2335.finalproject.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CurrencyResult")
public class CurrencyResult {
    /**
     * Unique identifier for the Trivia User.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "CurrencyFrom")
    String CurrencyFrom;
    @ColumnInfo(name = "CurrencyTo")
    String CurrencyTo;
    @ColumnInfo(name = "AmountFrom")
    double AmountFrom;
    @ColumnInfo(name = "AmountTo")
    double AmountTo;

    public String getCurrencyFrom() {
        return CurrencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        CurrencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return CurrencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        CurrencyTo = currencyTo;
    }

    public double getAmountFrom() {
        return AmountFrom;
    }

    public void setAmountFrom(double amountFrom) {
        AmountFrom = amountFrom;
    }

    public double getAmountTo() {
        return AmountTo;
    }

    public void setAmountTo(double amountTo) {
        AmountTo = amountTo;
    }
}
