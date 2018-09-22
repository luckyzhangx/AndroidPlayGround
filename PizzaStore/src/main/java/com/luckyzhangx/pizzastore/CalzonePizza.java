package com.luckyzhangx.pizzastore;

import com.luckyzhangx.factoryannotations.Factory;

@Factory(
        id = "Calzone",
        type = Meal.class
)
public class CalzonePizza implements Meal {
    @Override
    public float getPrice() {
         return 8.5f;
    }
}
