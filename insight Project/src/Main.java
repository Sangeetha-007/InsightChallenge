import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args)
    {
        final String file = "itcont.txt";
        BufferedReader br = null;
        BufferedWriter bw = null;
        ArrayList<Recipient> recipients = new ArrayList<Recipient>(); // arraylist will store all recipients

        try {

            br = new BufferedReader(new FileReader(file));

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                String [] currentLine = sCurrentLine.split("\\|"); // split on the pipe
                if(currentLine[0].isEmpty()) // if ID is empty
                    continue; // move on to the next iteration of the loop, i.e. code below this is not executed

                String rec_id = currentLine[0]; // recipient id
                int name_index = 0; // we have to find the index of the name now

                for(int i = 1; i < currentLine.length; i++) // go through array
                {
                    if(currentLine[i].contains(",")) // name has a "," character, so we look for that
                    {
                        name_index = i; // we have found the index of the name
                        break; // we can exit loop now
                    }
                }

                int zipcode_index = name_index + 3; // zipcode is 3 spaces after the name, according to our notes
                int trans_date_index = zipcode_index + 3; // trans_date is 3 indices after the zipcode
                int trans_amt_index = trans_date_index + 1; // trans_amt is 1 index after trans_date

                // if trans_amt is empty or OTHER_ID is NOT empty
                if(currentLine[trans_amt_index].isEmpty() || !currentLine[trans_amt_index + 1].isEmpty())
                    continue; // move on to the next iteration of the loop, i.e. code below this is not executed


                String zipcode = currentLine[zipcode_index];
                // remember that we only want the first five characters of the zipcode
                if(zipcode.length() > 5) // if zipcode is more than 5 characters
                    zipcode = zipcode.substring(0, 5); // get the first five characters (index 0 to 4)

                String trans_date = currentLine[trans_date_index];

                double trans_amt = Double.parseDouble(currentLine[trans_amt_index]);
                // we simply convert it from String to double using Double.parseDouble method.

                trans_amt = Math.round(trans_amt); // amount must be rounded as per instructions

                // create Recipient object 'r' using constructor
                Recipient r = new Recipient(rec_id, zipcode, trans_date, trans_amt);
                recipients.add(r); // add this recipient 'r' to the arraylist
            }

            medianvals_by_zip(recipients, bw);
            medianvals_by_date(recipients, bw);

            System.out.println("\n");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void medianvals_by_zip(ArrayList<Recipient> recipients, BufferedWriter bw)
    {
        try {
            FileWriter file = new FileWriter("medianvals_by_zip.txt");
            bw = new BufferedWriter(file);

            Map<String, Info> map = new HashMap<>();
            for(int i = 0; i < recipients.size(); i++)
            {
                String k = recipients.get(i).getZip();
                if( map.containsKey(k) )
                    map.get(k).update(recipients, i);
                else
                    map.put(k, new Info(recipients, i));

                bw.write(recipients.get(i).getID() + "|" + k + "|" + map.get(k).getMedian() + "|" + map.get(k).getNum_of_transactions() + "|" + map.get(k).getTotal_contributions() + "\n");
            }

            bw.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void medianvals_by_date(ArrayList<Recipient> recipients, BufferedWriter bw)
    {
        try {
            FileWriter file = new FileWriter("medianvals_by_date.txt");
            bw = new BufferedWriter(file);

            Map<String, Info> map = new HashMap<>();
            for(int i = 0; i < recipients.size(); i++)
            {
                String k = recipients.get(i).getTrans_date();
                if (map.containsKey(k))
                    map.get(k).update(recipients, i);
                else
                    map.put(k, new Info(recipients, i));
            }

            for(Map.Entry<String, Info> entry : map.entrySet())
                bw.write(entry.getValue().getID() + "|" + entry.getKey() + "|" + entry.getValue().getMedian() + "|" + entry.getValue().getNum_of_transactions() + "|" + entry.getValue().getTotal_contributions() + "\n");
            

            bw.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

}