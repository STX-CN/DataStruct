package com.stx.java.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LinkedList<E> implements List<E> {

    private Node head;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        Node p = head;
        int index = 0;
        while (p!=null){
            result[index++] = p.data;
            p = p.next;
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (size==0){
            return a;
        }
        Node p = head;
        int index = 0;
        while (p!=null){
            if (index < a.length) {
                a[index++] = (T)p.data;
            }else {
                break;
            }
            p = p.next;
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        Node newNode = new Node();
        newNode.data = e;
        if (head == null){
            head = newNode;
        }else {
            Node p = head;
            while (p.next != null){
                p = p.next;
            }
            p.next = newNode;
            newNode.prev = p;

//            newNode.next = head;
//            head.prev = newNode;
//            head = newNode;
        }
        size ++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (head == null){
            return false;
        }
        Node p = head;
        while (p != null){
            if (p.data == o){
                break;
            }
        }
        if (p != null){
            p.prev.next = p.next;
            p.next.prev = p.prev;
            p.data = null;
            p.prev = null;
            p.next = null;
            size--;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        Node p = head;
        while (p.next != null){
            Node tmp = p;
            tmp.data = null;
            tmp.prev = null;
            tmp.next = null;
            p = p.next;
        }
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size){
            return null;
        }
        Node p = head;
        int i = 0;
        while (p.next != null){
            if (i == index){
                break;
            }
            i ++;
            p = p.next;
        }
        if (p != null){
            return (E)p.data;
        }
        return null;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size){
            return null;
        }
        Node p = head;
        int i = 0;
        while (p.next != null){
            if (i == index){
                break;
            }
            i++;
            p = p.next;
        }
        p.data = element;
        return element;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index >= size){
            return;
        }
        Node p = head;
        int i = 0;
        while (p.next != null){
            if (i == index){
                break;
            }
            i++;
            p = p.next;
        }
        Node newNode = new Node();
        newNode.data = element;
        p.prev.next = newNode;
        newNode.prev = p.prev;
        newNode.next = p;
        p.prev = newNode;
        size++;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size){
            return null;
        }
        Node p = head;
        int i = 0;
        while (p.next != null){
            if (i == index){
                break;
            }
            i++;
            p = p.next;
        }
        p.prev.next = p.next;
        p.next.prev = p.prev;
        E oldValue = (E)p.data;
        p.data = null;
        p.prev = null;
        p.next = null;
        size--;
        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        if (o==null){
            return -1;
        }
        Node p = head;
        int i = 0;
        while (p.next != null){
            if (o == p.data){
                break;
            }
            i++;
            p = p.next;
        }
        return i>0?i:-1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node p = head;
        while (p.next != null){
            p = p.next;
        }
        int index = size-1;
        while (p.prev != null){
            if (o == p.data){
                break;
            }
            p = p.prev;
            index--;
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    protected class Node<E>{
        public E data;
        public Node prev;
        public Node next;
    }

}
