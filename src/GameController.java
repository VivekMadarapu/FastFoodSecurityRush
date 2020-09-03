import libraries.StdDraw;
import libraries.Stopwatch;

import java.awt.*;
import java.util.Random;

public class GameController
{
    public static void main(String[] args) throws Exception
    {
        Restraunt restraunt = new Restraunt();
        StdDraw.setCanvasSize(800, 600);
        Random random = new Random();
        StdDraw.picture(0.5, 0.5, "GameBack.png", 1, 1);
        Customer selectedCustomer = null;

        Stopwatch watch = new Stopwatch();

        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        long lastFpsTime = 0;
        int timer = 60;
        double multiplier = 0.01;
        while(true)
        {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;

            lastFpsTime += updateLength;
            if(lastFpsTime >= 1000000000)
                lastFpsTime = 0;

            restraunt.update(multiplier);
            for (int r = 0; r < restraunt.getLanes().length; r++)
            {
                for (int c = 0; c < restraunt.getLanes()[r].size(); c++)
                {
                    Customer customer = (Customer) restraunt.getLanes()[r].toArray()[c];
                    if (customer != null)
                    {
                        customer.setX(r / 5.0 + 0.3);
                        customer.setY(c / 10.0 + 0.36);
                        StdDraw.setPenColor(customer.getColor());
                        StdDraw.filledCircle(customer.getX(), customer.getY(), 0.04);
                        if (customer.isFlagged())
                        {
                            StdDraw.setPenColor(Color.RED);
                            StdDraw.filledCircle(customer.getX(), customer.getY(), 0.01);
                        }
                    }
                }
            }

            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();
            if(StdDraw.isMousePressed())
            {
                for (int r = 0; r < restraunt.getLanes().length; r++)
                {
                    for (int c = 0; c < restraunt.getLanes()[r].size(); c++)
                    {
                        Customer customer = (Customer) restraunt.getLanes()[r].toArray()[c];
                        if (Math.sqrt(Math.pow((customer.getY() - mouseY), 2) + Math.pow((customer.getX() - mouseX), 2)) <= 0.04) {
                            selectedCustomer = customer;
                            StringBuilder itemStr = new StringBuilder("Detected Items:");
                            StringBuilder itemStr2 = new StringBuilder("                ");
                            for (int i = 0; i < customer.getItems().size(); i++) {
                                if(i < 7)
                                    itemStr.append(customer.getItems().get(i)).append(" ");
                                else
                                    itemStr2.append(customer.getItems().get(i)).append(" ");

                            }
                            StdDraw.setPenColor(new Color(165, 165, 110));
                            StdDraw.filledRectangle(0.5, 0.15, 0.37, 0.05);
                            StdDraw.setPenColor(Color.BLACK);
                            StdDraw.textLeft(0.13, 0.15, itemStr.toString());
                            StdDraw.textLeft(0.18, 0.12, itemStr2.toString());
                        }
                    }
                }
            }
            if(StdDraw.hasNextKeyTyped())
            {
                char key = StdDraw.nextKeyTyped();
                if(key == 'f' && selectedCustomer != null)
                {
                    if(selectedCustomer.isFlagged())
                        selectedCustomer.setFlagged(false);
                    else
                        selectedCustomer.setFlagged(true);
                }
                if(key == '=')
                {
                    multiplier+=0.01;
                    System.out.println("Speed: " + (int) (multiplier*100));
                }
                if(key == '-')
                {
                    multiplier-=0.01;
                    System.out.println("Speed: " + (int) (multiplier*100));
                }
                if(key == ']')
                {
                    restraunt.setReputation(restraunt.getReputation()+10);
                }
                if(key == '[')
                {
                    restraunt.setReputation(restraunt.getReputation()-10);
                }
            }

            timer--;
            if(timer == 0)
            {
                timer = 150;
                multiplier += 0.01;
                System.out.println("Speed: " + (int) (multiplier*100));
            }
            if(restraunt.getReputation() <= 0)
                break;
        }
        StdDraw.picture(0.5, 0.5, "GameOver.png", 1, 1);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(0.5, 0.09, "Score: " + (int) Math.round(watch.elapsedTime()));
        System.out.println(watch.elapsedTime());
    }
}