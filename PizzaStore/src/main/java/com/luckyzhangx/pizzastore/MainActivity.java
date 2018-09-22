package com.luckyzhangx.pizzastore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PizzaStore pizzaStore = new PizzaStore();
        Meal meal = pizzaStore.order("CalzonePizza");
        System.out.println("Bill: $" + meal.getPrice());
    }
}
