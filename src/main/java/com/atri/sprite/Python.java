package com.atri.sprite;

import com.atri.util.Direction;
import com.atri.util.RoleAndSpeed;
import com.atri.util.SoundEffect;
import com.atri.view.Director;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;

import java.util.LinkedList;

public class Python extends Role {

    @Getter
    private final LinkedList<Segment> body;
    private final int INITIAL_SIZE = 3;
    private long lastDirectionChangeTime = 0;  // 上次改变方向的时间戳（毫秒）
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

    public void reset() {
        body.clear();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            body.addFirst(new Segment(6 + i, 10));
        }
        super.direction = Direction.RIGHT;
    }

    public LinkedList<Segment> deepCopy(LinkedList<Segment> body) {
        LinkedList<Segment> copy = new LinkedList<>();
        for (Segment segment : body) {
            copy.add(new Segment(segment));
        }
        return copy;
    }

    public static class Segment {
        public Segment() {
        }

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

        long DIRECTION_CHANGE_DELAY =
                Director.getRoleAndSpeed().getRole().equals(RoleAndSpeed.SLUG.getRole()) ? 200 :
                        Director.getRoleAndSpeed().getRole().equals(RoleAndSpeed.WORM.getRole()) ? 150 :
                                Director.getRoleAndSpeed().getRole().equals(RoleAndSpeed.PYTHON.getRole()) ? 125 : 150;
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastDirectionChangeTime < DIRECTION_CHANGE_DELAY) return;
        if (this.direction == Direction.UP && direction != Direction.DOWN &&
                direction != Direction.UP) {
            SoundEffect.KEYCODE_PRESS.play();
            this.direction = direction;
        } else if (this.direction == Direction.DOWN && direction != Direction.UP &&
                direction != Direction.DOWN) {
            SoundEffect.KEYCODE_PRESS.play();
            this.direction = direction;
        } else if (this.direction == Direction.LEFT && direction != Direction.RIGHT &&
                direction != Direction.LEFT) {
            SoundEffect.KEYCODE_PRESS.play();
            this.direction = direction;
        } else if (this.direction == Direction.RIGHT && direction != Direction.LEFT &&
                direction != Direction.RIGHT) {
            SoundEffect.KEYCODE_PRESS.play();
            this.direction = direction;
        }
        lastDirectionChangeTime = currentTime;
    }

    public void draw(GraphicsContext gc) {
        for (int i = 0; i < body.size(); i++) {
            Segment segment = body.get(i);
            double GRID_SIZE = Director.GRID_SIZE;
            double cornerRadius = GRID_SIZE / 2.5;
            gc.setFill(Color.BLACK);
            gc.fillRoundRect(segment.x * GRID_SIZE, segment.y * GRID_SIZE,
                    GRID_SIZE, GRID_SIZE, cornerRadius, cornerRadius);
            if (i == 0) {
                // 绘制方形黑色蛇头

                gc.setFill(Color.WHITE);
                double eyeSize = GRID_SIZE / 4.0;   // 设置眼睛大小

                // 左眼
                gc.fillOval(segment.x * GRID_SIZE + (double) GRID_SIZE / 4 - eyeSize / 2,
                        segment.y * GRID_SIZE + (double) GRID_SIZE / 4 - eyeSize / 2,
                        eyeSize, eyeSize);
                // 右眼
                gc.fillOval(segment.x * GRID_SIZE + (double) (3 * GRID_SIZE) / 4 - eyeSize / 2,
                        segment.y * GRID_SIZE + (double) GRID_SIZE / 4 - eyeSize / 2,
                        eyeSize, eyeSize);

            }
        }
    }

    public void grow() {
        Segment newSegment = new Segment(body.getLast().x, body.getLast().y);
        body.addLast(newSegment);
    }

    public boolean checkSelfCollision() {
        LinkedList<Segment> body = this.getBody();
        Segment head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            Segment segment = body.get(i);
            if (head.x == segment.x && head.y == segment.y) {
                return true;
            }
        }
        return false;
    }


    public void addTailUpdateHead() {
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
