package manager;

import dao.DealDAO;
import model.Deal;
import model.Item;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DealManager {

    Map<Integer, Lock> dealLocks;
    DealDAO dealDAO;

    public DealManager(DealDAO dealDAO) {
        this.dealDAO = dealDAO;
        dealLocks = new HashMap<>();
    }

    public Deal createDeal(Item item, int quantity, int timeDuration, ChronoUnit timeUnit) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = now.plus(timeDuration, timeUnit);
        return dealDAO.createDeal(item.getId(), quantity, endTime);
    }

    public void updateDealEndTime(int dealId, int timeDuration, ChronoUnit timeUnit) {
        if (!dealLocks.containsKey(dealId)) {
             dealLocks.put(dealId, new ReentrantLock());
        }
        try {
            dealLocks.get(dealId).lock();
            LocalDateTime currentEndTime = dealDAO.getDeal(dealId).getEndTime();
            LocalDateTime newEndTime = currentEndTime.plus(timeDuration, timeUnit);
            dealDAO.updateDealEndTime(dealId, newEndTime);
        } finally {
            dealLocks.get(dealId).unlock();
        }
    }

    public void updateDealQuantity(int dealId, int quantity) {
        if (!dealLocks.containsKey(dealId)) {
            dealLocks.put(dealId, new ReentrantLock());
        }
        try {
            dealLocks.get(dealId).lock();
            Deal deal = dealDAO.getDeal(dealId);
            if(deal.getQuantityPurchased() > quantity) {
                throw new IllegalArgumentException("Already sold more items");
            }
            dealDAO.updateDealQuantityAllowed(dealId, quantity);
        } finally {
            dealLocks.get(dealId).unlock();
        }
    }

    public void cancelDeal(int dealId) {
        if (!dealLocks.containsKey(dealId)) {
            dealLocks.put(dealId, new ReentrantLock());
        }
        try {
            dealLocks.get(dealId).lock();
            dealDAO.invalidateDeal(dealId);
        } finally {
            dealLocks.get(dealId).unlock();
        }
    }

    public boolean purchaseDeal(int dealId) {
        if (!dealLocks.containsKey(dealId)) {
            dealLocks.put(dealId, new ReentrantLock());
        }
        try {
            dealLocks.get(dealId).lock();
            Deal deal = dealDAO.getDeal(dealId);
            LocalDateTime endTime = deal.getEndTime();
            int remainingQuantity = deal.getQuantityAllowed() - deal.getQuantityPurchased();
            if(LocalDateTime.now().isAfter(endTime) || remainingQuantity == 0) {
                return false;
            }
            dealDAO.purchaseDeal(dealId, deal.getQuantityPurchased()+1);
            return true;
        } finally {
            dealLocks.get(dealId).unlock();
        }
    }

}
