package com.github.gabuly.livebyrivermod.leaders.client;
import com.github.gabuly.livebyrivermod.leaders.LeaderSheep;
import com.github.gabuly.livebyrivermod.livebyrivermod;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class LeaderSheepModel<T extends LeaderSheep> extends SheepModel<T> {
    public LeaderSheepModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = SheepModel.createBodyMesh(12, CubeDeformation.NONE);
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 8)
                .addBox(-4.0F, -6.0F, -8.0F, 8.0F, 12.0F, 8.0F), PartPose.offset(0.0F, 5.0F, 2.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }
}

}
