package ru.unn.agile.Range.Model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

public class RangeTest {
    @Test
    public void rangeCanBeCreated() {
        Boundary leftBound = new Boundary(1, true);
        Boundary rightBound = new Boundary(3, false);

        Range range = new Range(leftBound, rightBound);

        assertNotNull(range);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rangeCanNotBeCreatedWhenLeftBoundBiggerThanRight() {
        Boundary leftBound = new Boundary(1, true);
        Boundary rightBound = new Boundary(-1, true);

        Range range = new Range(leftBound, rightBound);
    }

    @Test
    public void rangeCanBeCreatedWhenLeftEqualsRightAndBothAreIncluded() {
        Boundary leftBound = new Boundary(0, true);
        Boundary rightBound = new Boundary(0, true);

        Range range = new Range(leftBound, rightBound);

        assertNotNull(range);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rangeCanNotBeCreatedWhenLeftEqualsRightAndOnlyOneIsIncluded() {
        Boundary leftBound = new Boundary(1, true);
        Boundary rightBound = new Boundary(1, false);

        Range range = new Range(leftBound, rightBound);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rangeCanNotBeCreatedWhenLeftBoundIsNull() {
        Boundary rightBound = new Boundary(-1, true);

        Range range = new Range(null, rightBound);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rangeCanNotBeCreatedWhenRightBoundIsNull() {

        Boundary leftBound = new Boundary(1, true);

        Range range = new Range(leftBound, null);

    }

    @Test
    public void sameRangesAreEqual() {
        Boundary leftBound1 = new Boundary(-1, false);
        Boundary rightBound1 = new Boundary(6, false);
        Range range1 = new Range(leftBound1, rightBound1);
        Range range2 = new Range(leftBound1, rightBound1);

        assertTrue(range1.equals(range2));
    }

    @Test
    public void rangesWithSameBoundValuesAndDifferentInclusionsAreNotEqual() {
        Boundary leftBound1 = new Boundary(-1, false);
        Boundary rightBound1 = new Boundary(20, false);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(-1, false);
        Boundary rightBound2 = new Boundary(20, true);
        Range range2 = new Range(leftBound2, rightBound2);

        assertFalse(range1.equals(range2));
    }

    @Test
    public void rangeContainsValuesInsideRange() {
        Boundary leftBound = new Boundary(1, true);
        Boundary rightBound = new Boundary(3, true);
        Range range = new Range(leftBound, rightBound);
        int[] values = {1, 2, 3};

        assertTrue(range.containsValues(values));
    }

    @Test
    public void rangeDoesNotContainValuesOutsideRange() {
        Boundary leftBound = new Boundary(1, true);
        Boundary rightBound = new Boundary(3, true);
        Range range = new Range(leftBound, rightBound);
        int[] values = {-11, 9};

        assertFalse(range.containsValues(values));
    }

    @Test
    public void rangeContainsValuesOnlyOneOfWhichOutsideRange() {
        Boundary leftBound = new Boundary(1, true);
        Boundary rightBound = new Boundary(3, false);
        Range range = new Range(leftBound, rightBound);
        int[] values = {1, 2, 3};

        assertFalse(range.containsValues(values));
    }

    @Test
    public void rangeDoesNotContainValuesWithEmptyInput() {
        Boundary leftBound = new Boundary(1, true);
        Boundary rightBound = new Boundary(3, false);
        Range range = new Range(leftBound, rightBound);
        int[] values = {};

        assertFalse(range.containsValues(values));
    }

    @Test
    public void singlePointRangeContainsRequiredValues() {
        Boundary leftBound = new Boundary(1, true);
        Boundary rightBound = new Boundary(1, true);
        Range range = new Range(leftBound, rightBound);
        int[] values = {1};

        assertTrue(range.containsValues(values));
    }

    @Test
    public void gettingAllPointsOfRangeWithInitBoundaries() {
        Boundary leftBound = new Boundary(-2, true);
        Boundary rightBound = new Boundary(4, true);
        Range range = new Range(leftBound, rightBound);
        int[] values = {-2, -1, 0, 1, 2, 3, 4};

        assertArrayEquals(values, range.getAllPoints());
    }

    @Test
    public void gettingAllPointsOfRangeWithoutLeftInclusion() {
        Boundary leftBound = new Boundary(-2, false);
        Boundary rightBound = new Boundary(4, true);
        Range range = new Range(leftBound, rightBound);
        int[] values = {-1, 0, 1, 2, 3, 4};

        assertArrayEquals(values, range.getAllPoints());
    }

    @Test
    public void gettingAllPointsOfRangeWithoutInclusions() {
        Boundary leftBound = new Boundary(-2, false);
        Boundary rightBound = new Boundary(4, false);
        Range range = new Range(leftBound, rightBound);
        int[] values = {-1, 0, 1, 2, 3};

        assertArrayEquals(values, range.getAllPoints());
    }

    @Test
    public void gettingEndPointsOfRangeWithoutInclusions() {
        Boundary leftBound = new Boundary(-2, false);
        Boundary rightBound = new Boundary(2, false);
        Range range = new Range(leftBound, rightBound);
        int[] values = {-1, 1};

        assertArrayEquals(values, range.getEndPoints());
    }

    @Test
    public void gettingEndPointsOfSinglePointRange() {
        Boundary leftBound = new Boundary(2, true);
        Boundary rightBound = new Boundary(2, true);
        Range range = new Range(leftBound, rightBound);
        int[] values = {2, 2};

        assertArrayEquals(values, range.getEndPoints());
    }

    @Test
    public void gettingEndPointsOfSinglePointRangeWithoutLeftInclusion() {
        Boundary leftBound = new Boundary(2, false);
        Boundary rightBound = new Boundary(3, true);
        Range range = new Range(leftBound, rightBound);
        int[] values = {3, 3};

        assertArrayEquals(values, range.getEndPoints());
    }

    @Test
    public void rangeWithoutInclusionsContainsSameRange() {
        Boundary leftBound = new Boundary(-2, false);
        Boundary rightBound = new Boundary(5, false);
        Range range1 = new Range(leftBound, rightBound);
        Range range2 = new Range(leftBound, rightBound);

        assertTrue(range1.containsRange(range2));
    }

    @Test
    public void rangeWithIncludedLeftBoundaryContainsSameRange() {
        Boundary leftBound = new Boundary(-2, true);
        Boundary rightBound = new Boundary(5, false);
        Range range1 = new Range(leftBound, rightBound);
        Range range2 = new Range(leftBound, rightBound);

        assertTrue(range1.containsRange(range2));
    }

    @Test
    public void rangeWithIncludedRightBoundaryContainsSameRange() {
        Boundary leftBound = new Boundary(-2, false);
        Boundary rightBound = new Boundary(5, true);
        Range range1 = new Range(leftBound, rightBound);
        Range range2 = new Range(leftBound, rightBound);

        assertTrue(range1.containsRange(range2));
    }

    @Test
    public void rangeWithBothIncludedBoundaryContainsSameRange() {
        Boundary leftBound = new Boundary(-2, true);
        Boundary rightBound = new Boundary(5, true);
        Range range1 = new Range(leftBound, rightBound);
        Range range2 = new Range(leftBound, rightBound);

        assertTrue(range1.containsRange(range2));
    }

    @Test
    public void rangeDoesNotContainExtendedRange() {
        Boundary leftBound1 = new Boundary(0, false);
        Boundary rightBound1 = new Boundary(3, true);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(-2, false);
        Boundary rightBound2 = new Boundary(10, true);
        Range range2 = new Range(leftBound2, rightBound2);

        assertFalse(range1.containsRange(range2));
    }

    @Test
    public void rangeContainsContainedRange() {
        Boundary leftBound1 = new Boundary(-1, false);
        Boundary rightBound1 = new Boundary(3, true);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(0, false);
        Boundary rightBound2 = new Boundary(2, true);
        Range range2 = new Range(leftBound2, rightBound2);

        assertTrue(range1.containsRange(range2));
    }

    @Test
    public void rangeDoesNotContainOnlyOverlappingRange() {
        Boundary leftBound1 = new Boundary(-1, false);
        Boundary rightBound1 = new Boundary(3, true);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(-10, false);
        Boundary rightBound2 = new Boundary(2, true);
        Range range2 = new Range(leftBound2, rightBound2);

        assertFalse(range1.containsRange(range2));
    }

    @Test
    public void rangeWithoutInclusionsDoesNotContainRangeWithLeftInclusion() {
        Boundary leftBound1 = new Boundary(-1, false);
        Boundary rightBound1 = new Boundary(3, false);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(-1, true);
        Boundary rightBound2 = new Boundary(3, false);
        Range range2 = new Range(leftBound2, rightBound2);

        assertFalse(range1.containsRange(range2));
    }

    @Test
    public void rangeWithRightInclusionContainsRangeWithoutInclusions() {
        Boundary leftBound1 = new Boundary(-5, false);
        Boundary rightBound1 = new Boundary(3, true);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(-5, false);
        Boundary rightBound2 = new Boundary(3, false);
        Range range2 = new Range(leftBound2, rightBound2);

        assertTrue(range1.containsRange(range2));
    }

    @Test
    public void rangeWithBothInclusionsContainsRangeWithoutInclusions() {
        Boundary leftBound1 = new Boundary(-1, true);
        Boundary rightBound1 = new Boundary(3, true);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(-1, false);
        Boundary rightBound2 = new Boundary(3, false);
        Range range2 = new Range(leftBound2, rightBound2);

        assertTrue(range1.containsRange(range2));
    }

    @Test
    public void rangeWithoutIntPointsContainsSameRange() {
        Boundary leftBound = new Boundary(1, false);
        Boundary rightBound = new Boundary(2, false);
        Range range1 = new Range(leftBound, rightBound);
        Range range2 = new Range(leftBound, rightBound);

        assertTrue(range1.containsRange(range2));
    }

    @Test
    public void rangeContainsContainedRangeWithoutIntPoints() {
        Boundary leftBound1 = new Boundary(-1, true);
        Boundary rightBound1 = new Boundary(16, true);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(10, false);
        Boundary rightBound2 = new Boundary(11, false);
        Range range2 = new Range(leftBound2, rightBound2);

        assertTrue(range1.containsRange(range2));
    }

    @Test
    public void rangeDoesNotContainNotContainedRange() {
        Boundary leftBound1 = new Boundary(-1, true);
        Boundary rightBound1 = new Boundary(6, true);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(10, false);
        Boundary rightBound2 = new Boundary(11, false);
        Range range2 = new Range(leftBound2, rightBound2);

        assertFalse(range1.containsRange(range2));
    }

    @Test
    public void rangeOverlapsSameRange() {
        Boundary leftBound1 = new Boundary(0, true);
        Boundary rightBound1 = new Boundary(4, false);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(0, true);
        Boundary rightBound2 = new Boundary(4, false);
        Range range2 = new Range(leftBound2, rightBound2);

        assertTrue(range1.overlapsRange(range2));
    }

    @Test
    public void rangeDoesNotOverlapAnotherNoOverlappedRange() {
        Boundary leftBound1 = new Boundary(0, true);
        Boundary rightBound1 = new Boundary(4, false);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(5, true);
        Boundary rightBound2 = new Boundary(6, false);
        Range range2 = new Range(leftBound2, rightBound2);

        assertFalse(range1.overlapsRange(range2));
    }

    @Test
    public void rangeOverlapsAnotherOverlappedRange() {
        Boundary leftBound1 = new Boundary(0, true);
        Boundary rightBound1 = new Boundary(4, false);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(3, false);
        Boundary rightBound2 = new Boundary(6, false);
        Range range2 = new Range(leftBound2, rightBound2);

        assertTrue(range1.overlapsRange(range2));
    }

    @Test
    public void rangeOverlapsSameRangeButWithoutInclusions() {
        Boundary leftBound1 = new Boundary(-1, true);
        Boundary rightBound1 = new Boundary(3, false);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(-1, false);
        Boundary rightBound2 = new Boundary(3, false);
        Range range2 = new Range(leftBound2, rightBound2);

        assertTrue(range1.overlapsRange(range2));
    }

    @Test
    public void rangeOverlapsRangeContainOriginRange() {
        Boundary leftBound1 = new Boundary(-1, true);
        Boundary rightBound1 = new Boundary(2, true);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(-2, false);
        Boundary rightBound2 = new Boundary(9, false);
        Range range2 = new Range(leftBound2, rightBound2);

        assertTrue(range1.overlapsRange(range2));
    }

    @Test
    public void rangeDoesNotOverlapRangeWithLeftValueEqualsRightOriginAndIsNotIncluded() {
        Boundary leftBound1 = new Boundary(-1, false);
        Boundary rightBound1 = new Boundary(0, false);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(0, false);
        Boundary rightBound2 = new Boundary(5, false);
        Range range2 = new Range(leftBound2, rightBound2);

        assertFalse(range1.overlapsRange(range2));
    }

    @Test
    public void rangeOverlapsRangeWithLeftValueEqualsRightOriginAndIsIncluded() {
        Boundary leftBound1 = new Boundary(-1, false);
        Boundary rightBound1 = new Boundary(0, true);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(0, true);
        Boundary rightBound2 = new Boundary(5, false);
        Range range2 = new Range(leftBound2, rightBound2);

        assertTrue(range1.overlapsRange(range2));
    }

    @Test
    public void rangeWithInclusionsDoesNotOverlapRangeWithLeftEqualsRightOriginAndIsNotIncluded() {
        Boundary leftBound1 = new Boundary(-10, true);
        Boundary rightBound1 = new Boundary(0, true);
        Range range1 = new Range(leftBound1, rightBound1);
        Boundary leftBound2 = new Boundary(0, false);
        Boundary rightBound2 = new Boundary(6, true);
        Range range2 = new Range(leftBound2, rightBound2);

        assertFalse(range1.overlapsRange(range2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionInEqualsWhenArgumentIsIncorrect() {
        Boundary leftBound = new Boundary(0, true);
        Boundary rightBound = new Boundary(2, true);
        Range range = new Range(leftBound, rightBound);

        range.equals(leftBound);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionInEqualsWhenArgumentIsNull() {
        Boundary leftBound = new Boundary(-19, true);
        Boundary rightBound = new Boundary(20, true);
        Range range = new Range(leftBound, rightBound);

        range.equals(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionInContainsValuesWhenArgumentIsNull() {
        Boundary leftBound = new Boundary(1, true);
        Boundary rightBound = new Boundary(3, false);
        Range range = new Range(leftBound, rightBound);

        range.containsValues(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionInContainsRangeWhenArgumentIsNull() {
        Boundary leftBound = new Boundary(1, true);
        Boundary rightBound = new Boundary(3, false);
        Range range = new Range(leftBound, rightBound);

        range.containsRange(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionInOverlapsRangeWhenArgumentIsNull() {
        Boundary leftBound = new Boundary(1, true);
        Boundary rightBound = new Boundary(3, false);
        Range range = new Range(leftBound, rightBound);

        range.overlapsRange(null);
    }

    @Test
    public void gettingAllPointsOfRangeWithoutIntPoints() {
        Boundary leftBound = new Boundary(10, false);
        Boundary rightBound = new Boundary(11, false);
        Range range = new Range(leftBound, rightBound);

        assertNull(range.getAllPoints());
    }

    @Test
    public void gettingEndPointsOfRangeWithoutIntPoints() {
        Boundary leftBound = new Boundary(-3, false);
        Boundary rightBound = new Boundary(-2, false);
        Range range = new Range(leftBound, rightBound);

        assertNull(range.getEndPoints());
    }
}
