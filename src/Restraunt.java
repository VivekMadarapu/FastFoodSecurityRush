import libraries.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Restraunt
{
    private int reputation;
    private ArrayDeque<Customer>[] lanes;
    Random random = new Random();

    public Restraunt()
    {
        reputation = 50;
        lanes = new ArrayDeque[3];
        lanes[0] = new ArrayDeque<>();
        lanes[1] = new ArrayDeque<>();
        lanes[2] = new ArrayDeque<>();
    }

    public void update(double multiplier) throws FileNotFoundException
    {
        for (int l = 0; l < 3;l++)
        {
            if(lanes[l].size() < 7)
            {
                if (random.nextDouble() < 0.9)
                {
                    Customer customer = new Customer(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    if(customer.getColor().getRed() < 100 && customer.getColor().getGreen() < 100 && customer.getColor().getBlue() < 100)
                    {
                        customer.setPotTheif(true);
                        generateItems(customer, false);
                    }
                    else
                        generateItems(customer, true);

                    lanes[l].addLast(customer);
                }
            }
            if (!lanes[l].isEmpty() && random.nextDouble() < 0.01 + multiplier)
            {
                Customer removed = lanes[l].removeFirst();

                if(removed.isFlagged() && (removed.getItems().contains("Knife") || removed.getItems().contains("Gun") || removed.getItems().contains("Bomb")|| removed.getItems().contains("Grenade")))
                    reputation += 10;
                else if(!removed.isFlagged() && (removed.getItems().contains("Knife") || removed.getItems().contains("Gun") || removed.getItems().contains("Bomb") || removed.getItems().contains("Grenade")))
                    reputation -= 10;
                else if(removed.isFlagged() && !(removed.getItems().contains("Knife") || removed.getItems().contains("Gun") || removed.getItems().contains("Bomb") || removed.getItems().contains("Grenade")))
                    reputation -= 20;

                StdDraw.setPenColor(new Color(165, 165, 110));
                StdDraw.filledRectangle(0.75, 0.05, 0.12, 0.03);
                StdDraw.setPenColor(Color.BLACK);
                StdDraw.textLeft(0.65, 0.04, "Reputation: " + reputation);
            }
        }
    }

    public ArrayDeque<Customer>[] getLanes(){
        return lanes;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    private void generateItems(Customer customer, boolean safe) throws FileNotFoundException
    {
        Scanner items;
        if(safe)
            items = new Scanner(new File("items_safe.txt"));
        else
            items = new Scanner(new File("items.txt"));

        while (items.hasNext())
        {
            if(random.nextDouble() < 0.6)
                customer.addItem(items.nextLine());
            else
                items.nextLine();
        }
    }

}
