package dm;

import org.hibernate.annotations.GenericGenerator;
import revenueRecognition.MfDate;
import revenueRecognition.Money;

import javax.persistence.*;

/**
 * Created by mrteera on 1/26/2017 AD.
 */
@Entity
@Table( name = "revenuerecognition" )
public class RevenueRecognition {
    private Long id;

    private Money amount;
    private MfDate date;
//    @ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
//    private Contract contract;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public MfDate getDate() {
        return date;
    }

    public void setDate(MfDate date) {
        this.date = date;
    }

//    public Contract getContract() {
//        return contract;
//    }
//
//    public void setContract(Contract contract) {
//        this.contract = contract;
//    }

    public RevenueRecognition() {
    }

    public RevenueRecognition(Money amount, MfDate date) {
        this.amount = amount;
        this.date = date;
    }

//    public RevenueRecognition(Money amount, MfDate date, Contract contract) {
//        this.amount = amount;
//        this.date = date;
//        this.contract = contract;
//    }

    public boolean isRecognizableBy(MfDate asOf) {
        return asOf.after(date) || asOf.equals(date);
    }
}
