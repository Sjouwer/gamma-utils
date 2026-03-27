package io.github.sjouwer.gammautils.mixin;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TextureAtlas.class)
public interface SpriteAtlasHolderInvoker {

    @Invoker("getSprite")
    public TextureAtlasSprite invokeGetSprite(Identifier objectId);
}
