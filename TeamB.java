public class TeamB {
    public static void main(String[] args) {
        Square square = new Square();
        Circle circle = new Circle();
        Triangle triangle = new Triangle();
 
        // Pretend the user clicked these shapes, in this order.
        System.out.println("--- user clicked the square ---");
        square.rotate();
        square.playSound();
        System.out.println();
 
        System.out.println("--- user clicked the circle ---");
        circle.rotate();
        circle.playSound();
        System.out.println();
 
        System.out.println("--- user clicked the triangle ---");
        triangle.rotate();
        triangle.playSound();
        System.out.println();
    }
}
 
class Square {
    // Instance variables: things a Square knows about itself.
    double width = 50;
    double height = 50;
 
    // Methods: things a Square can do.
    void rotate() {
        System.out.println("Rotating the square 360 degrees around ("
                + width / 2 + ", " + height / 2 + ")");
    }
 
    void playSound() {
        System.out.println("Playing square.wav");
    }
}
 
class Circle {
    double width = 40;
    double height = 40;
 
    void rotate() {
        System.out.println("Rotating the circle 360 degrees around ("
                + width / 2 + ", " + height / 2 + ")");
    }
 
    void playSound() {
        System.out.println("Playing circle.wav");
    }
}
 
class Triangle {
    double width = 60;
    double height = 30;
 
    void rotate() {
        System.out.println("Rotating the triangle 360 degrees around ("
                + width / 2 + ", " + height / 2 + ")");
    }
 
    void playSound() {
        System.out.println("Playing triangle.wav");
    }
}

