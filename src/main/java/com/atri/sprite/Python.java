package com.atri.sprite;

import com.atri.util.Direction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;

import java.util.LinkedList;

public class Python extends Role{

    @Getter
    private final LinkedList<Segment> body;
    private final int INITIAL_SIZE = 3;
    private final int GRID_SIZE = 20;
    @Getter
    private LinkedList<Segment> oldBody;

    public Python(double x, double y, Direction direction, double speed) {
        super(x, y, direction, speed);
        body = new LinkedList<>();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            body.addFirst(new Segment(6 + i, 10));
        }
        super.direction = Direction.RIGHT;
    }

    public Segment getHead() {
        return body.getFirst();
    }

    public Segment getLast() {
        return body.getLast();
    }

    public LinkedList<Segment> deepCopy(LinkedList<Segment> body) {
        LinkedList<Segment> copy = new LinkedList<>();
        for (Segment segment : body) {
            copy.add(new Segment(segment));
        }
        return copy;
    }

    public static class Segment {
        public Segment() {}

        public Segment(Segment other) {
            this.x = other.x;
            this.y = other.y;
        }
        public int x, y;
        public Segment(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public void move() {
        int headX = getHead().x;
        int headY = getHead().y;

        switch (direction) {
            case UP -> headY--;
            case DOWN -> headY++;
            case LEFT -> headX--;
            case RIGHT -> headX++;
        }

        oldBody = deepCopy(body);

        Segment newHead = new Segment(headX, headY);
        body.addFirst(newHead);

        body.removeLast();
    }

    @Override
    public boolean collisionCheck(Sprite sprite) {
        return false;
    }

    public void setDirection(Direction direction) {
        if (this.direction == Direction.UP && direction != Direction.DOWN) {
            this.direction = direction;
        } else if (this.direction == Direction.DOWN && direction != Direction.UP) {
            this.direction = direction;
        } else if (this.direction == Direction.LEFT && direction != Direction.RIGHT) {
            this.direction = direction;
        } else if (this.direction == Direction.RIGHT && direction != Direction.LEFT) {
            this.direction = direction;
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        for (Segment segment : body) {
            gc.fillRect(segment.x * GRID_SIZE, segment.y * GRID_SIZE, GRID_SIZE, GRID_SIZE);
            if (segment == body.getFirst()) System.out.println(segment.x + ", " + segment.y );
        }
    }

    public void grow() {
        Segment newSegment = new Segment(body.getLast().x, body.getLast().y);
        body.addLast(newSegment);
    }

    public boolean checkSelfCollision() {
        Segment head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            Segment segment = body.get(i);
            if (head.x == segment.x && head.y == segment.y) return true;
        }
        return false;
    }

    public void addTailUpdateHead(int newX, int newY) {
        LinkedList<Segment> oldBody = getOldBody(); // 更新为新头部位置
        Segment head = oldBody.get(1);
        Segment tail = oldBody.getLast();
        Segment newHead = getHead();
        newHead.x = head.x;
        newHead.y = head.y;
        body.addFirst(newHead);
        body.addLast(tail);
    }

}
