package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private ListNode head;
    private ListNode tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        ListNode newNode = new ListNode<>(value);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else if (size == 1) {
            head.setNext(newNode);
            newNode.setPrev(head);
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        ListNode newNode = new ListNode<>(value);
        if (size == 0 && index == 0) {
            head = newNode;
            tail = newNode;
            size++;
            return;
        }
        if (index == 0 && size > 0) {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
            size++;
            return;
        }
        if (size == index) {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
            size++;
            return;
        }
        ListNode temp = findElementByIndex(index);
        newNode.setNext(temp);
        newNode.setPrev(temp.getPrev());
        temp.setPrev(newNode);
        newNode.getPrev().setNext(newNode);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        ListNode temp = findElementByIndex(index);
        return (T) temp.getValue();
    }

    @Override
    public T set(T value, int index) {
        ListNode temp = findElementByIndex(index);
        T oldvalue = (T) temp.getValue();
        temp.setValue(value);
        return oldvalue;
    }

    @Override
    public T remove(int index) {
        ListNode temp = findElementByIndex(index);
        unlink(temp);
        return (T) temp.getValue();
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        boolean found = false;
        ListNode temp = head;
        if (temp.getValue() != null && temp.getValue().equals(object)
                || temp.getValue() == object) {
            unlink(head);
            found = true;
            return found;
        }
        while (temp != tail && !found) {
            if (temp.getValue() != null && temp.getValue().equals(object)
                    || temp.getValue() == object) {
                unlink(temp);
                found = true;
            }
            if (found) {
                return found;
            }
            temp = temp.getNext();
        }
        if (temp.getValue() != null && temp.getValue().equals(object)
                || temp.getValue() == object) {
            unlink(tail);
            found = true;
        }
        return found;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private ListNode findElementByIndex(int index) {
        if (index < 0 || index > size - 1 || isEmpty()) {
            throw new IndexOutOfBoundsException("invalid index " + index);
        }
        if (index == 0) {
            return head;
        }
        if (index == size - 1) {
            return tail;
        }
        if (index > size / 2) {
            ListNode temp = tail;
            index = size - index - 1;
            while (index != 0) {
                temp = temp.getPrev();
                index--;
            }
            return temp;
        }
        ListNode temp = head;
        while (index != 0) {
            temp = temp.getNext();
            index--;
        }
        return temp;
    }

    private void unlink(ListNode<T> unlinkElement) {
        if (unlinkElement == head && size == 1) {
            head = null;
            tail = null;
            size--;
            return;
        }
        if (unlinkElement == tail) {
            tail.getPrev().setNext(null);
            tail = tail.getPrev();
            size--;
            return;
        }
        if (unlinkElement == head) {
            head = unlinkElement.getNext();
            size--;
            return;
        }
        unlinkElement.getNext().setPrev(unlinkElement.getPrev());
        unlinkElement.getPrev().setNext(unlinkElement.getNext());
        size--;
    }

    class ListNode<T> {
        private T value;
        private ListNode prev;
        private ListNode next;

        ListNode(T value) {
            this.value = value;
        }

        public ListNode getNext() {
            return next;
        }

        public ListNode getPrev() {
            return prev;
        }

        public T getValue() {
            return value;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        public void setPrev(ListNode prev) {
            this.prev = prev;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
