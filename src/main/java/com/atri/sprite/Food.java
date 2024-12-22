package com.atri.sprite;

import com.atri.view.Director;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import lombok.extern.java.Log;

import java.util.LinkedList;
import java.util.Random;

/**
 * Food 类继承自 Role 类，表示游戏中的食物。
 * 该类负责生成食物的位置、绘制食物并使食物旋转。
 * 食物的位置会随机生成，并确保不会与蛇的身体重叠。
 */
@Log
public class Food extends Role {
    int snackX;  // 食物的 x 坐标
    int snackY;  // 食物的 y 坐标
    double GRID_SIZE = Director.GRID_SIZE;  // 网格大小
    private double angle = 0;  // 旋转角度，用于使食物旋转

    /**
     * 构造方法，初始化食物的存活状态。
     *
     * @param alive 食物的存活状态（true 表示食物存活，false 表示食物被消耗）
     */
    public Food(boolean alive) {
        super(alive);
    }

    /**
     * 随机生成食物的位置，确保生成的位置不与蛇的身体重合。
     * 如果生成的位置与蛇的身体重合，则重新生成位置，直到找到有效位置为止。
     *
     * @param body 蛇的身体，由多个 Segment 组成
     */
    public void snack(LinkedList<Python.Segment> body) {
        Random random = new Random();
        boolean validPosition = false;

        // 循环直到生成有效位置
        while (!validPosition) {
            super.x = random.nextInt(0, 30);  // 随机生成 x 坐标
            super.y = random.nextInt(1, 21);  // 随机生成 y 坐标

            int snackX = (int) (x * GRID_SIZE);  // 计算食物在画布上的 x 坐标
            int snackY = (int) (y * GRID_SIZE);  // 计算食物在画布上的 y 坐标

            validPosition = true;  // 默认认为位置有效

            // 检查生成的位置是否与蛇的身体重合
            for (Python.Segment segment : body) {
                if (super.x == segment.x && super.y == segment.y + 1) {
                    validPosition = false;  // 如果重合，重新生成位置
                    break;
                }
            }

            // 更新食物位置
            this.snackX = snackX;
            this.snackY = snackY;
            log.info("食物位置：" + x + ", " + y);  // 打印食物的位置
        }
    }

    /**
     * 绘制食物，带有旋转效果。
     * 食物使用字符 "✿" 表示，且可以旋转以增加视觉效果。
     *
     * @param gc GraphicsContext 对象，用于绘制食物
     */
    public void drawFood(GraphicsContext gc) {
        gc.save();  // 保存当前绘图状态

        // 调整旋转中心点
        gc.translate(snackX + GRID_SIZE / 2, snackY - GRID_SIZE / 2);  // 移动到旋转中心
        gc.rotate(angle);              // 旋转食物
        gc.translate(-snackX - GRID_SIZE / 2, -snackY + GRID_SIZE / 2);  // 还原位置

        gc.setFont(Font.font(24));  // 设置字体大小
        // 微调食物字符 "✿" 的位置
        gc.fillText("✿", snackX - 2, snackY - 1);  // 绘制食物

        gc.restore();  // 恢复绘图状态
    }

    /**
     * 使食物旋转，更新旋转角度。
     * 每次调用时，增加角度，确保食物持续旋转。
     */
    @Override
    public void move() {
        angle += 5;  // 增加旋转角度
        if (angle >= 360) angle = 0;  // 如果旋转角度大于等于 360，重置为 0
    }
}

