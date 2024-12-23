package com.atri.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

/**
 * 枚举类，表示不同的音效。
 * 每个音效都关联一个音频文件，并提供播放音效的功能。
 */
public enum SoundEffect {

    /**
     * 按钮点击音效，关联的音频文件路径为 "/sound/button_click.mp3"。
     */
    BUTTON_CLICK("/sound/button_click.mp3"),

    /**
     * 吃掉物品音效，关联的音频文件路径为 "/sound/eat.mp3"。
     */
    EAT("/sound/eat.mp3"),

    /**
     * 按键按下音效，关联的音频文件路径为 "/sound/keyCode_press.mp3"。
     */
    KEYCODE_PRESS("/sound/keyCode_press.mp3"),

    /**
     * 游戏结束音效，关联的音频文件路径为 "/sound/game_over.mp3"。
     */
    GAME_OVER("/sound/game_over.mp3");

    // 音频文件的路径
    private final String filePath;

    // MediaPlayer 用于播放音效
    private MediaPlayer mediaPlayer;

    /**
     * 构造方法，初始化音效的文件路径。
     *
     * @param filePath 音效文件的路径
     */
    SoundEffect(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 播放音效。
     * 如果当前音效已在播放，会先停止并清理旧的播放器资源，然后播放新的音效。
     */
    public void play() {
        // 如果已有播放器实例，停止并销毁旧的播放器
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        // 创建新的音效资源并实例化 MediaPlayer
        Media sound = new Media(Objects.requireNonNull(
                SoundEffect.class.getResource(filePath)).toExternalForm());

        mediaPlayer = new MediaPlayer(sound);

        // 设置音效播放结束后的回调，播放完后销毁 MediaPlayer
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.dispose());

        // 播放音效
        mediaPlayer.play();
    }
}

