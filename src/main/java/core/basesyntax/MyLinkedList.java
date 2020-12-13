package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private MyNode<T> first;
    private MyNode<T> last;

    public class MyNode<T> {
        T item;
        MyNode<T> next;
        MyNode<T> prev;

        public MyNode(MyNode<T> prev, T item, MyNode<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            last = new MyNode<T>(null, value, null);
            first = last;
        } else {
            MyNode<T> addedElement = new MyNode<T>(last, value, null);
            last.next = addedElement;
            last = addedElement;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            MyNode<T> current = new MyNode<>(null, value, first);
            MyNode<T> nextFromCurrent = first;
            nextFromCurrent.prev = current;
            first = current;
        } else {
            MyNode<T> current = find(index);
            MyNode<T> added = new MyNode<>(current.prev, value, current);
            current.prev.next = added;
            current.prev = added;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return find(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        MyNode<T> current = find(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index == size) {
            throw new IndexOutOfBoundsException();
        }
        MyNode<T> removed = find(index);
        T result = removed.item;
        if (index == 0) {
            if (size == 1) {
                first = null;
                first = last;
            } else {
                first = removed.next;
                first.prev = null;
            }
        } else if (index == size - 1) {
            last = removed.prev;
            last.next = null;
        } else {
            removed.next.prev = removed.prev;
            removed.prev.next = removed.next;
        }
        size--;
        return result;
    }

    @Override
    public boolean remove(T object) {
        MyNode<T> current = first;
        int i = 0;
        for (i = 0; i < size; i++) {
            if (object == current.item || object != null && object.equals(current.item)) {
                remove(i);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private MyNode<T> find(int index) {
        MyNode<T> current = first;
        int count = 0;
        while (count < index) {
            current = current.next;
            count++;
        }
        return current;
    }
}
