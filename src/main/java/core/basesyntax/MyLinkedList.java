package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public Node<T> before = null;
    public Node<T> second = null;
    private int size = 0;

    private Node<T> getNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (before == null) {
            throw new NullPointerException();
        }
        Node<T> currentGet = before;
        int counter = 0;
        while (counter != index) {
            currentGet = currentGet.next;
            counter++;
        }
        return currentGet;
    }

    @Override
    public boolean add(T value) {
        Node<T> newValue = new Node<>(value);
        if (before == null) {
            before = newValue;
            second = newValue;
        } else {
            second.prev = second;
            second.next = newValue;
            second = newValue;
            second.next = null;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 0) {
            add(value);
        } else if (index > 0 && index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> current = before;
            Node<T> last = getNode(size - 1);
            last.next = null;
            current.prev = second;
            add(value);
            before = second;
            second.prev = null;
            second.next = current;
        } else if (index < size) {
            Node<T> current = getNode(index);
            Node<T> currentPre = getNode(index - 1);
            add(value);
            current.prev = second;
            currentPre.next = second;
            second.prev = currentPre;
            second.next = current;
        }
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
        return getNode(index).obj;
    }

    @Override
    public T set(T value, int index) {
        Node<T> setter = getNode(index);
        Node<T> currentSet = before;
        T result = setter.obj;
        while (true) {
            if (currentSet.equals(setter)) {
                currentSet.obj = value;
                return result;
            }
            currentSet = currentSet.next;
        }
    }

    @Override
    public T remove(int index) {
        Node<T> remove = getNode(index);
        if (index == 0) {
            before = remove.next;
            size--;
            return remove.obj;
        } else if (index == size - 1) {
            getNode(index - 1).next = null;
            size--;
            return remove.obj;
        }
        Node<T> next = getNode(index + 1);
        Node<T> prev = getNode(index - 1);
        next.prev = prev;
        prev.next = next;
        size--;
        T result = remove.obj;
        return result;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if (t == null ? getNode(i).obj == t : getNode(i).obj.equals(t)) {
                remove(i);
                return true;
            }
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

    private class Node<T> {
        public T obj;
        Node<T> next;
        Node<T> prev;

        public Node(T obj) {
            this.obj = obj;
            this.next = null;
            this.prev = null;
        }
    }
}


