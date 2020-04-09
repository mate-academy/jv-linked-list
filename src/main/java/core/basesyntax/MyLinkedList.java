package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int elementsData;

    public MyLinkedList() {
        this.first = new Node<T>(null, null, null);;
        this.last = new Node<T>(null, null, null);;
        this.elementsData = 0;
    }

    public static void main(String[] args) {
        MyLinkedList<String> a = new MyLinkedList<>();
        a.add("1");
        a.add("2");
        a.add("3");
        a.remove("3");
        System.out.println(a);
        System.out.println(a.size());
    }

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<T>(null, value, null);
        if (elementsData == 0) {
            this.first = node;
            this.last = node;
            this.elementsData += 1;
            return true;
        }
        this.last.next = node;
        node.prev = this.last;
        node.next = null;
        this.last = node;
        this.elementsData += 1;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > this.elementsData || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + " Size: " + this.elementsData);
        }
        Node<T> node = new Node<T>(null, value, null);
        if (index == 0) {
            this.first.prev = node;
            node.next = this.first;
            this.first = node;
        }
        if (index == this.elementsData) {
            this.last.next = node;
            node.prev = this.last;
            this.last = node;
        }
        if (index > 0 && index < this.elementsData) {
            Node<T> buffer = searching(index);
            buffer.prev.next = node;
            node.next = buffer;
            node.prev = buffer.prev;
            buffer.prev = node;
        }
        this.elementsData += 1;
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
        if (index < this.elementsData && index >= 0) {
            return searching(index).item;
        }
        throw new IndexOutOfBoundsException("Index: " + index + " Size: " + this.elementsData);
    }

    @Override
    public T set(T value, int index) {
        if (index > this.elementsData - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + " Size: " + this.elementsData);
        }
        Node<T> buffer = searching(index);
        T item = buffer.item;
        buffer.item = value;
        return item;
    }

    @Override
    public T remove(int index) {
        if (index > this.elementsData - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + " Size: " + this.elementsData);
        }
        Node<T> buffer = searching(index);
        if (this.elementsData == 1) {
            this.first = null;
            this.last = null;
        }
        if (index == 0 && this.elementsData > 1) {
            this.first = this.first.next;
            this.first.prev = null;
        }
        if (index == this.elementsData - 1 && this.elementsData > 1) {
            this.last = this.last.prev;
            this.last.next = null;
        }
        if (index > 0 && index < this.elementsData - 1) {
            buffer.prev.next = buffer.next;
            buffer.next.prev = buffer.prev;
        }
        this.elementsData -= 1;
        return buffer.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> buffer = this.first;
        for (int i = 0; i < this.elementsData; i++) {
            if (t == null ? buffer.item == null : buffer.item.equals(t)) {
                remove(i);
                return true;
            }
            buffer = buffer.next;
        }
        return false;
    }

    private Node<T> searching(int index) {
        Node<T> buffer = this.first;
        for (int i = 0; i < index; i++) {
            buffer = buffer.next;
        }
        return buffer;
    }

    @Override
    public int size() {
        return this.elementsData;
    }

    @Override
    public boolean isEmpty() {
        return this.elementsData == 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node<T> buffer = this.first;
        while (buffer != this.last) {
            result.append(buffer.item + ", ");
            buffer = buffer.next;
        }
        result.append(this.last != null ? this.last.item : "" + "]");
        return result.toString();
    }

    private static class Node<E> {
        E item;
        MyLinkedList.Node<E> next;
        MyLinkedList.Node<E> prev;

        Node(MyLinkedList.Node<E> prev, E element, MyLinkedList.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return this.item + "";
        }
    }
}
