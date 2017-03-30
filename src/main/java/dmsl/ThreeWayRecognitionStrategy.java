package dmsl;

import revenueRecognition.Money;

import java.util.ArrayList;
import java.util.List;


public class ThreeWayRecognitionStrategy extends RecognitionStrategy {
    private int firstRecognitionOffset;
    private int secondRecognitionOffset;

//    private List<RevenueRecognition> revenueRecognitions;

    public ThreeWayRecognitionStrategy(int firstRecognitionOffset,int secondRecognitionOffset) {
        this.firstRecognitionOffset = firstRecognitionOffset;
        this.secondRecognitionOffset = secondRecognitionOffset;
    }

    List<RevenueRecognition> calculateRevenueRecognitions(Contract contract) {
        List<RevenueRecognition> revenueRecognitions = new ArrayList<RevenueRecognition>();
        Money[] allocation = contract.getRevenue().allocate(3);
        RevenueRecognition r1 = contract.addRevenueRecognition(new RevenueRecognition(allocation[0], contract.getWhenSigned()));
        RevenueRecognition r2 = contract.addRevenueRecognition(new RevenueRecognition(allocation[1], contract.getWhenSigned().addDays(firstRecognitionOffset)));
        RevenueRecognition r3 = contract.addRevenueRecognition(new RevenueRecognition(allocation[2], contract.getWhenSigned().addDays(secondRecognitionOffset)));
        revenueRecognitions.add(r1);
        revenueRecognitions.add(r2);
        revenueRecognitions.add(r3);
        return revenueRecognitions;
    }
}
