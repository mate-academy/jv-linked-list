package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private MyLinkedList.Node<T>[] array;
    private MyLinkedList.Node<T> header = new MyLinkedList.Node<T>(null, null, null);

    public MyLinkedList() {
        this.array = new MyLinkedList.Node[0];
    }

    @Override
    public boolean add(T value) {
        MyLinkedList.Node<T> node = new MyLinkedList.Node<T>(header, value, header);
        grow();
        if (this.array.length == 1) {
            this.array[0] = node;
            return true;
        }
        this.array[this.array.length - 1] = node;
        node.prev = this.array[this.array.length - 2];
        this.array[this.array.length - 2].next = node;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > this.array.length) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index
                    + " Size: " + this.array.length);
        }
        grow();
        MyLinkedList.Node<T> node = new MyLinkedList.Node<T>(header, value, header);
        for (int i = this.array.length - 1; i > index; i--) {
            this.array[i] = this.array[i - 1];
        }
        node.prev = index > 1 ? this.array[index - 1] : header;
        node.next = index < this.array.length - 2 ? this.array[index + 1] : header;
        this.array[index] = node;
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
        if (index < array.length) {
            return this.array[index].item;
        }
        throw new ArrayIndexOutOfBoundsException("Index: " + index + " Size: " + this.array.length);
    }

    @Override
    public T set(T value, int index) {
        if (index > array.length) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index
                    + " Size: " + this.array.length);
        }
        T buffer = this.array[index].item;
        this.array[index].item = value;
        changingPrevAndNextItems(index);
        return buffer;
    }

    @Override
    public T remove(int index) {
        if (index < this.array.length) {
            T bufferedItem = this.array[index].item;
            if (index < 1 && this.array.length == 1) {
                this.array = new MyLinkedList.Node[0];
                return bufferedItem;
            }
            cut(index);
            changingPrevAndNextItems(index);
            return bufferedItem;
        }
        throw new ArrayIndexOutOfBoundsException("Index: " + index + " Size: " + this.array.length);
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < this.array.length; i++) {
            if ((t == null && this.array[i].item == null)
                    || (this.array[i].item.equals(t))) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    private void changingPrevAndNextItems(int index) {
        for (int i = this.array.length - (this.array.length - 1);
                i < this.array.length - 1; i++) {
            this.array[i].next = this.array[i + 1].next;
            this.array[i].prev = this.array[i - 1].prev;
        }
        this.array[0].next = this.array.length > 1 ? this.array[1] : header;
        this.array[0].prev = header;
        this.array[this.array.length - 1].prev = this.array.length > 1
                ? this.array[this.array.length - 2] : header;
        this.array[this.array.length - 1].next = header;
    }

    @Override
    public int size() {
        return this.array.length;
    }

    @Override
    public boolean isEmpty() {
        return this.array.length == 0;
    }

    private void grow() {
        MyLinkedList.Node<T>[] cloneOfArray = this.array.clone();
        this.array = new MyLinkedList.Node[this.array.length + 1];
        System.arraycopy(cloneOfArray, 0, this.array, 0, cloneOfArray.length);
    }

    private void cut(int index) {
        MyLinkedList.Node<T>[] cloneOfArray = this.array.clone();
        this.array = new MyLinkedList.Node[this.array.length - 1];
        System.arraycopy(cloneOfArray, 0, this.array, 0, index);
        System.arraycopy(cloneOfArray, index + 1, this.array, index,
                this.array.length - index);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < this.array.length; i++) {
            result.append(",");
            result.append(this.array[i]);
        }
        result.append("]");
        if (result.length() > 2) {
            result.deleteCharAt(1);
        }
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
