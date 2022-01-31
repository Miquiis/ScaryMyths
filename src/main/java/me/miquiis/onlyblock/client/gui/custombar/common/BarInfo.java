package me.miquiis.onlyblock.client.gui.custombar.common;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.UUID;

public class BarInfo {

    private final UUID uniqueID;
    protected ITextComponent text;
    protected float percent;
    protected ResourceLocation texture;

    public BarInfo(UUID uniqueID, ITextComponent text)
    {
        this.uniqueID = uniqueID;
        this.text = text;
        this.texture = texture;
        this.percent = 1.0f;
    }

    public BarInfo(UUID uniqueID, ITextComponent text, float percent)
    {
        this.uniqueID = uniqueID;
        this.text = text;
        this.percent = percent;
    }

    public void setPercent(float percent)
    {
        this.percent = percent;
    }

    public void setText(ITextComponent text)
    {
        this.text = text;
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public ITextComponent getText() {
        return text;
    }

    public float getPercent() {
        return percent;
    }

    public ResourceLocation getTexture() {
        return texture;
    }
}
