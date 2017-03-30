package dmsl;

import java.util.List;


public abstract class RecognitionStrategy {
    abstract List<RevenueRecognition> calculateRevenueRecognitions(Contract contract);
}
