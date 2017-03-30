package dmsl;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

//@Repository
//@Transactional(readOnly = true)
public class CompleteRecognitionStrategy extends RecognitionStrategy {
//    @PersistenceContext
//    private EntityManager entityManager;

    @Autowired
    private RecognitionService rs;


//    @Transactional
    List<RevenueRecognition> calculateRevenueRecognitions(Contract contract) {
        List<RevenueRecognition> revenueRecognitions = new ArrayList<RevenueRecognition>();
        RevenueRecognition revenueRecognition;
        revenueRecognition = new RevenueRecognition(contract.getRevenue(),contract.getWhenSigned());
        revenueRecognitions.add(revenueRecognition);
        return revenueRecognitions;
//        rs.recognizedRevenueComplete(revenueRecognition);
    }
}
