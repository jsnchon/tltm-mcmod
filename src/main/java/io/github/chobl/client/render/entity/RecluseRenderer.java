package io.github.chobl.client.render.entity;

import io.github.chobl.TheLittleThingsMod;
import io.github.chobl.client.render.model.entity.RecluseModel;
import io.github.chobl.common.entities.RecluseEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RecluseRenderer extends MobRenderer<RecluseEntity, RecluseModel<RecluseEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TheLittleThingsMod.MOD_ID, "textures/entities/recluse/recluse_f1.png");

    public RecluseRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new RecluseModel<>(), 0.7F);
    }

    public ResourceLocation getEntityTexture(RecluseEntity entity) {
        return TEXTURE;
    }
}
