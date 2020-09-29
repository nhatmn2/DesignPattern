package BuilderPattern.com.company;

public class NutritionFacts{
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static class Builder{
        //require parameters
        private final int servingSize;
        private final int servings;

        //Optional parameters - initialized to default values
        private int calories = 0;
        private int fat = 0;
        private int carbohydrate = 0;
        private int sodium = 0;

        public Builder(int servingSize, int servings){
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val){
            calories = val;
            return this;
        }

        public Builder carbohydrate(int val){
            carbohydrate = val;
            return this;
        }

        public Builder fat(int val){
            fat = val;
            return this;
        }

        public Builder sodium(int val){
            sodium = val;
            return this;
        }

        public NutritionFacts build(){
            return new NutritionFacts(this);
        }
    }

    private NutritionFacts(Builder builder){
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    public String toString() {
        return String.format("NutritionFacts[servingsize(%d), servings(%d), calories(%d), fat(%d), sodium(%d), carbs(%d)]",
                servingSize, servings, calories, fat, sodium, carbohydrate);
    }
}

class Nutrition{
    public static void main(String[] args){
        System.out.println("\n=======================================================BUILDER PATTERN=========================================================\n");
        NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8).calories(100).sodium(35).carbohydrate(27).build();
        System.out.println("Builder pattern... -- cocaCola is: " + cocaCola);
    }
}
