package algonquin.cst2335.finalproject.Entities;
public class CurrencyResult {
    String CurrencyFrom;
    String CurrencyTo;
    double AmountFrom;
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
