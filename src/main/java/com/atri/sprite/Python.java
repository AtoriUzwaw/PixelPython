package com.atri.sprite;

import com.atri.util.Direction;
import com.atri.util.RoleAndSpeed;
import com.atri.util.SoundEffect;
import com.atri.view.Director;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;

import java.util.LinkedList;

/**
 * Python 类继承自 Role 类，表示一条蛇的角色。
 * 该类包含蛇的身体、移动、碰撞检测、蛇头和蛇尾的管理等功能。
 */
public class Python extends Role {

    @Getter
    private final LinkedList<Segment> body;     // 蛇的身体（由多个Segment组成）
    private final int INITIAL_SIZE = 3;         // 初始身体长度
    private long lastDirectionChangeTime = 0;   // 上次改变方向的时间戳（毫秒）
    @Getter
    private LinkedList<Segment> oldBody;        // 记录上次移动前的蛇身体（用于碰撞检测）

    /**
     * 构造方法，初始化蛇的方向和身体。
     * 默认蛇的身体长度为3，初始位置在 (6, 10)。
     *
     * @param direction 蛇的初始移动方向
     */
    public Python(Direction direction) {
        super(direction);
        body = new LinkedList<>();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            body.addFirst(new Segment(6 + i, 10));  // 初始化蛇身
        }
    }

    /**
     * 获取蛇的头部位置（蛇的头是身体的第一个元素）。
     *
     * @return 返回蛇头的 Segment 对象
     */
    public Segment getHead() {
        return body.getFirst();
    }

    /**
     * 重置蛇的状态，将蛇的位置恢复到初始状态。
     * 蛇的方向会被重置为右（Direction.RIGHT）。
     */
    public void reset() {
        body.clear();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            body.addFirst(new Segment(6 + i, 10));  // 重置蛇身
        }
        super.direction = Direction.RIGHT;               // 方向重置为右
    }

    /**
     * 深拷贝蛇的身体，返回新的身体列表。
     *
     * @param body 蛇的身体（Segment 列表）
     * @return 返回深拷贝的身体列表
     */
    public LinkedList<Segment> deepCopy(LinkedList<Segment> body) {
        LinkedList<Segment> copy = new LinkedList<>();
        for (Segment segment : body) {
            copy.add(new Segment(segment));  // 复制每一个 Segment
        }
        return copy;
    }

    /**
     * Segment 类用于表示蛇的每一节身体部分。
     * 每个 Segment 存储了它的 x 和 y 坐标。
     */
    public static class Segment {
        public int x, y;  // 每节的坐标

        /**
         * 默认构造函数。
         */
        public Segment() {
        }

        /**
         * 复制构造函数，根据另一个 Segment 创建新的 Segment。
         *
         * @param other 另一个 Segment 对象
         */
        public Segment(Segment other) {
            this.x = other.x;
            this.y = other.y;
        }

        /**
         * 带坐标的构造函数。
         *
         * @param x  坐标 x
         * @param y  坐标 y
         */
        public Segment(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * 移动蛇，更新蛇头位置，并将蛇尾去掉。
     * 根据当前方向改变蛇头坐标。
     */
    @Override
    public void move() {
        int headX = getHead().x;
        int headY = getHead().y;

        // 根据方向改变蛇头的坐标
        switch (direction) {
            case UP -> headY--;
            case DOWN -> headY++;
            case LEFT -> headX--;
            case RIGHT -> headX++;
        }

        oldBody = deepCopy(body);  // 记录旧的蛇身

        // 创建新蛇头并添加到身体前面
        Segment newHead = new Segment(headX, headY);
        body.addFirst(newHead);

        // 移除蛇尾
        body.removeLast();
    }

    /**
     * 设置蛇的方向，并控制方向变化的时间间隔。
     * 防止蛇反向移动，增加了时间限制防止频繁改变方向。
     *
     * @param direction 新的方向
     */
    public void setDirection(Direction direction) {
        // 设置不同角色的方向改变时间间隔
        long DIRECTION_CHANGE_DELAY =
                Director.getRoleAndSpeed().getRole().equals(RoleAndSpeed.SLUG.getRole()) ? 200 :
                        Director.getRoleAndSpeed().getRole().equals(RoleAndSpeed.WORM.getRole()) ? 150 :
                                Director.getRoleAndSpeed().getRole().equals(RoleAndSpeed.PYTHON.getRole()) ? 125 : 150;
        long currentTime = System.currentTimeMillis();

        // 判断是否满足方向变更的时间间隔
        if (currentTime - lastDirectionChangeTime < DIRECTION_CHANGE_DELAY) return;

        // 控制合法的方向变化，防止蛇直接反向
        if ((this.direction == Direction.UP && direction != Direction.DOWN && direction != Direction.UP) ||
                (this.direction == Direction.DOWN && direction != Direction.UP && direction != Direction.DOWN) ||
                (this.direction == Direction.LEFT && direction != Direction.RIGHT && direction != Direction.LEFT) ||
                (this.direction == Direction.RIGHT && direction != Direction.LEFT && direction != Direction.RIGHT)) {
            SoundEffect.KEYCODE_PRESS.play();  // 播放按键音效
            this.direction = direction;
        }

        lastDirectionChangeTime = currentTime;  // 更新方向变化的时间
    }

    /**
     * 绘制蛇。
     * 绘制每一节蛇身，并在蛇头上绘制眼睛。
     *
     * @param gc GraphicsContext 对象，用于绘制
     */
    public void draw(GraphicsContext gc) {
        for (int i = 0; i < body.size(); i++) {
            Segment segment = body.get(i);
            double GRID_SIZE = Director.GRID_SIZE;  // 网格大小
            double cornerRadius = GRID_SIZE / 2.5;  // 圆角半径
            gc.setFill(Color.BLACK);
            gc.fillRoundRect(segment.x * GRID_SIZE, segment.y * GRID_SIZE, GRID_SIZE, GRID_SIZE, cornerRadius, cornerRadius);  // 绘制蛇节

            if (i == 0) {  // 绘制蛇头
                gc.setFill(Color.WHITE);            // 白色蛇眼
                double eyeSize = GRID_SIZE / 4.0;   // 设置眼睛大小

                // 绘制左眼
                gc.fillOval(segment.x * GRID_SIZE + GRID_SIZE / 4 - eyeSize / 2,
                        segment.y * GRID_SIZE + GRID_SIZE / 4 - eyeSize / 2, eyeSize, eyeSize);

                // 绘制右眼
                gc.fillOval(segment.x * GRID_SIZE + (3 * GRID_SIZE) / 4 - eyeSize / 2,
                        segment.y * GRID_SIZE + GRID_SIZE / 4 - eyeSize / 2, eyeSize, eyeSize);
            }
        }
    }

    /**
     * 使蛇生长，增加一节身体。
     * 该方法会在蛇吃到食物时调用。
     */
    public void grow() {
        Segment newSegment = new Segment(body.getLast().x, body.getLast().y);  // 创建新的蛇节
        body.addLast(newSegment);  // 将新蛇节加到身体末尾
    }

    /**
     * 检查蛇是否发生了自撞。
     * 如果蛇头与任何一节身体重合，则发生自撞。
     *
     * @return 如果发生自撞，返回 true，否则返回 false
     */
    public boolean checkSelfCollision() {
        LinkedList<Segment> body = this.getBody();
        Segment head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            Segment segment = body.get(i);
            if (head.x == segment.x && head.y == segment.y) {
                return true;  // 如果蛇头与其他蛇节重合，返回碰撞
            }
        }
        return false;
    }

    /**
     * 更新蛇的尾巴并将蛇头向尾巴方向移动。
     * 该方法用于更新蛇的状态并模拟蛇的移动。
     */
    public void addTailUpdateHead() {
        LinkedList<Segment> oldBody = getOldBody(); // 获取旧的身体
        Segment head = oldBody.get(1);              // 获取新蛇头
        Segment tail = oldBody.getLast();           // 获取尾巴
        Segment newHead = getHead();
        newHead.x = head.x;
        newHead.y = head.y;
        body.addFirst(newHead);                     // 添加新的蛇头
        body.addLast(tail);                         // 添加尾巴
    }
}


