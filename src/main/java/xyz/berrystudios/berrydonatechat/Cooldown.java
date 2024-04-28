package xyz.berrystudios.berrydonatechat;

public class Cooldown {
    private final String player;
    private final long addedAt;

    public Cooldown(String player) {
        this.player = player;
        this.addedAt = System.currentTimeMillis();
    }
    public String getPlayer() {
        return player;
    }
    public long getAddedAt() {
        return addedAt;
    }
}



