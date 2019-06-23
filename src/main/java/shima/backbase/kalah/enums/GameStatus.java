package shima.backbase.kalah.enums;

public enum GameStatus {
    CREATED(0),
    ENDED(1);
    private final int value;

    GameStatus(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
