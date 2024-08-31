package io.github.chobl.core.init;

import io.github.chobl.TheLittleThingsMod;
import io.github.chobl.common.entities.RecluseEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;

//LINKS
//https://forums.minecraftforge.net/topic/80288-1151-how-to-register-a-entitytype-with-the-deferredregister/
//https://forums.minecraftforge.net/topic/87597-1161-custom-entity-attributes/
//http://jabelarminecraft.blogspot.com/p/minecraft-forge-1721710-apply.html
//https://github.com/MinecraftForge/MinecraftForge/issues/6911
//https://docs.google.com/spreadsheets/d/1nqqieNkw9r4NfI-WypIvgeuZnYB4_QU7BcDXptgzqnM/pub?single=true&gid=0&output=html

@Mod.EventBusSubscriber(modid = TheLittleThingsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeInit<T extends Entity> {
    //initializing entity types: https://forums.minecraftforge.net/topic/80288-1151-how-to-register-a-entitytype-with-the-deferredregister/

    public static final EntityType<RecluseEntity> RECLUSE = registerEntity(EntityType.Builder.create(RecluseEntity::new, EntityClassification.MONSTER).size(1.3F, 2.6F).setUpdateInterval(3).immuneToFire(), "recluse");

    private static final EntityType registerEntity(EntityType.Builder builder, String entityName){
        ResourceLocation entityPath = new ResourceLocation(TheLittleThingsMod.MOD_ID, entityName);
        return (EntityType) builder.build(entityName).setRegistryName(entityPath);
    }

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event){
        try {
            for (Field f : EntityTypeInit.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof EntityType) { //only register things that are EntityTypes
                    event.getRegistry().register((EntityType) obj);
                } else if (obj instanceof EntityType[]) {
                    for (EntityType type : (EntityType[]) obj) {
                        event.getRegistry().register(type);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        initializeAttributes();
    }

    //we only call for the creation of each entity's custom attributes in the GlobalAttributesRegister during the EntityType Registry being called to register entities, and AFTER
    //all fields in this class (the entities) have been registered. means that no error will be thrown since entities will be made before attributes are pushed into their map
    private static void initializeAttributes(){
        GlobalEntityTypeAttributes.put(RECLUSE, RecluseEntity.bakeAttributes().create());
    }

    //<?> is a wildcard operator. means that you can pass whatever you want in that parameter, meaning <EntityType<?>> can have RecluseEntity passed into the
    //wildcard space to make it <EntityType<RecluseEntity>>

    //creating and registering a new entity. calling the builder method to create a new RecluseEntity instance with the name "recluse", with certain parameters
}
