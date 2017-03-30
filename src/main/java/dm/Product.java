package dm;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "PRODUCT" )
public class Product {
    private Long id;
    private String name;
    private String type;

    @Transient
    private RecognitionStrategy recognitionStrategy;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    private static List<Product> productList= new ArrayList<Product>();

//    public Product() {
//        super();
//    }
    public Product() {
    }

    public Product(String name, String type, RecognitionStrategy recognitionStrategy) {
        this.name = name;
        this.type = type;
        this.recognitionStrategy = recognitionStrategy;
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


    // 	public void addProduct(Product newProduct)
//    {
//    	productList.add(newProduct);
//    }
//
//    public static Product newWordProcessor(String name) {
//        Product newProduct = new Product(name, new CompleteRecognitionStrategy());
//        productList.add(newProduct);
//        return newProduct;
//    }

//    public static Product newSpreadsheet(String name) {
//        Product newProduct = new Product(name, new ThreeWayRecognitionStrategy(60, 90));
//        productList.add(newProduct);
//        return newProduct;
//    }
//    public Product newDatabase(String name) {
//        Product newProduct =  new Product(name, new ThreeWayRecognitionStrategy(30, 60));
//        productList.add(newProduct);
//        return newProduct;
//    }

    void calculateRevenueRecognitions(Contract contract) {
        recognitionStrategy.calculateRevenueRecognitions(contract);
    }
}
