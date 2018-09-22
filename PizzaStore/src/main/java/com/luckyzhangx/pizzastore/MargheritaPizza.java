package com.luckyzhangx.pizzastore;

import com.luckyzhangx.factoryannotations.Factory;

@Factory(
        id = "Margherita",
        type = Meal.class
)
public class MargheritaPizza implements Meal {
    @Override
    public float getPrice() {
        return 6.0f;
    }
}
