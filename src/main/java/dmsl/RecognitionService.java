package dmsl;

import revenueRecognition.MfDate;
import revenueRecognition.Money;


public interface RecognitionService {
    long insertProductWP(String name);
    long insertProductSpreadsheet(String name);
    int countRecognitions();
    void saveRevenueRecognitions(RevenueRecognition rr);
    Contract insertContract(long productID, Money revenue, MfDate whenSigned);
    void calculateRevenueRecognitions(long contractNumber);
    Money recognizedRevenue(long contractNumber, MfDate asOf);
    void recognizedRevenueComplete(RevenueRecognition revenueRecognition);
    Money getRevenueRecognition(MfDate asOf);
    boolean checkRevenue();
}
