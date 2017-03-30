package dmsl;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class AbstractRecognitionTest extends AbstractTransactionalJUnit4SpringContextTests {

//    @SpringBootApplication
//    static class TestConfig {}

    @BeforeTransaction
    public void setupData() throws Exception {

        deleteFromTables("product");
    }
}

