package me.miquiis.onlyblock.common.capability.interfaces;

public interface IOnlyMoneyBlock extends ISerializable, IPlayerSyncable {

    int getDays();
    int getBankAccount();
    int getCash();
    boolean isFrozen();
    int getFrozenDay();

    void setDays(int days);
    void sumDays(int days);
    void setBankAccount(int amount);
    void setCash(int amount);
    void sumBankAccount(int amount);
    void sumCash(int amount);
    void setFrozen(int frozenDay, boolean frozen);
}
