package ru.unn.agile.Range.Model;

import java.util.Objects;

public class Boundary {
    public Boundary(final int value, final boolean isIncluded) {
        this.value = value;
        this.isIncluded = isIncluded;
    }

    public Boundary(final Boundary bound) {
        this.value = bound.value();
        this.isIncluded = bound.isIncluded();
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, isIncluded);
    }

    @Override
    public boolean equals(final Object object) throws IllegalArgumentException {
        checkNull(object);
        checkInstance(object);

        Boundary bound = (Boundary) object;
        return (value == bound.value() && isIncluded == bound.isIncluded());
    }

    public int value() {
        return this.value;
    }

    public boolean isIncluded() {
        return this.isIncluded;
    }

    public boolean isLessThan(final int value) {
        if (isIncluded) {
            return this.value <= value;
        }
        return this.value < value;
    }

    public boolean isMoreThan(final int value) {
        if (isIncluded) {
            return this.value >= value;
        }
        return this.value > value;
    }

    private void checkNull(final Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
    }

    private void checkInstance(final Object object) {
        if (!(object instanceof Boundary)) {
            throw new IllegalArgumentException("The argument must belong to the class Boundary");
        }
    }

    private int value;
    private boolean isIncluded;
}
