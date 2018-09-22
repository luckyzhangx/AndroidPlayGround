package com.luckyzhangx.pizzastore;

import com.luckyzhangx.factoryannotations.Factory;

@Factory(
        id = "Tiramisu",
        type = Meal.class
)
public class Tiramisu implements Meal {
    @Override
    public float getPrice() {
        return 4.5f;
    }
}
