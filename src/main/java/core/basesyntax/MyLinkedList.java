package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        first = new Node<>(null, null, null);
        last = new Node<>(null, null, null);
    }

    private void checkIndexException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void add(T value) {
        Node<T> newElem;
        if (first.item == null) {
            newElem = new Node<>(null, value, last);
            first = newElem;
            last.prev = first;
        } else if (last.item == null) {
            newElem = new Node<>(first, value, null);
            last = newElem;
            first.next = last;
        } else {
            newElem = new Node<>(last, value, null);
            last.next = newElem;
            last = newElem;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            checkIndexException(index);
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> result = first;
            for (int i = 0; i < size; i++) {
                if (index == i) {
                    Node<T> newElem = new Node<>(result.prev, value, result.prev.next);
                    result.prev.next = newElem;
                    result.prev = newElem;
                    size++;
                    break;
                }
                result = result.next;
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
        checkIndexException(index);
        Node<T> result = first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.item;
    }

    @Override
    public void set(T value, int index) {
        checkIndexException(index);
        Node<T> result = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                result.item = value;
            }
            result = result.next;
        }
    }

    @Override
    public T remove(int index) {
        checkIndexException(index);
        Node<T> result = first;
        for (int i = 0; i < size; i++) {
            if (index == 0) {
                result.next.prev = result.prev;
                first = result.next;
                break;
            } else if (index == i && i < size - 1) {
                result.prev.next = result.next;
                result.next.prev = result.prev;
                break;
            } else if (index == i && i == size - 1) {
                result.prev.next = result.next;
                last = result.prev;
                break;
            }
            result = result.next;
        }
        size--;
        return result.item;
    }

    @Override
    public T remove(T t) {
        Node<T> result = first;
        for (int i = 0; i < size; i++) {
            if (result.item == t) {
                return remove(i);
            }
            result = result.next;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }


    public static void main(String[] args) {
        MyLinkedList<String> myLinkedList = new MyLinkedList<>();
        myLinkedList.add("First");
        myLinkedList.add("Second");
        myLinkedList.add("Third");
        myLinkedList.add("First");
        myLinkedList.add("Second");
        myLinkedList.add("Third");
        String actualFirst = myLinkedList.remove(0);
        String actualSecond = myLinkedList.remove(3);
        String actualThird = myLinkedList.remove(3);
        System.out.println(myLinkedList.get(0));
    }
}
