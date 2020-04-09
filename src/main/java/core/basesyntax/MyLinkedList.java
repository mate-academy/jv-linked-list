package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first = null;
    private Node<T> last = null;
    private int size = 0;

    @Override
    public boolean add(T value) {
        Node<T> newValue = new Node<>(value);
        if (first == null) {
            first = newValue;
            last = newValue;
        } else {
            Node<T> raw = last;
            last.next = newValue;
            last = newValue;
            last.prev = raw;
            last.next = null;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> node = new Node<>(value);
        if (size == index) {
            add(value);
            return;
        } else if (index == 0) {
            first.prev = node;
            node.next = first;
            first = node;
        } else {
            Node<T> current = getNode(index);
            Node<T> currentPre = current.prev;
            node.next = current;
            node.prev = currentPre;
            current.prev = node;
            currentPre.next = node;
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
        return getNode(index).obj;
    }

    @Override
    public T set(T value, int index) {
        Node<T> setter = getNode(index);
        T result = setter.obj;
        setter.obj = value;
        return result;

    }

    @Override
    public T remove(int index) {
        Node<T> remove = getNode(index);
        if (index == 0) {
            first = remove.next;
            size--;
            return remove.obj;
        } else if (index == size - 1) {
            getNode(index - 1).next = null;
            size--;
            return remove.obj;
        }
        remove.next.prev = remove.prev; //+ remove.next
        remove.prev.next = remove.next; //-
        size--;
        return remove.obj;
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

    private static class Node<T> {
        public T obj;
        Node<T> next;
        Node<T> prev;

        public Node(T obj) {
            this.obj = obj;
            this.next = null;
            this.prev = null;
        }
    }

    private Node<T> getNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentGet = first;
        int counter = 0;
        while (counter != index) {
            currentGet = currentGet.next;
            counter++;
        }
        return currentGet;
    }
}
