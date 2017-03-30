package dmsl;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table( name = "PRODUCT" )
public class Product {
//    @Autowired
//    private static EntityManager entityManager;

    private Long id;
    private String name;
    private String type;

    @Transient
    private RecognitionStrategy recognitionStrategy;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    private static List<Product> productList= new ArrayList<Product>();

    public Product() {
    }

    public Product(String name, String type, RecognitionStrategy recognitionStrategy) {
        this.name = name;
        this.type = type;
        this.recognitionStrategy = recognitionStrategy;
    }

//    public Product(String name, RecognitionStrategy recognitionStrategy) {
//        this.id = (long) (productList.size()+1);
//        this.name = name;
//        this.recognitionStrategy = recognitionStrategy;
//    }

//    public void addProduct(Product newProduct)
//    {
//        productList.add(newProduct);
//    }

//    @Transactional
    public static long newWordProcessor(String name) {
        Product newProduct = new Product(name, "WP", new CompleteRecognitionStrategy());
        productList.add(newProduct);
//        entityManager.persist(newProduct);
        return newProduct.getId();

    }

    public static long newSpreadsheet(String name) {
        Product newProduct = new Product(name, "CSV", new ThreeWayRecognitionStrategy(60, 90));
        productList.add(newProduct);
        return newProduct.getId();
    }
    public static long newDatabase(String name) {
        Product newProduct =  new Product(name, "DB", new ThreeWayRecognitionStrategy(30, 60));
        productList.add(newProduct);
        System.out.println("Product list size:" + productList.size());
        return newProduct.getId();
    }

    public static Product read(long productID) {
        System.out.println("productID: "+ productID);
        Product result = null;
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).id == productID) {
                result = productList.get(i);
            }
        }
        return result;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Transactional
    List<RevenueRecognition> calculateRevenueRecognitions(Contract contract) {
        return recognitionStrategy.calculateRevenueRecognitions(contract);
    }
}
