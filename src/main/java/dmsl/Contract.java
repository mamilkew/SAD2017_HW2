package dmsl;

import org.hibernate.annotations.GenericGenerator;
import revenueRecognition.MfDate;
import revenueRecognition.Money;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "CONTRACT" )
public class Contract {
    private long id;
    private Product product;
    private Money revenue;
    private MfDate whenSigned;

    public Contract() {}

    public Contract(Product product, Money revenue, MfDate whenSigned) {
        this.product = product;
        this.revenue = revenue;
        this.whenSigned = whenSigned;
    }

//    public Contract(Product product, Money revenue, MfDate whenSigned) {
//        this.id = (long) (allContracts.size()+1);
//        this.product = product;
//        this.revenue = revenue;
//        this.whenSigned = whenSigned;
//    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Money getRevenue() {
        return revenue;
    }

    public void setRevenue(Money revenue) {
        this.revenue = revenue;
    }

    public MfDate getWhenSigned() {
        return whenSigned;
    }

    public void setWhenSigned(MfDate whenSigned) {
        this.whenSigned = whenSigned;
    }

    private static List <RevenueRecognition> revenueRecognitions = new ArrayList<RevenueRecognition>();
//    private static List <Contract> allContracts = new ArrayList<Contract>();

//    public static Contract addNewContract(Product product, Money revenue, MfDate whenSigned)
//    {
//        Contract newContract = new Contract(product, revenue, whenSigned);
//        allContracts.add(newContract);
//        return newContract;
//    }

    public static Contract addNewContract(Product product, Money revenue, MfDate whenSigned)
    {
        Contract newContract = new Contract(product, revenue, whenSigned);
        return newContract;
    }

    public static Contract read(long contractID) {
        EntityManagerFactory entityManagerFactory;
        entityManagerFactory = Persistence.createEntityManagerFactory( "NewPersistenceUnit" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Contract> allContracts = entityManager.createQuery( "from Contract ", Contract.class ).getResultList();
        System.out.println("contractID: "+ contractID);
        Contract result = null;
        for (int i = 0; i < allContracts.size(); i++) {
            if (allContracts.get(i).id == contractID) {
                result = allContracts.get(i);
            }
        }
        System.out.println("Contracts size: " + allContracts.size());
        System.out.println(result._getRevenueRecognitionSize());
        return result;
    }

//    public static boolean deleteContract(long contractID) {
//        Contract result = null;
//        boolean deleteStatus = false;
//        for (int i = 0; i < allContracts.size(); i++) {
//            if (allContracts.get(i).id == contractID) {
//                result = allContracts.get(i);
//                deleteStatus = true;
//            }
//        }
//        allContracts.remove(result);
//        return deleteStatus;
//    }


//    public Money recognizedRevenue(MfDate asOf) {
//        Money result = Money.dollars(0);
//        Iterator < RevenueRecognition > it = revenueRecognitions.iterator();
//        while (it.hasNext()) {
//            RevenueRecognition r = (RevenueRecognition) it.next();
//            if (r.isRecognizableBy(asOf))
//                result = result.add(r.getAmount());
//        }
//        return result;
//    }

//    private RecognitionService rs;
//
//    @Transactional
//    public Money recognizedRevenue(MfDate asOf) {
//        Money result = Money.dollars(0);
//        return rs.getRevenueRecognition(asOf);
//        List<RevenueRecognition> query_result = entityManager.createQuery( "from RevenueRecognition ", RevenueRecognition.class ).getResultList();
//        for ( RevenueRecognition revenuerecognition : query_result ) {
//            if (revenuerecognition.isRecognizableBy(asOf))
//                result = result.add(revenuerecognition.getAmount());
//        }
//        return result;
//    }

    @Transactional
    public List<RevenueRecognition> calculateRecognitions() {
        return product.calculateRevenueRecognitions(this);
    }

//    public void addRevenueRecognition(RevenueRecognition revenueRecognition) {
//        revenueRecognitions.add(revenueRecognition);
//    }

    public RevenueRecognition addRevenueRecognition(RevenueRecognition revenueRecognition) {
//        entityManager.persist(revenueRecognition);
        return revenueRecognition;
    }

    public int _getRevenueRecognitionSize()
    {
        EntityManagerFactory entityManagerFactory;
        entityManagerFactory = Persistence.createEntityManagerFactory( "NewPersistenceUnit" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<RevenueRecognition> result = entityManager.createQuery( "from RevenueRecognition ", RevenueRecognition.class ).getResultList();
        entityManagerFactory.close();
        return result.size();
    }

    public String _getAdministratorEmailAddress() {
        // TODO Auto-generated method stub
        return null;
    }

    public static int countRecognitions() {
        // TODO Auto-generated method stub
        return revenueRecognitions.size();
    }
}
