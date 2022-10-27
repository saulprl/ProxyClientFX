package remoteproxy;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {

    private int id;
    private Date date;
    private int count;
    private double sale;
    private double profit;

    public Report(int id, Date date, int count, double sale, double profit) {
        this.id = id;
        this.date = date;
        this.count = count;
        this.sale = sale;
        this.profit = profit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

}
