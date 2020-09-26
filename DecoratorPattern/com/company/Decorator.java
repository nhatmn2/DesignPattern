package DecoratorPattern.com.company;

import java.io.PrintStream;

public class Decorator {
    public static PrintStream so = System.out;
    public static void main(String[] args) {
        so.println("\n=======================\nDECORATOR PATTERN !\n=======================");
        Beverage e = new Espresso();
        so.println("     e is: " + e);
        Beverage dr = new DarkRoast();
        so.println("    dr is: " + dr);
        Beverage modr = new Mocha(dr);
        so.println("  modr is: " + modr);
        Beverage wmodr = new Whip(modr);
        so.println(" wmodr is: " + wmodr);
        Beverage wwmodr = new Whip(wmodr);
        so.println("wwmodr is: " + wwmodr);
        Beverage cwwmodr = new Caramel(wwmodr);
        so.println("cwwmodr is: " + cwwmodr);

        so.println("on the fly: " + new Mocha(new Mocha(new Mocha(new Espresso()))));
    }
}

abstract class Beverage{
    private String description;

    public Beverage() {this("unknown beverage"); }
    public Beverage(String description){this.description = description;}

    public String toString(){
        String s = String.format("%-38s $ %.2f", description(), cost());
        return s;
    }

    public String description(){return this.description;}
    public abstract float cost();

}

class HouseBlend extends Beverage{
    public HouseBlend() {super("HouseBlend");}
    public float cost() {return .89f;}
}

class DarkRoast extends Beverage{
    public DarkRoast() {super("DarkRoast");}
    public float cost() {return .99f;}
}

class Decaf extends Beverage{
    public Decaf() {super("Decap");}
    public float cost() {return 1.05f;}
}

class Espresso extends Beverage{
    public Espresso() {super("Espresso");}
    public float cost() {return 1.99f;}
}
abstract class CondimentDecorator extends Beverage{
    protected Beverage beverage;
    CondimentDecorator(Beverage beverage){this.beverage = beverage;}
    abstract public String description();
}

class Mocha extends CondimentDecorator{
    public Mocha(Beverage beverage){super(beverage);}
    public String description() {return "Mocha" + beverage.description();}
    public float cost() {return 0.20f + beverage.cost();}
}

class Whip extends CondimentDecorator{
    public Whip(Beverage beverage){super(beverage);}
    public String description() {return "Whip" + beverage.description();}
    public float cost() {return 0.10f + beverage.cost();}
}

class Soy extends CondimentDecorator{
    public Soy(Beverage beverage){super(beverage);}
    public String description() {return "Soy" + beverage.description();}
    public float cost(){return 0.15f + beverage.cost();}
}

class Milk extends CondimentDecorator{
    public Milk(Beverage beverage){super(beverage);}
    public String description() {return "Milk" + beverage.description();}
    public float cost(){return 0.10f + beverage.cost();}
}

class Caramel extends CondimentDecorator{
    public Caramel(Beverage beverage){super((beverage));}
    public String description(){return "Caramel" + beverage.description();}
    public float cost(){return 0.50f + beverage.cost();}
}
