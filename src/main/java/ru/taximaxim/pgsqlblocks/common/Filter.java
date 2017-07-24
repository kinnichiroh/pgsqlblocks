package ru.taximaxim.pgsqlblocks.common;

import java.util.ArrayList;
import java.util.List;

public abstract class Filter<T> {

    protected final List<FilterListener> listeners = new ArrayList<>();

    protected T value;

    protected final List<FilterCondition> supportedConditions;

    protected final FilterValueType valueType;

    protected boolean isActive = false;

    protected Filter(FilterValueType valueType) {
        this.valueType = valueType;
        this.supportedConditions = FilterCondition.getConditionsForValueType(this.valueType);
    }

    protected FilterCondition condition = FilterCondition.NONE;


    public List<FilterCondition> getSupportedConditions() {
        return supportedConditions;
    }

    public void setCondition(FilterCondition condition) {
        if (condition == null) {
            throw new IllegalArgumentException("Filter condition cannot be null");
        }
        if (this.condition == condition) {
            return;
        }
        if (!getSupportedConditions().contains(condition)) {
            throw new IllegalArgumentException("Filter does not support condition: " + condition);
        }
        boolean isActiveBeforeSetCondition = isActive();
        this.condition = condition;
        boolean isActiveAfterSetCondition = isActive();
        if (isActiveBeforeSetCondition == isActiveAfterSetCondition) {
            listeners.forEach(listener -> listener.filterConditionChanged(this));
        }
        setActive(isActiveAfterSetCondition);
    }

    public T getValue() {
        return value;
    }

    public FilterCondition getCondition() {
        return condition;
    }

    public void setValue(T value) {
        if ((this.value == null && value == null) || (this.value != null && this.value.equals(value))) {
            return;
        }
        boolean isActiveBeforeSetValue = isActive();
        this.value = value;
        boolean isActiveAfterSetValue = isActive();
        if (isActiveBeforeSetValue == isActiveAfterSetValue) {
            listeners.forEach(listener -> listener.filterValueChanged(this));
        }
        setActive(isActiveAfterSetValue);
    }

    protected void setActive(boolean isActive) {
        if (this.isActive != isActive) {
            this.isActive = isActive;
            if (this.isActive) {
                listeners.forEach(listener -> listener.filterDidActivate(this));
            }
            else {
                listeners.forEach(listener -> listener.filterDidDeactivate(this));
            }
        }
        this.isActive = isActive;
    }

    public abstract boolean filter(T actualValue);

    public void reset() {
        condition = FilterCondition.NONE;
        value = null;
    }

    public boolean isActive() {
        if (condition == FilterCondition.NONE) {
            return false;
        }
        if (value == null) {
            return false;
        }
        return true;
    }

    public void addListener(FilterListener listener) {
        listeners.add(listener);
    }

    public void removeListener(FilterListener listener) {
        listeners.remove(listener);
    }

}

