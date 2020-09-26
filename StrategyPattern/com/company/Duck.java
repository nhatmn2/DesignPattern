package StrategyPattern.com.company;

import java.io.PrintStream;

import static StrategyPattern.com.company.Duck.so;

//----------------------------------------------------------------------
//  root class Duck
// ----------------------------------------------------------------------
public abstract class Duck {
    static PrintStream so = System.out;     // for convenience

    FlyBehavior fb;      // interface
    QuackBehavior qb;    // interface

    public Duck() { }

    public abstract void display();

    public void performFly() { fb.fly(); }
    public void performQuack() { qb.quack(); }
    public void swim() { so.println("I can float (well, all ducks float!)"); }

    String classname() { return this.getClass().getSimpleName(); }

    static void runTest(Duck duck) {
        so.println("\nrunning Duck test================ for type: " + duck.classname());
        duck.display();
        duck.swim();
        duck.performQuack();
        duck.performFly();
        so.println("=== ending Duck test================");
    }

    static void runTests() {
        so.println("\nrunning ALL Duck tests=============================================");
        runTest(new MallardDuck());
        runTest(new RedheadDuck());
        runTest(new RubberDuck());
        runTest(new WoodenDecoyDuck());
        so.println("============== ending ALL Duck tests=================================");
    }
    public static void main(String[] args) { Duck.runTests(); }
}

//----------------------------------------------------------------------
//  Interfaces
//----------------------------------------------------------------------
interface FlyBehavior { void fly(); }
interface QuackBehavior { void quack(); }

//----------------------------------------------------------------------
// Concrete FlyBehavior classes
//----------------------------------------------------------------------
class FlyWithWings implements FlyBehavior {
    public void fly() { so.println("I'm flying!"); }
}

class FlyNoWay implements FlyBehavior {
    public void fly() { so.println("Sorry, I can't fly!"); }
}

//----------------------------------------------------------------------
// Concrete QuackBehavior classes
//----------------------------------------------------------------------
class Quack implements QuackBehavior {
    public void quack() { so.println("I say: Quack!"); }
}

class Squeak implements QuackBehavior {
    public void quack() { so.println("I say: Squeak!"); }
}

class MuteQuack implements QuackBehavior {
    public void quack() { so.println("I say: << silence >>"); }
}


//----------------------------------------------------------------------
// LiveDuck  classes
//----------------------------------------------------------------------
abstract class LiveDuck extends Duck {
    public LiveDuck() {
        super();
        qb = new Quack();
        fb = new FlyWithWings();
    }
    abstract public void display();
}

class MallardDuck extends LiveDuck {
    public MallardDuck() { super(); }
    public void display() { so.println("I'm a real Mallard duck"); }
}

class RedheadDuck extends LiveDuck {
    public RedheadDuck() { super(); }
    public void display() { so.println("I'm a real Redhead duck"); }
}

//----------------------------------------------------------------------
//  NotAliveDuck  classes
//----------------------------------------------------------------------
abstract class NotAliveDuck extends Duck {
    public NotAliveDuck() {
        super();
        fb = new FlyNoWay();
    }
    abstract public void display();
}

class RubberDuck extends NotAliveDuck {
    public RubberDuck() {
        super();
        qb = new Squeak();
    }
    public void display() { so.println("I'm a Rubber duck (from Target)"); }
}

class WoodenDecoyDuck extends NotAliveDuck {
    public WoodenDecoyDuck() {
        super();
        qb = new MuteQuack();
    }
    public void display() { so.println("I'm a Wooden decoy duck (from Big 5 Sports)"); }
}
