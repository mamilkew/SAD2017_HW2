package dm;

import org.hibernate.annotations.GenericGenerator;
import revenueRecognition.MfDate;
import revenueRecognition.Money;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrteera on 1/26/2017 AD.
 */
@Entity
@Table( name = "CONTRACT" )
public class Contract {
    private Long id;
    private Product product;
    private Money revenue;
    private MfDate whenSigned;

    public Contract() {}

    public Contract(Product product, Money revenue, MfDate whenSigned) {
        this.product = product;
        this.revenue = revenue;
        this.whenSigned = whenSigned;
    }

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

    public void setRevenue(Money revenue) {
        this.revenue = revenue;
    }

    public void setWhenSigned(MfDate whenSigned) {
        this.whenSigned = whenSigned;
    }

    public Money getRevenue() {
        return revenue;
    }

    public MfDate getWhenSigned() {
        return whenSigned;
    }

//    @OneToMany
    private List<RevenueRecognition> revenueRecognitions = new ArrayList<RevenueRecognition>();

    public static Contract addNewContract(Product product, Money revenue, MfDate whenSigned)
    {
        Contract newContract = new Contract(product, revenue, whenSigned);
        return newContract;
    }

    public Money recognizedRevenue(MfDate asOf) {
        Money result = Money.dollars(0);
//        Iterator<RevenueRecognition> it = revenueRecognitions.iterator();
//        while (it.hasNext()) {
//            RevenueRecognition r = (RevenueRecognition) it.next();
//            if (r.isRecognizableBy(asOf))
//                result = result.add(r.getAmount());
//        }
        EntityManagerFactory entityManagerFactory;
        entityManagerFactory = Persistence.createEntityManagerFactory( "NewPersistenceUnit" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<RevenueRecognition> query_result = entityManager.createQuery( "from RevenueRecognition ", RevenueRecognition.class ).getResultList();
        for ( RevenueRecognition revenuerecognition : query_result ) {
            if (revenuerecognition.isRecognizableBy(asOf))
            result = result.add(revenuerecognition.getAmount());
        }
        entityManagerFactory.close();
        return result;
    }

    public void calculateRecognitions() {
        product.calculateRevenueRecognitions(this);
    }


    public void addRevenueRecognition(RevenueRecognition revenueRecognition) {
        EntityManagerFactory entityManagerFactory;
        entityManagerFactory = Persistence.createEntityManagerFactory( "NewPersistenceUnit" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(revenueRecognition);
        entityManager.getTransaction().commit();
        entityManagerFactory.close();
//        revenueRecognitions.add(revenueRecognition);
    }

    public int _getRevenueRecognitionSize()
    {
        EntityManagerFactory entityManagerFactory;
        entityManagerFactory = Persistence.createEntityManagerFactory( "NewPersistenceUnit" );
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<RevenueRecognition> result = entityManager.createQuery( "from RevenueRecognition ", RevenueRecognition.class ).getResultList();
        entityManagerFactory.close();
        return result.size();
//        return revenueRecognitions.size();
    }
}
