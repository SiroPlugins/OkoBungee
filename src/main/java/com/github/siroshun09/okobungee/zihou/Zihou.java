package com.github.siroshun09.okobungee.zihou;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Timer;

public class Zihou {
    private final Timer timer;

    public Zihou() {
        timer = new Timer("Zihou-Timer", false);
        timer.schedule(new ZihouTask(), getStartTime(), 3600000L);
    }

    /**
     * 時報の開始時刻を計算する。
     *
     * @return 時報の開始時刻
     */
    @NotNull
    private Date getStartTime() {
        Instant now = Instant.now();
        Instant temp = now.truncatedTo(ChronoUnit.HOURS);

        while (temp.isBefore(now)) temp = temp.plusSeconds(3600L);

        return Date.from(temp);
    }

    /**
     * 時報を停止する。
     */
    public void stop() {
        timer.cancel();
    }
}
