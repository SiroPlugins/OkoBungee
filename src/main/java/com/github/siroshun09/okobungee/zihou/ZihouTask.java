package com.github.siroshun09.okobungee.zihou;

import com.github.siroshun09.okobungee.Configuration;
import com.github.siroshun09.sirolibrary.message.BungeeMessage;

import java.util.TimerTask;

class ZihouTask extends TimerTask {
    @Override
    public void run() {
        BungeeMessage.broadcastWithColor(Configuration.get().getZihouMsg());
    }
}
