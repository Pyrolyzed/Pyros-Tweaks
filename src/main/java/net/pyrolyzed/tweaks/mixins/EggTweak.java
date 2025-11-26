package net.pyrolyzed.tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.pyrolyzed.tweaks.Config;

@Mixin(ThrownEgg.class)
public abstract class EggTweak {
    private static final int HATCH_CHANCE = Config.HATCH_CHANCE.getAsInt();

    @Inject(
        method = "onHit",
        at = @At("HEAD"),
        cancellable = true
    )
    private void injectOnHit(HitResult hitResult, CallbackInfo callbackInfo) {
        ThrownEgg self = (ThrownEgg)(Object)this;
        Level level = self.level();
        if (level.isClientSide) {
            return;
        }

        callbackInfo.cancel();

        if (level.random.nextInt(HATCH_CHANCE) == 0) {
            int count = 1;
            if (level.random.nextInt(32) == 0) {
                count = 4;
            }

            for (int i = 0; i < count; i++) {
                Chicken chicken = EntityType.CHICKEN.create(level);
                if (chicken != null) {
                    chicken.setAge(-24000);
                    chicken.moveTo(self.getX(), self.getY(), self.getZ(), self.getYRot(), 0.0F);
                    level.addFreshEntity(chicken);
                }
            }
        }

        level.broadcastEntityEvent(self, (byte)3);
        self.discard();
    }
}
