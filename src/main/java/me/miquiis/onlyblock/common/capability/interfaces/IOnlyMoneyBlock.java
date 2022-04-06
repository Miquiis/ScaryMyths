package me.miquiis.onlyblock.common.capability.interfaces;

public interface IOnlyMoneyBlock extends ISerializable, IPlayerSyncable {

    String getPin();
    int getDays();
    int getBankAccount();
    int getCash();
    boolean isFrozen();
    int getFrozenDay();
    boolean hasBusiness();
    boolean hasATM();

    void setBusiness(boolean hasBusiness);
    void setPin(String pin);
    void setHasATM(boolean hasATM);
    void setDays(int days);
    void sumDays(int days);
    void setBankAccount(int amount);
    void setCash(int amount);
    void sumBankAccount(int amount);
    void sumCash(int amount);
    void setFrozen(int frozenDay, boolean frozen);
    void reset();
}
