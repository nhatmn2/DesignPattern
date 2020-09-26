package ObserverPattern.com.company;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
//import java.lang.Math;

import static ObserverPattern.com.company.Weather.so;
import static java.lang.Math.pow;
//import static java.lang.StrictMath.abs;

//=================================================================================
//   Weather
//=================================================================================
public class Weather {
    public static PrintStream so = System.out;

    public static void runTests() {
        WeatherData wd = new WeatherData();
        CurrentCD cd = new CurrentCD(wd);
        StatDisplay sd = new StatDisplay(wd);
        ForecastDisplay fd = new ForecastDisplay(wd);
        HeatIndexDisplay hi = new HeatIndexDisplay(wd);

        wd.setMeasurements(35, 65, 30.2f);
        wd.setMeasurements(55, 70, 29.8f);
        wd.setMeasurements(75, 75, 29.6f);

        wd.removeObserver(fd);
        wd.removeObserver(hi);

        wd.setMeasurements(92, 80, 29.6f);

        wd.removeObserver(sd);

        wd.setMeasurements(95, 85, 29.2f);
        wd.setMeasurements(100, 90, 29.0f);

        //hi.runTests();
    }

    public static void main(String[] args) {
        so.println("OBSERVER pattern !");
        Weather.runTests();
    }
}

//-------------------------------------------------------------------
// Interface classes
//-------------------------------------------------------------------
interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
interface Observer { void update(float temp, float humid, float pres); }
interface DisplayElement { void display(); }

        
        //-------------------------------------------------------------------
// Concrete Subject class
//-------------------------------------------------------------------
        
//=================================================================================
//   WeatherData
//=================================================================================
class WeatherData implements Subject {
    private ArrayList observers;
    private float temperature, humidity, pressure;

    public WeatherData() { observers = new ArrayList(); }

    public void registerObserver(Observer o) { observers.add(o); }
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) { observers.remove(i); }
    }
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); ++i) {
            Observer o = (Observer)observers.get(i);
            o.update(temperature, humidity, pressure);
        }
    }
    public void setMeasurements(float temp, float humid, float pres) {
        temperature = temp;
        humidity = humid;
        pressure = pres;

        notifyObservers();
    }
}

        
        //-------------------------------------------------------------------
// Concrete Display classes
//-------------------------------------------------------------------
        
//=================================================================================
//   CurrentCD
//=================================================================================
class CurrentCD implements Observer, DisplayElement {
    private float temp, humid;
    private Subject wd;

    public CurrentCD(Subject wd) {
        this.wd = wd;
        wd.registerObserver(this);
    }
    public void update(float temp, float humid, float pres) {
        this.temp = temp;
        this.humid = humid;
        display();
    }
    public void display() {
        String s = String.format("\nCurrently: %.2f deg F and humidity: %.2f %%", temp, humid);
        so.println(s);
    }
}

        
//=================================================================================
//   StatDisplay
//=================================================================================
class StatDisplay implements Observer, DisplayElement {
    private Queue<Float> qTemps = new LinkedList<>();
    private Queue<Float> qHumidities = new LinkedList<>();
    private Queue<Float> qPressures = new LinkedList<>();

    private Subject wd;

    public StatDisplay(Subject wd) {
        this.wd = wd;
        wd.registerObserver(this);
    }
    public void update(float temp, float humid, float pres) {
        qTemps.add(temp);
        qHumidities.add(humid);
        qPressures.add(pres);

        if (qTemps.size() > 5) {   // queue will be no larger than 5
            qTemps.remove();
            qHumidities.remove();
            qPressures.remove();
        }
        display();
    }
    public void display() {
        String s = String.format("Stats: Mean %.2f deg F. %.2f %% humidity and %.2f pressure\n",
                mean(qTemps), mean(qHumidities), mean(qPressures));
        so.print(s);
    }

    private float mean(Queue<Float> queue) {
        float total = 0.0f;
        float size = queue.size();
        for (float el : queue) {
            total += el;
        }
        return (size == 0 ? 0.0f : total / size);
    }
}

//=================================================================================
//   ForecastDisplay
//=================================================================================
class ForecastDisplay implements Observer, DisplayElement {
    private String forecast_expected;
    private Subject wd;

    public ForecastDisplay(Subject wd) {
        this.wd = wd;
        wd.registerObserver(this);
        // temp, humid, press, days_in_mean all automatically set to 0
    }
    private void forecast(String forecast_expected) { this.forecast_expected = forecast_expected; }
    private String forecast() { return forecast_expected; }

    public void update(float temp, float humid, float pres) {
        if (pres < 29.2) { forecast("Hurricane coming!"); }
        else if (pres < 29.6) { forecast("Tropical storm coming -- possibly a hurricane"); }
        else if (temp > 90) { forecast("Heat wave expected"); }
        else if (temp < 40) { forecast("Snow expected"); }
        else if (temp < 50) { forecast("Cold wave expected"); }
        else if (temp < 60) { forecast("Cooling off expected"); }
        else if (60 < temp && temp < 80) { forecast("Nice weather expected"); }
        display();
    }

    public void display() {
        String s = String.format("Forecast: %s\n", forecast());
        so.print(s);
    }
}

        
class HeatIndexDisplay implements Observer, DisplayElement {
    private float temp, humid, pres;
    private Subject wd;

    public HeatIndexDisplay(Subject wd) {
        this.wd = wd;
        wd.registerObserver(this);
    }
    public void update(float temp, float humid, float pres) {
        this.temp = temp;
        this.humid = humid;
        display();
    }

    private float heatIndex() {
        double rh = humid;
        double T = temp;

        return (float)(16.923 +
                1.85212e-1 * T + 5.37941 * rh - 1.00254e-1 * T * rh +
                9.41695e-3 * pow(T, 2.0) + 7.28898e-3 * pow(rh, 2.0) +
                3.45372e-4 * pow(T, 2.0) * rh - 8.14971e-4  * T * pow(rh, 2.0) +
                1.02102e-5 * pow(T, 2.0) * pow(rh, 2) - 3.8646e-5 * pow(T, 3.0) + 2.91583e-5 * pow(rh, 3.0) +
                1.42721e-6 * pow(T, 3.0) * rh +
                1.97483e-7 * T * pow(rh, 3.0) -
                2.18429e-8 * pow(T, 3.0) * pow(rh, 2) +
                8.43296e-10 * pow(T, 2.0) * pow(rh, 3.0) -
                4.81975e-11 * pow(T, 3.0) * pow(rh, 3.0));
    }
    public void display() {
        String s = String.format("Heat index: %.3f", heatIndex());
        so.println(s);
    }
    public static void runTests() {
        so.println("\nbeginning tests of HeatIndexDisplay =======================");
        WeatherData wd = new WeatherData();
        HeatIndexDisplay hi = new HeatIndexDisplay(wd);
        wd.setMeasurements(80, 65, 30);
        wd.setMeasurements(82, 70, 30);
        wd.setMeasurements(78, 90, 30);
        so.println("======= ending tests of HeatIndexDisplay =======================");
    }
}