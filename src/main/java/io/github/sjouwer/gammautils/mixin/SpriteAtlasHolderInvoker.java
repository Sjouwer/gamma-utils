package io.github.sjouwer.gammautils.mixin;

import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpriteAtlasTexture.class)
public interface SpriteAtlasHolderInvoker {

    @Invoker("getSprite")
    public Sprite invokeGetSprite(Identifier objectId);
}
