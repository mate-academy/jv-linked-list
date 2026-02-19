package core.basesyntax;

public class CheckIndexForAdd {
    public void checkIndexForAdd(int index, int size) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size
            );
        }
    }
}
