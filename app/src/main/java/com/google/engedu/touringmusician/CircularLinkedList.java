/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.touringmusician;

import android.graphics.Point;

import java.util.Iterator;

public class CircularLinkedList implements Iterable<Point> {

    private class Node {
        Point point;
        Node prev, next;

        Node(Point p) {
            this.point = p;
        }
    }

    private Node head = null;

    private float distanceBetween(Point from, Point to) {
        return (float) Math.sqrt(Math.pow(from.y - to.y, 2) + Math.pow(from.x - to.x, 2));
    }

    /**
     * Computes the total tour distance by traversing the circular linked list.
     * Returns 0 if the list is empty or contains only one node.
     */
    public float totalDistance() {
        if (head == null || head.next == head) {
            return 0;
        }
        float total = 0;
        Node curr = head;
        do {
            total += distanceBetween(curr.point, curr.next.point);
            curr = curr.next;
        } while (curr != head);
        return total;
    }

    private void firstNode(Node node) {
        head = node;
        head.prev = head;
        head.next = head;
    }

    private void insertNode(Node node, Node curr) {
        node.next = curr;
        node.prev = curr.prev;
        curr.prev = node;
        node.prev.next = node;
    }

    public void insertBeginning(Point p) {
        Node node = new Node(p);
        if (head != null) {
            insertNode(node, head);
            head = node;
        } else {
            firstNode(node);
        }
    }

    /**
     * Inserts a new point next to the nearest existing node in the tour.
     * Chooses the side (before or after the nearest node) that is closer.
     */
    public void insertNearest(Point p) {
        Node node = new Node(p);
        if (head == null) {
            firstNode(node);
            return;
        }

        Node best = head;
        double bestDis = distanceBetween(head.point, p);
        Node curr = head.next;
        // Traverse all nodes including head to find the nearest one
        while (curr != head) {
            double dist = distanceBetween(curr.point, p);
            if (dist < bestDis) {
                bestDis = dist;
                best = curr;
            }
            curr = curr.next;
        }

        // Insert on the side of the nearest node that is closer to p
        if (distanceBetween(p, best.prev.point) < distanceBetween(p, best.next.point)) {
            insertNode(node, best);
        } else {
            insertNode(node, best.next);
        }
    }

    /**
     * Inserts a new point at the position that results in the smallest
     * increase in total tour distance (cheapest insertion heuristic).
     */
    public void insertSmallest(Point p) {
        Node node = new Node(p);
        if (head == null) {
            firstNode(node);
            return;
        }
        if (head.next == head) {
            insertNode(node, head);
            return;
        }

        // Compute total distance once, then evaluate each edge replacement
        float currentTotal = totalDistance();
        Node best = null;
        double bestIncrease = Double.POSITIVE_INFINITY;
        Node curr = head;
        do {
            double increase = distanceBetween(p, curr.point)
                    + distanceBetween(p, curr.next.point)
                    - distanceBetween(curr.point, curr.next.point);
            if (increase < bestIncrease) {
                bestIncrease = increase;
                best = curr.next;
            }
            curr = curr.next;
        } while (curr != head);

        insertNode(node, best);
    }

    public void reset() {
        head = null;
    }

    private class CircularLinkedListIterator implements Iterator<Point> {

        Node current;

        CircularLinkedListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Point next() {
            Point toReturn = current.point;
            current = current.next;
            if (current == head) {
                current = null;
            }
            return toReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return new CircularLinkedListIterator();
    }
}
