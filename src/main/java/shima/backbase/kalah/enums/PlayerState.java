package shima.backbase.kalah.enums;

public enum PlayerState {
    TURN(0),
    WAITING(1),
    WINNER(2),
    LOOSER(4),
    EQUAL(5);
    private final int value;

    PlayerState(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
