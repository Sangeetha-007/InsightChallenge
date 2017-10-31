import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sangeetha on 10/30/2017.
 */
public class Info {

    ArrayList<Double> amounts;
    private int median;
    private int num_of_transactions;
    private int total_contributions;
    private String ID;

    public Info(ArrayList<Recipient> recipients, int i)
    {
        median = (int)recipients.get(i).getTrans_amt();
        num_of_transactions = 1;
        total_contributions = median;
        amounts = new ArrayList<>();
        amounts.add(recipients.get(i).getTrans_amt());
        ID = recipients.get(i).getID();
    }

    public void update(ArrayList<Recipient> recipients, int i)
    {
        amounts.add(recipients.get(i).getTrans_amt());

        total_contributions += amounts.get(amounts.size()-1);
        num_of_transactions++;
        ID = recipients.get(i).getID();

        Collections.sort(amounts); // sort to find median

        int size = amounts.size();
        if(size % 2 != 0) // if size is odd
            median = amounts.get(size/2).intValue(); // median is just middle number
        else  // if size is even, median is the average of the middle two numbers
        {
            // add the middle two numbers
            double total = amounts.get(size/2) + amounts.get(size/2 - 1);
            median = (int)Math.round(total / 2);
        }
    }

    public int getMedian() { return median; }
    public int getNum_of_transactions() { return num_of_transactions; }
    public int getTotal_contributions() { return total_contributions; }
    public String getID() { return ID; }
}
