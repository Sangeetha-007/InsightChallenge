import java.util.ArrayList;

/**
 * Created by Sangeetha on 10/28/17.
 */
public class Recipient
{
    // every recipient has these four attributes
    private String ID;
    private String zip;
    private String trans_date;
    private double trans_amt;


    // contructor
    public Recipient(String id, String z, String date, double amount)
    {
        ID = id;
        zip = z;
        trans_date = date;
        trans_amt = amount;
    }

    public String getID() { return ID; }
    public String getZip() { return zip; }
    public String getTrans_date() { return trans_date; }
    public double getTrans_amt() { return trans_amt; }


    public void print_info()
    {
        System.out.println("CMTE_ID: " + ID);
        System.out.println("ZIPCODE: " + zip);
        System.out.println("TRANSACTION_DT: " + trans_date);
        System.out.println("TRANSACTION_AMT: " + trans_amt);
        System.out.println();
    }

}
