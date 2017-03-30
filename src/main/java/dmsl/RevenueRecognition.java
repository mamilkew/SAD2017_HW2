package dmsl;

import org.hibernate.annotations.GenericGenerator;
import revenueRecognition.MfDate;
import revenueRecognition.Money;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "revenuerecognition" )
public class RevenueRecognition {
    private Long id;
    private Money amount;
    private MfDate date;

    public RevenueRecognition() {
    }

    public RevenueRecognition(Money amount, MfDate date) {
        this.amount = amount;
        this.date = date;
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

    public boolean isRecognizableBy(MfDate asOf) {
        return asOf.after(date) || asOf.equals(date);
    }
}
