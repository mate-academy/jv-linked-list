package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int amountOfElements = 0;
    private Box<T> firstBox;
    private Box<T> lastBox;

    @Override
    public void add(T value) {
        if (amountOfElements == 0) {
            Box newBox = new Box(value, null, null);
            firstBox = lastBox = newBox;
            amountOfElements++;
        } else {
            Box newBox = new Box(value, null, lastBox);
            lastBox.linkToNextBox = newBox;
            lastBox = newBox;
            amountOfElements++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > amountOfElements) {
            throw new IndexOutOfBoundsException();
        }
        if (index == amountOfElements) {
            add(value);
        } else {
            Box<T> boxToMoveAhead = searchByIndex(index);
            Box newBox = new Box(value, boxToMoveAhead, boxToMoveAhead.linkToPrevBox);
            boxToMoveAhead.linkToPrevBox = newBox;
            newBox.linkToNextBox.linkToNextBox = newBox;
            amountOfElements++;
            if (index == 0) {
                firstBox = newBox;
                amountOfElements++;
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Box<T> elementToGet = searchByIndex(index);
        return elementToGet.value;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index > amountOfElements) {
            throw new IndexOutOfBoundsException();
        }
        Box<T> boxToChangeValue = searchByIndex(index);
        boxToChangeValue.value = value;
    }

    @Override
    public T remove(int index) {
        Box<T> boxToRemove = searchByIndex(index);
        final Object elementToRemove = boxToRemove.value;
        if (index < 0 || amountOfElements < index) {
            throw new IndexOutOfBoundsException();
        }
        if (amountOfElements == 1) {
            firstBox = lastBox = null;
            amountOfElements--;
            return (T) elementToRemove;
        }
        if (boxToRemove == firstBox) {
            boxToRemove.linkToNextBox.linkToPrevBox = null;
            firstBox = boxToRemove.linkToNextBox;
            amountOfElements--;
            return (T) elementToRemove;
        } else if (boxToRemove == lastBox) {
            lastBox = boxToRemove.linkToPrevBox;
            lastBox.linkToNextBox = null;
            amountOfElements--;
            return (T) elementToRemove;
        } else {
            boxToRemove.linkToPrevBox.linkToNextBox = boxToRemove.linkToNextBox;
            boxToRemove.linkToNextBox.linkToPrevBox = boxToRemove.linkToPrevBox;
            amountOfElements--;
            return (T) elementToRemove;
        }
    }

    @Override
    public T remove(T t) {
        int indexToRemove = searchByValue(t);
        if (indexToRemove == amountOfElements) {
            return null;
        }
        T elementToRemove = remove(indexToRemove);
        return elementToRemove;
    }

    @Override
    public int size() {
        return amountOfElements;
    }

    @Override
    public boolean isEmpty() {
        return amountOfElements == 0;
    }

    public int searchByValue(T value) {
        Box interatorBox = firstBox;
        int count = 0;
        while (interatorBox.linkToNextBox != null) {
            if (interatorBox.value == value || value != null & value.equals(interatorBox.value)) {
                return count;
            }
            interatorBox = interatorBox.linkToNextBox;
            count++;
        }
        return amountOfElements;
    }

    public Box searchByIndex(int index) {
        if (index < 0 || amountOfElements <= index) {
            throw new IndexOutOfBoundsException();
        }
        if (amountOfElements / 2 > index) {
            int count = 0;
            Box iteratorBox = firstBox;
            while (count < index) {
                iteratorBox = iteratorBox.linkToNextBox;
                count++;
            }
            return iteratorBox;
        } else {
            Box iteratorBox = lastBox;
            int count = amountOfElements - 1;
            while (count > index) {
                iteratorBox = iteratorBox.linkToPrevBox;
                count--;
            }
            return iteratorBox;
        }
    }

    private static class Box<T> {
        T value;
        Box<T> linkToNextBox;
        Box<T> linkToPrevBox;

        Box(T value, Box<T> linkToNextBox, Box<T> linkToPrevBox) {
            this.value = value;
            this.linkToNextBox = linkToNextBox;
            this.linkToPrevBox = linkToPrevBox;
        }
    }
}
