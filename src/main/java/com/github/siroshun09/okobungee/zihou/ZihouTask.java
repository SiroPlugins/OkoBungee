package com.github.siroshun09.okobungee.zihou;

import com.github.siroshun09.okobungee.OkoBungee;
import com.github.siroshun09.okobungee.utils.MessageUtils;

import java.time.LocalTime;
import java.util.TimerTask;

class ZihouTask extends TimerTask {
    @Override
    public void run() {
        LocalTime time = LocalTime.now();

        MessageUtils.broadcast(OkoBungee.getInstance().getConfig().getConfiguration().getString("ZihouMessage")
                .replace("%h", String.valueOf(time.getHour()))
                .replace("%m", String.valueOf(time.getMinute()))
                .replace("%s", String.valueOf(time.getSecond())));
    }
}
