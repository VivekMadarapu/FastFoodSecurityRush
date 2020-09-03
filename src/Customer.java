import java.awt.*;
import java.util.ArrayList;

public class Customer
{
    private boolean isPotTheif;
    private boolean flagged;
    private Color color;
    private ArrayList<String> items;
    private double x, y;

    public Customer(boolean isTheif, Color color)
    {
        this.isPotTheif = isTheif;
        this.color = color;
        flagged = false;
        items = new ArrayList<>();
    }

    public Customer(Color color)
    {
        this.color = color;
        this.isPotTheif = false;
        flagged = false;
        items = new ArrayList<>();
    }

    public boolean isPotTheif() {
        return isPotTheif;
    }

    public Color getColor()
    {
        if(color == null){
            return Color.BLACK;
        }
        return color;
    }

    public void setPotTheif(boolean potTheif) {
        isPotTheif = potTheif;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public ArrayList<String> getItems() {
        return items;
    }

    @Override
    public String toString()
    {
        if(color == null){
            return "None";
        }
        return color.toString();
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

}
