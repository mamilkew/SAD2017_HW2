package dmsl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import revenueRecognition.*;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RecognitionServiceImpl implements RecognitionService {
//    @PersistenceContext(name="NewPersistenceUnit")
//    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

    public Contract findContractByID(long contractID) {
        return entityManager.find(Contract.class, contractID);
    }

    @Transactional
    public void calculateRevenueRecognitions(long contractNumber) {
        Contract contract = findContractByID(contractNumber);
        List<RevenueRecognition> revenueRecognitions = contract.calculateRecognitions();
        for ( RevenueRecognition revenuerecognition : revenueRecognitions ) {
            entityManager.persist(revenuerecognition);
        }
//        RevenueRecognition revenueRecognition;
//        revenueRecognition = new RevenueRecognition(contract.getRevenue(),contract.getWhenSigned());
//        entityManager.persist(revenueRecognition);
    }

    @Transactional
    public void saveRevenueRecognitions(RevenueRecognition rr) {
        entityManager.persist(rr);
    }

    @Transactional
    public Money recognizedRevenue(long contractNumber, MfDate asOf) {
//        return Contract.read(contractNumber).recognizedRevenue(asOf);
        Contract contract =  entityManager.find(Contract.class, contractNumber);
//        return contract.recognizedRevenue(asOf);
        return getRevenueRecognition(asOf);
    }

    @Transactional
    public Money getRevenueRecognition(MfDate asOf) {
        Money result = Money.dollars(0);
        List<RevenueRecognition> query_result = entityManager.createQuery( "from RevenueRecognition ", RevenueRecognition.class ).getResultList();
        for ( RevenueRecognition revenuerecognition : query_result ) {
            if (revenuerecognition.isRecognizableBy(asOf))
                result = result.add(revenuerecognition.getAmount());
        }
        return result;
    }

    @Transactional
    public void recognizedRevenueComplete(RevenueRecognition revenueRecognition) {
        entityManager.persist(revenueRecognition);
    }

    @Transactional
    public long insertProductWP(String name)
    {
//        return Product.newWordProcessor(name);
        Product product = new Product(name, "WP", new CompleteRecognitionStrategy());
        entityManager.persist(product);
        return product.getId();
    }

    public long insertProductDatabase(String name)
    {
        return Product.newDatabase(name);
    }

    @Transactional
    public long insertProductSpreadsheet(String name)
    {
        Product product = new Product(name, "CSV", new ThreeWayRecognitionStrategy(30, 60));
        entityManager.persist(product);
        return product.getId();
//        return Product.newSpreadsheet(name);
    }

    public Product findProductByID(long productID) {
        return entityManager.find(Product.class, productID);
    }

    public Contract insertContract(long productID, Money revenue, MfDate whenSigned)
    {
        Contract newContract = new Contract(findProductByID(productID), revenue, whenSigned);
        entityManager.persist(newContract);
        return newContract;
//        return  Contract.addNewContract(Product.read(productID), revenue, whenSigned);
    }

    public int countRecognitions() {
//        List<RevenueRecognition> query_result = entityManager.createQuery( "from RevenueRecognition ", RevenueRecognition.class ).getResultList();
        List<RevenueRecognition> query_result = entityManager.createQuery( "from RevenueRecognition ", RevenueRecognition.class ).getResultList();
        System.out.println("bbbbbbbbbbbbbbbbbbbbb");
        System.out.println(query_result);
        System.out.println("bbbbbbbbbbbbbbbbbbbbb");
        return query_result.size();
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
//        cq.select(cb.count(cq.from(RevenueRecognition.class)));

//        return entityManager.createQuery(cq).getSingleResult();
//        return entityManager.createQuery(cq).getResultList().size();
//        return Contract.countRecognitions();
    }

    public boolean checkRevenue() {
        RevenueRecognition rr = entityManager.find(RevenueRecognition.class, 1);
        if (rr != null) {
            return true;
        } else {
            return false;
        }
    }
}
