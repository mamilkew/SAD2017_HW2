package dm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import revenueRecognition.MfDate;
import revenueRecognition.Money;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by milkk on 3/29/2017 AD.
 */
public class RevenueRecognitionTest {

    static private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory( "NewPersistenceUnit" );
    }

    @After
    public void tearDown() throws Exception {
        entityManagerFactory.close();
    }

    @Test
    public void testCalculateRevenueRecognitionThreeWayProduct() throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Product threeWayProduct = new Product("Oracle", "DB", new ThreeWayRecognitionStrategy(30, 60));
        Contract contract = new Contract(threeWayProduct, Money.dollars(30000), new MfDate());
        entityManager.persist(threeWayProduct);
        entityManager.persist(contract);
        entityManager.getTransaction().commit();
        entityManager.close();
        int beforeInsert = contract._getRevenueRecognitionSize();
        contract.calculateRecognitions();
        int afterInsert =  contract._getRevenueRecognitionSize();

        Money resultAmount = contract
                .recognizedRevenue(contract.getWhenSigned().addDays(90));

        assertEquals(0, beforeInsert);
        assertEquals(3, afterInsert);
        assertEquals(30000, resultAmount.amount().doubleValue(),0);
    }

    @Test
    public void testCalculateRevenueRecognitionCompleteProduct() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Product completeProduct = new Product("Microsoft", "Software", new CompleteRecognitionStrategy());
        Contract contract = new Contract(completeProduct, Money.dollars(10000), new MfDate());
        entityManager.persist(completeProduct);
        entityManager.persist(contract);
        entityManager.getTransaction().commit();
        entityManager.close();
        contract.calculateRecognitions();
        Money resultAmount = contract
                .recognizedRevenue(contract.getWhenSigned());
        assertEquals(10000, resultAmount.amount().doubleValue(),0);
    }
}