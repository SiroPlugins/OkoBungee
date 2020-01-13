package com.github.siroshun09.okobungee.zihou;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Timer;

/**
 * 時報の管理クラス。
 */
public class Zihou {
    private static Zihou instance;
    private final Timer timer;

    /**
     * コンストラクタ。
     * <p>
     * インスタンスが生成された時点で次の時報が予約される。
     */
    private Zihou() {
        timer = new Timer("Zihou-Timer", false);
        timer.schedule(new ZihouTask(), getStartTime(), 3600000L);
        instance = this;
    }

    public static Zihou get() {
        if (instance == null) {
            new Zihou();
        }
        return instance;
    }

    /**
     * 次回の時報の開始時刻を計算する。
     *
     * @return 次回の時報の開始時刻
     */
    @NotNull
    private Date getStartTime() {
        Instant now = Instant.now();
        Instant temp = now.truncatedTo(ChronoUnit.HOURS);
        while (temp.isBefore(now)) {
            temp = temp.plusSeconds(3600L);
        }
        return Date.from(temp);
    }

    /**
     * 時報を停止する。
     */
    public void stop() {
        timer.cancel();
    }
}
