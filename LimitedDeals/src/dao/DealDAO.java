package dao;

import model.Deal;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DealDAO {

    private static volatile DealDAO INSTANCE;
    Map<Integer, Deal> dealsDB;

    private DealDAO() {
        dealsDB = new HashMap<>();
    }

    public static DealDAO getInstance() {
        if(INSTANCE == null) {
            synchronized (DealDAO.class) {
                if(INSTANCE == null) {
                    //
                    INSTANCE = new DealDAO();
                    //
                }
            }
        }
        return INSTANCE;
    }

    public Deal getDeal(final int dealId) {
        // Select * from deals where dealId = <dealId>
        return dealsDB.get(dealId);
    }

    public Deal createDeal(int itemId, int quantity, LocalDateTime endTime) {
        // Insert into deals (it)
        Deal deal = new Deal(itemId, quantity, endTime);
        dealsDB.put(deal.getDealId(), deal);
        return deal;
    }

    public void updateDealEndTime(int dealId, LocalDateTime endDateTime) {
        // Update deal set endTime = <endDate> where Id = <dealId>
        Deal deal = dealsDB.get(dealId);
        deal.setEndTime(endDateTime);
    }

    public void updateDealQuantityAllowed(int dealId, int quantity) {
        // Update deal set maxQuantity = <quantity> where Id = <dealId>
        Deal deal = dealsDB.get(dealId);
        deal.setQuantityAllowed(quantity);
    }

    public void invalidateDeal(int dealId) {
        // Update deal set isActive = false where id = <dealId>
        Deal deal = dealsDB.get(dealId);
        deal.invalidate();
    }

    public void purchaseDeal(int dealId, int quantityPurchased) {
        // Update deal set quantityPurchased = quantityPurchased where dealId = <dealId>
        Deal deal = dealsDB.get(dealId);
        deal.setQuantityPurchased(quantityPurchased);
    }
}
