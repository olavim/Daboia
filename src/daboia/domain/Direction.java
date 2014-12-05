
package daboia.domain;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);
    
    public static Direction directionFromString(String direction) {
        switch (direction.toUpperCase()) {
            case "UP":
                return UP;
            case "DOWN":
                return DOWN;
            case "LEFT":
                return LEFT;
            case "RIGHT":
                return RIGHT;
            default:
                throw new IllegalArgumentException("Illegal direction");
        }
    }
    
    private final int xTransform;
    private final int yTransform;
    
    private Direction(int xTransform, int yTransform) {
        this.xTransform = xTransform;
        this.yTransform = yTransform;
    }
    
    public int xTransform() {
        return this.xTransform;
    }
    
    public int yTransform() {
        return this.yTransform;
    }
}
