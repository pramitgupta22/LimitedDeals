import dao.DealDAO;
import manager.DealManager;
import model.Deal;
import model.Item;

import java.time.temporal.ChronoUnit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DealDAO dealsDao = DealDAO.getInstance();
        DealManager manager = new DealManager(dealsDao);

        Item i1 = new Item(123);
        Deal d1 = manager.createDeal(i1, 2, 10, ChronoUnit.MINUTES);

        boolean purchased;
        purchased = manager.purchaseDeal(d1.getDealId());
        System.out.println("First purchase");
        System.out.println(purchased);
        purchased = manager.purchaseDeal(d1.getDealId());
        System.out.println("Second purchase");
        System.out.println(purchased);
        purchased = manager.purchaseDeal(d1.getDealId());
        System.out.println("Third purchase");
        System.out.println(purchased);
    }
}


/*

    Item
        - price
        - id

    Deal
       - ItemId
       - quantityRemaining
       - quantityAllowed
       - startTime
       - untilTime
       - isActive = true
       + updateQuantity(quantityAllowed)
       + updateEndTime(timeDuration, timeUnit)
       + invalidateDeal()
       + reduceAvailableItem()

    DealsManager
       - List<Deals>
       - Map <dealId, List<userId>>
       + crate(timeDuration, timeUnit, quantityAllowed, Item)
       + purchaseItem(userId, dealId)
       + endDeal(dealId)




    User

 */

