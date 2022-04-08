package com.example.kdemo.book.effectivejava;

abstract class Figure2 {
    abstract double area();
}

class Circle extends Figure2 {
    final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * (radius * radius);
    }
}

class Rectangle extends Figure2 {
    final double length;
    final double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }
}

class Square extends Rectangle {
    Square(double side) {
        super(side, side);
    }
}