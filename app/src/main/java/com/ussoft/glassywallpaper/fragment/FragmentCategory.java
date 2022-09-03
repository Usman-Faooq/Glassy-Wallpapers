package com.ussoft.glassywallpaper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.ussoft.glassywallpaper.CategoryActivity;
import com.ussoft.glassywallpaper.R;

public class FragmentCategory extends Fragment {


    public FragmentCategory() {
        // Required empty public constructor
    }

    CardView catAbstract, catAnimal, catDrink, catTech, catFood, catNature, catSports
            , catEnter, catVehicle, catSpace, catMountain, catBeach;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        catAbstract = view.findViewById(R.id.cat_abstract);
        catAnimal = view.findViewById(R.id.cat_animals);
        catDrink = view.findViewById(R.id.cat_drinks);
        catTech = view.findViewById(R.id.cat_tech);
        catFood = view.findViewById(R.id.cat_food);
        catNature = view.findViewById(R.id.cat_nature);
        catSports = view.findViewById(R.id.cat_sports);
        catEnter = view.findViewById(R.id.cat_entertainment);
        catVehicle = view.findViewById(R.id.cat_vehicles);
        catSpace = view.findViewById(R.id.cat_space);
        catMountain = view.findViewById(R.id.cat_mountain);
        catBeach = view.findViewById(R.id.cat_beach);

        catAbstract.setOnClickListener(view1 -> {
            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("search", "Abstract");
            startActivity(i);
        });

        catAnimal.setOnClickListener(view12 -> {
            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("search", "Animals");
            startActivity(i);
        });

        catDrink.setOnClickListener(view13 -> {
            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("search", "Drinks");
            startActivity(i);
        });

        catTech.setOnClickListener(view14 -> {
            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("search", "Technology");
            startActivity(i);
        });

        catFood.setOnClickListener(view15 -> {
            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("search", "Foods");
            startActivity(i);
        });

        catNature.setOnClickListener(view16 -> {
            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("search", "Nature");
            startActivity(i);
        });

        catSports.setOnClickListener(view17 -> {
            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("search", "Sports");
            startActivity(i);
        });

        catEnter.setOnClickListener(view18 -> {
            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("search", "Entertainment");
            startActivity(i);
        });

        catVehicle.setOnClickListener(view19 -> {
            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("search", "Vehicles");
            startActivity(i);
        });

        catSpace.setOnClickListener(view110 -> {
            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("search", "Space");
            startActivity(i);
        });

        catMountain.setOnClickListener(view111 -> {
            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("search", "Mountain");
            startActivity(i);
        });

        catBeach.setOnClickListener(view112 -> {
            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("search", "Beach");
            startActivity(i);
        });
        return view;
    }
}