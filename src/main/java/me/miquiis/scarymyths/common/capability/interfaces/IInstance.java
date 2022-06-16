package me.miquiis.scarymyths.common.capability.interfaces;

public interface IInstance<T> {

    T getInstance();
    void setInstance(T instance);
}
