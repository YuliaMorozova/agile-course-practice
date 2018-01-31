package ru.unn.agile.Range.Model;

import java.util.Objects;

public class Range {

    public Range(final Boundary left, final Boundary right) throws IllegalArgumentException {
        this.checkNull(left);
        this.checkNull(right);
        this.checkCorrectnessOfBoundaries(left, right);

        this.leftBound = new Boundary(left);
        this.rightBound = new Boundary(right);
        this.calculateNumberOfIntPoints();
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfIntPoints, leftBound, rightBound);
    }

    @Override
    public boolean equals(final Object object) throws IllegalArgumentException {
        this.checkNull(object);
        this.checkInstance(object);

        Range range = (Range) object;
        return leftBound.equals(range.leftBound) && rightBound.equals(range.rightBound);
    }

    public int[] getAllPoints() {
        if (numberOfIntPoints == 0) {
            return null;
        }
        int[] result = new int[numberOfIntPoints];
        int firstValue = (leftBound.isIncluded()) ? leftBound.value() : leftBound.value() + 1;
        for (int index = 0; index < numberOfIntPoints; index++) {
            result[index] = firstValue + index;
        }
        return result;
    }

    public int[] getEndPoints() {
        if (numberOfIntPoints == 0) {
            return null;
        }

        int[] result = new int[2];

        result[0] = leftBound.isIncluded() ? leftBound.value() : leftBound.value() + 1;
        result[1] = rightBound.isIncluded() ? rightBound.value() : rightBound.value() - 1;

        return result;
    }

    public boolean containsValues(final int[] values) throws IllegalArgumentException {
        checkNull(values);

        if (values.length == 0) {
            return false;
        }

        for (int value : values) {
            if (!containsPoint(value)) {
                return false;
            }
        }
        return true;
    }

    public boolean containsRange(final Range range) throws IllegalArgumentException {
        checkNull(range);

        boolean leftLessOrEqualInputLeft = leftBound.isLessThan(range.leftBound.value())
                || leftBound.equals(range.leftBound);
        boolean rightMoreOrEqualInputRight = rightBound.isMoreThan(range.rightBound.value())
                || rightBound.equals(range.rightBound);

        return leftLessOrEqualInputLeft && rightMoreOrEqualInputRight;
    }

    public boolean overlapsRange(final Range range) throws IllegalArgumentException {
        checkNull(range);
        return this.overlapsOnRightBound(range.leftBound)
                || this.overlapsOnLeftBound(range.rightBound)
                || range.overlapsOnRightBound(this.leftBound)
                || range.overlapsOnLeftBound(this.rightBound);
    }

    private void calculateNumberOfIntPoints() {
        this.numberOfIntPoints = rightBound.value() - leftBound.value() + 1;
        if (!leftBound.isIncluded()) {
            this.numberOfIntPoints--;
        }
        if (!rightBound.isIncluded()) {
            this.numberOfIntPoints--;
        }
        this.numberOfIntPoints = numberOfIntPoints < 0 ? 0 : this.numberOfIntPoints;
    }

    private boolean containsPoint(final int value) {
        return leftBound.isLessThan(value) && rightBound.isMoreThan(value);
    }

    private boolean overlapsOnRightBound(final Boundary boundary) {
        if (this.containsPoint(boundary.value())) {
            if (rightBound.value() == boundary.value()) {
                return boundary.isIncluded();
            }
            return true;
        }
        return false;
    }

    private boolean overlapsOnLeftBound(final Boundary boundary) {
        if (this.containsPoint(boundary.value())) {
            if (leftBound.value() == boundary.value()) {
                return boundary.isIncluded();
            }
            return true;
        }
        return false;
    }

    private void checkCorrectnessOfBoundaries(final Boundary left, final Boundary right) {
        boolean leftMoreRight = left.value() > right.value();
        boolean leftEqualRight = left.value() == right.value();
        boolean someBoundNoIncluded = !left.isIncluded() || !right.isIncluded();
        if (leftMoreRight || leftEqualRight && someBoundNoIncluded) {
            throw new IllegalArgumentException("Value of left bound should not exceed "
                    + "value of right bound to the inclusion in the range!");
        }
    }

    private void checkNull(final Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
    }

    private void checkInstance(final Object object) {
        if (!(object instanceof Range)) {
            throw new IllegalArgumentException("The argument must belong to the class Range");
        }
    }

    private int numberOfIntPoints;
    private Boundary leftBound;
    private Boundary rightBound;
}
