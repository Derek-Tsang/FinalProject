package algonquin.cst2335.finalproject.Model;

import algonquin.cst2335.finalproject.Entities.CurrencyResult;

public interface CurrencyDAO {
    void deleteCurrency(CurrencyResult selected);

    void insertCurrency(CurrencyResult selected);
}
