package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        newNode.prev = last;
        if (last != null) {
            last.next = newNode;
        }
        if (first == null) {
            first = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(value, null, null);
            Node<T> tempPoint = getByIndex(index);
            newNode.prev = tempPoint.prev;
            newNode.next = tempPoint;
            if (tempPoint.prev != null) {
                tempPoint.prev.next = newNode;
            }
            tempPoint.prev = newNode;
            if (newNode.prev == null) {
                first = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T element : list) {
                add(element);
            }
        }
    }

    @Override
    public T get(int index) {
        Node<T> tempPoint = getByIndex(index);
        return tempPoint.data;
    }

    @Override
    public T set(T value, int index) {
        Node<T> tempPoint = getByIndex(index);
        T result = tempPoint.data;
        tempPoint.data = value;
        return result;
    }

    @Override
    public T remove(int index) {
        Node<T> tempPoint = getByIndex(index);
        if (tempPoint.next != null) {
            tempPoint.next.prev = tempPoint.prev;
        }
        if (tempPoint.prev != null) {
            tempPoint.prev.next = tempPoint.next;
        }
        if (tempPoint.prev == null) {
            first = tempPoint.next;
        }
        if (tempPoint.next == null) {
            last = tempPoint.prev;
        }
        size--;
        return tempPoint.data;
    }

    @Override
    public boolean remove(T object) {
        boolean result = false;
        Node<T> tempPoint = first;
        for (int i = 0; i < size; i++) {
            if (tempPoint != null && (tempPoint.data == object || (tempPoint.data != null
                    && tempPoint.data.equals(object)))) {
                exclude(tempPoint);
                result = true;
                break;
            }
            tempPoint = tempPoint.next;
        }
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void exclude(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
        size--;
    }

    private Node<T> getByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index : " + index + " Out bounds of List");
        }
        Node<T> tempPoint = index < (size >> 1) ? first : last;
        boolean direct = tempPoint == first;
        for (int i = direct ? 0 : size - 1; direct ? i < index : i > index;
                i = direct ? i + 1 : i - 1) {
            tempPoint = direct ? tempPoint.next : tempPoint.prev;
        }
        return tempPoint;
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T data;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.data = data;
        }
    }

    public static void main(String[] args) {
        MyLinkedListInterface<String> myLinkedList = new MyLinkedList<>();
        myLinkedList.add("FIRST_ITEM");
        System.out.println(myLinkedList.size());
        boolean firstRemove = myLinkedList.remove("FIRST_ITEM");
        System.out.println(myLinkedList.size());
        myLinkedList.add("SECOND_ITEM");
        System.out.println(myLinkedList.size());
        boolean secondRemove = myLinkedList.remove("FIRST_ITEM");
        System.out.println(myLinkedList.size());
    }
}
