package ru.unn.agile.Range.Model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BoundaryTest {
    @Test
    public void boundaryCanBeCreated() {
        Boundary bound = new Boundary(1, true);

        assertNotNull(bound);
    }

    @Test
    public void boundaryCopyCanBeCreated() {
        Boundary bound = new Boundary(1, true);
        Boundary copyBound = new Boundary(bound);

        assertEquals(1, copyBound.value());
        assertEquals(true, copyBound.isIncluded());
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionInEqualsWhenArgumentIsIncorrect() {
        Boundary bound = new Boundary(1, true);
        Object o = new Object();
        bound.equals(o);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionInEqualsWhenArgumentIsNull() {
        Boundary bound = new Boundary(10, false);

        bound.equals(null);
    }

    @Test
    public void sameBoundariesAreEqual() {
        Boundary bound1 = new Boundary(1, true);
        Boundary bound2 = new Boundary(1, true);

        assertTrue(bound1.equals(bound2));
    }

    @Test
    public void differentBoundariesAreNotEqual() {
        Boundary bound1 = new Boundary(1, true);
        Boundary bound2 = new Boundary(2, true);

        assertFalse(bound1.equals(bound2));
    }

    @Test
    public void boundaryIsLessThanBiggerValue() {
        Boundary bound = new Boundary(1, true);

        assertTrue(bound.isLessThan(2));
    }

    @Test
    public void boundaryIsNotLessThanLowerValue() {
        Boundary bound = new Boundary(3, true);

        assertFalse(bound.isLessThan(2));
    }

    @Test
    public void boundaryIsMoreThanLowerValue() {
        Boundary bound = new Boundary(3, true);

        assertTrue(bound.isMoreThan(2));
    }

    @Test
    public void boundaryIsNotMoreThanBiggerValue() {
        Boundary bound = new Boundary(1, true);

        assertFalse(bound.isMoreThan(2));
    }

    @Test
    public void boundaryWithIncludedValueIsMoreThanLowerValue() {
        Boundary bound = new Boundary(2, true);

        assertTrue(bound.isMoreThan(2));
    }

    @Test
    public void boundaryWithoutIncludedValueIsNotMoreThanBiggerValue() {
        Boundary bound = new Boundary(2, false);

        assertFalse(bound.isMoreThan(2));
    }

    @Test
    public void boundaryWithIncludedValueIsLessThanBiggerValue() {
        Boundary bound = new Boundary(5, true);

        assertTrue(bound.isLessThan(5));
    }

    @Test
    public void boundaryWithoutIncludedValueIsNotLessThanLowerValue() {
        Boundary bound = new Boundary(3, false);

        assertFalse(bound.isLessThan(3));
    }
}
