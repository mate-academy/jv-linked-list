package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Box<T> head;
    private Box<T> last;

    @Override
    public void add(T value) {
        if (size == 0) {
            Box newBox = new Box(value, null, null);
            head = last = newBox;
            size++;
        } else {
            Box newBox = new Box(value, null, last);
            last.next = newBox;
            last = newBox;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else {
            Box<T> boxToMoveAhead = searchByIndex(index);
            Box newBox = new Box(value, boxToMoveAhead, boxToMoveAhead.previous);
            boxToMoveAhead.previous = newBox;
            newBox.next.previous = newBox;
            if (index == 0) {
                head = newBox;
            }
            size++;
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Box<T> boxToChangeValue = searchByIndex(index);
        boxToChangeValue.value = value;
    }

    @Override
    public T remove(int index) {
        Box<T> boxToRemove = searchByIndex(index);
        final Object elementToRemove = boxToRemove.value;
        if (index < 0 || size < index) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1) {
            head = last = null;
            size--;
            return (T) elementToRemove;
        }
        if (boxToRemove == head) {
            boxToRemove.next.previous = null;
            head = boxToRemove.next;
            size--;
            return (T) elementToRemove;
        } else if (boxToRemove == last) {
            last = boxToRemove.previous;
            last.next = null;
            size--;
            return (T) elementToRemove;
        } else {
            boxToRemove.previous.next = boxToRemove.next;
            boxToRemove.next.previous = boxToRemove.previous;
            size--;
            return (T) elementToRemove;
        }
    }

    @Override
    public T remove(T t) {
        int indexToRemove = searchByValue(t);
        if (indexToRemove == size) {
            return null;
        }
        T elementToRemove = remove(indexToRemove);
        return elementToRemove;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public int searchByValue(T value) {
        Box interatorBox = head;
        int count = 0;
        while (interatorBox.next != null) {
            if (interatorBox.value == value || value != null & value.equals(interatorBox.value)) {
                return count;
            }
            interatorBox = interatorBox.next;
            count++;
        }
        return size;
    }

    public Box <T> searchByIndex(int index) {
        if (index < 0 || size <= index) {
            throw new IndexOutOfBoundsException();
        }
        if (size / 2 > index) {
            int count = 0;
            Box iteratorBox = head;
            while (count < index) {
                iteratorBox = iteratorBox.next;
                count++;
            }
            return iteratorBox;
        } else {
            Box iteratorBox = last;
            int count = size - 1;
            while (count > index) {
                iteratorBox = iteratorBox.previous;
                count--;
            }
            return iteratorBox;
        }
    }

    private static class Box<T> {
        private T value;
        private Box<T> next;
        private Box<T> previous;

        Box(T value, Box<T> next, Box<T> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }
}
