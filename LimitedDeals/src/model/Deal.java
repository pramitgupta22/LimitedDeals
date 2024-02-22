package model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Deal {

    //AUTO Increment
    static int dealIdCounter = 0;

    private final int itemId;
    private final int dealId;
    private int quantityAllowed;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;

    private int quantityPurchased;

    private boolean isActive;

    public Deal(int itemId, int quantityAllowed, LocalDateTime endTime) {
        this.itemId = itemId;
        this.dealId = dealIdCounter++;
        this.isActive = true;
        this.quantityAllowed = quantityAllowed;
        this.quantityPurchased = 0;
        this.startTime = LocalDateTime.now();
        this.endTime = endTime;
    }

    public int getQuantityPurchased() {
        return quantityPurchased;
    }

    public int getQuantityAllowed() {
        return quantityAllowed;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getDealId() {
        return dealId;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setQuantityAllowed(int quantityAllowed) {
        this.quantityAllowed = quantityAllowed;
    }

    public void invalidate() {
        this.isActive = false;
    }

    public void setQuantityPurchased(int quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }
}
