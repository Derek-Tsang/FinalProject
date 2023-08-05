package algonquin.cst2335.finalproject.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a currency conversion result entity.
 * This class defines the structure and attributes of a currency conversion result.
 *
 * @version 1.0
 * @since 2023-08-04
 */
@Entity(tableName = "CurrencyResult")
public class CurrencyResult {
    /**
     * Unique identifier for the Trivia User.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    long id;

    /**
     * The currency from which the conversion is made.
     */
    @ColumnInfo(name = "CurrencyFrom")
    String CurrencyFrom;

    /**
     * The currency to which the conversion is made.
     */
    @ColumnInfo(name = "CurrencyTo")
    String CurrencyTo;

    /**
     * The amount in the original currency.
     */
    @ColumnInfo(name = "AmountFrom")
    double AmountFrom;

    /**
     * The amount in the target currency after conversion.
     */
    @ColumnInfo(name = "AmountTo")
    double AmountTo;

    /**
     * Gets the unique identifier of the currency result.
     * @return The identifier of the currency result.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the currency result.
     * @param id The identifier to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the currency from which the conversion is made.
     * @return The currency from.
     */
    public String getCurrencyFrom() {
        return CurrencyFrom;
    }

    /**
     * Sets the currency from which the conversion is made.
     * @param currencyFrom The currency from to set.
     */
    public void setCurrencyFrom(String currencyFrom) {
        CurrencyFrom = currencyFrom;
    }

    /**
     * Gets the currency to which the conversion is made.
     * @return The currency to.
     */
    public String getCurrencyTo() {
        return CurrencyTo;
    }

    /**
     * Sets the currency to which the conversion is made.
     * @param currencyTo The currency to set.
     */
    public void setCurrencyTo(String currencyTo) {
        CurrencyTo = currencyTo;
    }

    /**
     * Gets the amount in the original currency.
     * @return The amount in the original currency.
     */
    public double getAmountFrom() {
        return AmountFrom;
    }

    /**
     * Sets the amount in the original currency.
     * @param amountFrom The amount in the original currency to set.
     */
    public void setAmountFrom(double amountFrom) {
        AmountFrom = amountFrom;
    }

    /**
     * Gets the amount in the target currency after conversion.
     * @return The amount in the target currency.
     */
    public double getAmountTo() {
        return AmountTo;
    }

    /**
     * Sets the amount in the target currency after conversion.
     * @param amountTo The amount in the target currency to set.
     */
    public void setAmountTo(double amountTo) {
        AmountTo = amountTo;
    }
}
