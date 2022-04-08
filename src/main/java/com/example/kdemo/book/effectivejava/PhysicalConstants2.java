package com.example.kdemo.book.effectivejava;

public class PhysicalConstants2 {
    private PhysicalConstants2() {
    }

    static final double AVOGADROS_NUMBER = 6.022_140_857e23;
    static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
    static final double ELECTRON_MASS = 9.109_383_56e-31;
}

class Test {
    double atoms(double mols) {
        return PhysicalConstants2.AVOGADROS_NUMBER * mols;
    }
}