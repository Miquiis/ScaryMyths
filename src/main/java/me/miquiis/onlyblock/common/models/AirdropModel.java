package me.miquiis.onlyblock.common.models;// Made with Blockbench 4.1.5
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.miquiis.onlyblock.common.entities.AirdropEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class AirdropModel extends EntityModel<AirdropEntity> {
	private final ModelRenderer Bars;
	private final ModelRenderer Bars2;
	private final ModelRenderer Bars3;
	private final ModelRenderer Bars4;
	private final ModelRenderer Bars5;
	private final ModelRenderer Bars6;
	private final ModelRenderer Parachute;
	private final ModelRenderer cube_r1;
	private final ModelRenderer Holders;
	private final ModelRenderer BottomBorder;
	private final ModelRenderer SidesBorder;
	private final ModelRenderer Parachuterope;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer Blanket;
	private final ModelRenderer bb_main;

	public AirdropModel() {
		textureWidth = 256;
		textureHeight = 256;

		Bars = new ModelRenderer(this);
		Bars.setRotationPoint(0.0F, 24.0F, 0.0F);
		Bars.setTextureOffset(132, 100).addBox(-6.0F, -16.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		Bars.setTextureOffset(130, 123).addBox(-4.0F, -16.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		Bars.setTextureOffset(128, 46).addBox(-2.0F, -16.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		Bars.setTextureOffset(128, 25).addBox(0.0F, -16.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		Bars.setTextureOffset(128, 10).addBox(2.0F, -16.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		Bars.setTextureOffset(62, 126).addBox(4.0F, -16.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		Bars.setTextureOffset(46, 125).addBox(6.0F, -16.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);

		Bars2 = new ModelRenderer(this);
		Bars2.setRotationPoint(0.0F, 24.0F, 0.0F);
		Bars2.setTextureOffset(16, 125).addBox(-6.0F, -1.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		Bars2.setTextureOffset(94, 124).addBox(-4.0F, -1.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		Bars2.setTextureOffset(78, 123).addBox(-2.0F, -1.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		Bars2.setTextureOffset(114, 122).addBox(0.0F, -1.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		Bars2.setTextureOffset(0, 120).addBox(2.0F, -1.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		Bars2.setTextureOffset(76, 106).addBox(4.0F, -1.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		Bars2.setTextureOffset(40, 106).addBox(6.0F, -1.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);

		Bars3 = new ModelRenderer(this);
		Bars3.setRotationPoint(0.0F, 24.0F, 0.0F);
		Bars3.setTextureOffset(80, 82).addBox(-4.0F, -15.0F, -8.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars3.setTextureOffset(72, 82).addBox(-6.0F, -15.0F, -8.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars3.setTextureOffset(68, 82).addBox(-2.0F, -15.0F, -8.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars3.setTextureOffset(64, 82).addBox(0.0F, -15.0F, -8.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars3.setTextureOffset(60, 82).addBox(2.0F, -15.0F, -8.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars3.setTextureOffset(78, 55).addBox(4.0F, -15.0F, -8.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars3.setTextureOffset(76, 6).addBox(6.0F, -15.0F, -8.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);

		Bars4 = new ModelRenderer(this);
		Bars4.setRotationPoint(9.0F, 24.0F, -8.0F);
		Bars4.setTextureOffset(74, 55).addBox(-13.0F, -15.0F, 15.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars4.setTextureOffset(8, 72).addBox(-15.0F, -15.0F, 15.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars4.setTextureOffset(72, 6).addBox(-11.0F, -15.0F, 15.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars4.setTextureOffset(4, 72).addBox(-9.0F, -15.0F, 15.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars4.setTextureOffset(0, 72).addBox(-7.0F, -15.0F, 15.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars4.setTextureOffset(70, 55).addBox(-5.0F, -15.0F, 15.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars4.setTextureOffset(68, 6).addBox(-3.0F, -15.0F, 15.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);

		Bars5 = new ModelRenderer(this);
		Bars5.setRotationPoint(0.0F, 24.0F, 0.0F);
		Bars5.setTextureOffset(66, 55).addBox(-8.0F, -15.0F, -4.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars5.setTextureOffset(64, 6).addBox(-8.0F, -15.0F, -6.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars5.setTextureOffset(62, 55).addBox(-8.0F, -15.0F, -2.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars5.setTextureOffset(60, 8).addBox(-8.0F, -15.0F, 0.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars5.setTextureOffset(58, 55).addBox(-8.0F, -15.0F, 2.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars5.setTextureOffset(56, 8).addBox(-8.0F, -15.0F, 4.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars5.setTextureOffset(48, 8).addBox(-8.0F, -15.0F, 6.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);

		Bars6 = new ModelRenderer(this);
		Bars6.setRotationPoint(0.0F, 24.0F, 0.0F);
		Bars6.setTextureOffset(44, 8).addBox(7.0F, -15.0F, -4.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars6.setTextureOffset(40, 8).addBox(7.0F, -15.0F, -6.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars6.setTextureOffset(36, 8).addBox(7.0F, -15.0F, -2.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars6.setTextureOffset(10, 35).addBox(7.0F, -15.0F, 0.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars6.setTextureOffset(6, 35).addBox(7.0F, -15.0F, 2.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars6.setTextureOffset(32, 8).addBox(7.0F, -15.0F, 4.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		Bars6.setTextureOffset(28, 8).addBox(7.0F, -15.0F, 6.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);

		Parachute = new ModelRenderer(this);
		Parachute.setRotationPoint(1.0F, -8.0F, -9.0F);
		Parachute.setTextureOffset(0, 76).addBox(-8.0F, -35.0F, 2.0F, 14.0F, 1.0F, 14.0F, 0.0F, false);
		Parachute.setTextureOffset(112, 117).addBox(-11.0F, -31.0F, 17.0F, 20.0F, 3.0F, 2.0F, 0.0F, false);
		Parachute.setTextureOffset(128, 72).addBox(-9.0F, -34.0F, 16.0F, 16.0F, 4.0F, 1.0F, 0.0F, false);
		Parachute.setTextureOffset(128, 61).addBox(-9.0F, -34.0F, 1.0F, 16.0F, 4.0F, 1.0F, 0.0F, false);
		Parachute.setTextureOffset(126, 138).addBox(-9.0F, -4.0F, 1.0F, 16.0F, 6.0F, 1.0F, 0.0F, false);
		Parachute.setTextureOffset(140, 0).addBox(-9.0F, -4.0F, 16.0F, 16.0F, 6.0F, 1.0F, 0.0F, false);
		Parachute.setTextureOffset(56, 106).addBox(7.0F, -31.0F, 1.0F, 2.0F, 3.0F, 16.0F, 0.0F, false);
		Parachute.setTextureOffset(108, 77).addBox(-11.0F, -31.0F, -1.0F, 20.0F, 3.0F, 2.0F, 0.0F, false);
		Parachute.setTextureOffset(20, 106).addBox(-11.0F, -31.0F, 1.0F, 2.0F, 3.0F, 16.0F, 0.0F, false);
		Parachute.setTextureOffset(60, 82).addBox(-11.0F, -6.0F, 1.0F, 2.0F, 3.0F, 16.0F, 0.0F, false);
		Parachute.setTextureOffset(108, 41).addBox(-11.0F, -6.0F, -1.0F, 20.0F, 3.0F, 2.0F, 0.0F, false);
		Parachute.setTextureOffset(108, 67).addBox(-11.0F, -6.0F, 17.0F, 20.0F, 3.0F, 2.0F, 0.0F, false);
		Parachute.setTextureOffset(0, 101).addBox(7.0F, -6.0F, 1.0F, 2.0F, 3.0F, 16.0F, 0.0F, false);
		Parachute.setTextureOffset(80, 82).addBox(-13.0F, -28.0F, -1.0F, 2.0F, 4.0F, 20.0F, 0.0F, false);
		Parachute.setTextureOffset(104, 86).addBox(-13.0F, -28.0F, 19.0F, 24.0F, 4.0F, 2.0F, 0.0F, false);
		Parachute.setTextureOffset(36, 82).addBox(9.0F, -28.0F, -1.0F, 2.0F, 4.0F, 20.0F, 0.0F, false);
		Parachute.setTextureOffset(28, 0).addBox(-13.0F, -28.0F, -3.0F, 24.0F, 4.0F, 2.0F, 0.0F, false);
		Parachute.setTextureOffset(84, 28).addBox(9.0F, -8.0F, -1.0F, 2.0F, 2.0F, 20.0F, 0.0F, false);
		Parachute.setTextureOffset(104, 92).addBox(-13.0F, -8.0F, 19.0F, 24.0F, 2.0F, 2.0F, 0.0F, false);
		Parachute.setTextureOffset(84, 55).addBox(-13.0F, -8.0F, -1.0F, 2.0F, 2.0F, 20.0F, 0.0F, false);
		Parachute.setTextureOffset(104, 96).addBox(-13.0F, -8.0F, -3.0F, 24.0F, 2.0F, 2.0F, 0.0F, false);
		Parachute.setTextureOffset(32, 48).addBox(-14.0F, -24.0F, -3.0F, 1.0F, 4.0F, 24.0F, 0.0F, false);
		Parachute.setTextureOffset(0, 91).addBox(-14.0F, -24.0F, 21.0F, 26.0F, 4.0F, 1.0F, 0.0F, false);
		Parachute.setTextureOffset(56, 0).addBox(11.0F, -24.0F, -3.0F, 1.0F, 4.0F, 24.0F, 0.0F, false);
		Parachute.setTextureOffset(0, 96).addBox(-14.0F, -24.0F, -4.0F, 26.0F, 4.0F, 1.0F, 0.0F, false);
		Parachute.setTextureOffset(82, 20).addBox(-14.0F, -11.0F, 21.0F, 26.0F, 3.0F, 1.0F, 0.0F, false);
		Parachute.setTextureOffset(58, 28).addBox(-14.0F, -11.0F, -3.0F, 1.0F, 3.0F, 24.0F, 0.0F, false);
		Parachute.setTextureOffset(104, 82).addBox(-14.0F, -11.0F, -4.0F, 26.0F, 3.0F, 1.0F, 0.0F, false);
		Parachute.setTextureOffset(58, 55).addBox(11.0F, -11.0F, -3.0F, 1.0F, 3.0F, 24.0F, 0.0F, false);
		Parachute.setTextureOffset(82, 0).addBox(-15.0F, -20.0F, -5.0F, 28.0F, 9.0F, 1.0F, 0.0F, false);
		Parachute.setTextureOffset(0, 0).addBox(-15.0F, -20.0F, -4.0F, 1.0F, 9.0F, 26.0F, 0.0F, false);
		Parachute.setTextureOffset(82, 10).addBox(-14.0F, -20.0F, 22.0F, 26.0F, 9.0F, 1.0F, 0.0F, false);
		Parachute.setTextureOffset(28, 9).addBox(12.0F, -20.0F, -4.0F, 1.0F, 9.0F, 26.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(8.0F, 32.0F, 1.0F);
		Parachute.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 1.5708F, 0.0F);
		cube_r1.setTextureOffset(16, 140).addBox(-15.0F, -36.0F, -2.0F, 14.0F, 6.0F, 1.0F, 0.0F, false);
		cube_r1.setTextureOffset(92, 139).addBox(-15.0F, -36.0F, -17.0F, 14.0F, 6.0F, 1.0F, 0.0F, false);
		cube_r1.setTextureOffset(46, 141).addBox(-15.0F, -66.0F, -17.0F, 14.0F, 4.0F, 1.0F, 0.0F, false);
		cube_r1.setTextureOffset(144, 7).addBox(-15.0F, -66.0F, -2.0F, 14.0F, 4.0F, 1.0F, 0.0F, false);

		Holders = new ModelRenderer(this);
		Holders.setRotationPoint(0.0F, 24.0F, 0.0F);
		Holders.setTextureOffset(0, 35).addBox(3.0F, -17.0F, -9.0F, 2.0F, 18.0F, 1.0F, 0.0F, false);
		Holders.setTextureOffset(20, 0).addBox(-5.0F, -17.0F, -9.0F, 2.0F, 18.0F, 1.0F, 0.0F, false);
		Holders.setTextureOffset(112, 100).addBox(-5.0F, -17.0F, -8.0F, 2.0F, 1.0F, 16.0F, 0.0F, false);
		Holders.setTextureOffset(108, 50).addBox(3.0F, -17.0F, -8.0F, 2.0F, 1.0F, 16.0F, 0.0F, false);
		Holders.setTextureOffset(14, 0).addBox(-5.0F, -17.0F, 8.0F, 2.0F, 18.0F, 1.0F, 0.0F, false);
		Holders.setTextureOffset(8, 0).addBox(3.0F, -17.0F, 8.0F, 2.0F, 18.0F, 1.0F, 0.0F, false);
		Holders.setTextureOffset(108, 24).addBox(-5.0F, 0.0F, -8.0F, 2.0F, 1.0F, 16.0F, 0.0F, false);
		Holders.setTextureOffset(92, 106).addBox(3.0F, 0.0F, -8.0F, 2.0F, 1.0F, 16.0F, 0.0F, false);

		BottomBorder = new ModelRenderer(this);
		BottomBorder.setRotationPoint(8.0F, 24.0F, -8.0F);
		BottomBorder.setTextureOffset(110, 137).addBox(-16.0F, -1.0F, 1.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		BottomBorder.setTextureOffset(42, 44).addBox(-16.0F, -1.0F, 0.0F, 16.0F, 1.0F, 1.0F, 0.0F, false);
		BottomBorder.setTextureOffset(28, 6).addBox(-16.0F, -1.0F, 15.0F, 16.0F, 1.0F, 1.0F, 0.0F, false);
		BottomBorder.setTextureOffset(0, 135).addBox(-1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);

		SidesBorder = new ModelRenderer(this);
		SidesBorder.setRotationPoint(0.0F, 24.0F, 0.0F);
		SidesBorder.setTextureOffset(88, 28).addBox(-8.0F, -15.0F, -8.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		SidesBorder.setTextureOffset(84, 82).addBox(-8.0F, -15.0F, 7.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		SidesBorder.setTextureOffset(84, 55).addBox(7.0F, -15.0F, -8.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);
		SidesBorder.setTextureOffset(84, 28).addBox(7.0F, -15.0F, 7.0F, 1.0F, 14.0F, 1.0F, 0.0F, false);

		Parachuterope = new ModelRenderer(this);
		Parachuterope.setRotationPoint(-4.0F, 8.0F, 8.0F);
		

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(8.0F, 0.0F, -9.0F);
		Parachuterope.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, 0.2007F);
		cube_r2.setTextureOffset(0, 0).addBox(-1.0F, -20.0F, 0.0F, 1.0F, 21.0F, 1.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(1.0F, 0.0F, -9.0F);
		Parachuterope.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 0.0F, -0.2007F);
		cube_r3.setTextureOffset(4, 0).addBox(-1.0F, -20.0F, 0.0F, 1.0F, 21.0F, 1.0F, 0.0F, false);

		Blanket = new ModelRenderer(this);
		Blanket.setRotationPoint(0.0F, 24.0F, 0.0F);
		Blanket.setTextureOffset(0, 151).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 0.0F, 16.0F, 0.0F, false);
		Blanket.setTextureOffset(33, 152).addBox(8.0F, -16.0F, -8.0F, 0.0F, 9.0F, 16.0F, 0.0F, false);
		Blanket.setTextureOffset(0, 179).addBox(-8.0F, -16.0F, -8.0F, 0.0F, 9.0F, 16.0F, 0.0F, false);
		Blanket.setTextureOffset(0, 239).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 9.0F, 0.0F, 0.0F, false);
		Blanket.setTextureOffset(37, 238).addBox(-8.0F, -16.0F, 8.0F, 16.0F, 9.0F, 0.0F, 0.0F, false);

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.setTextureOffset(0, 44).addBox(-7.0F, -15.0F, -7.0F, 14.0F, 14.0F, 14.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(AirdropEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Bars.render(matrixStack, buffer, packedLight, packedOverlay);
		Bars2.render(matrixStack, buffer, packedLight, packedOverlay);
		Bars3.render(matrixStack, buffer, packedLight, packedOverlay);
		Bars4.render(matrixStack, buffer, packedLight, packedOverlay);
		Bars5.render(matrixStack, buffer, packedLight, packedOverlay);
		Bars6.render(matrixStack, buffer, packedLight, packedOverlay);
		Parachute.render(matrixStack, buffer, packedLight, packedOverlay);
		Holders.render(matrixStack, buffer, packedLight, packedOverlay);
		BottomBorder.render(matrixStack, buffer, packedLight, packedOverlay);
		SidesBorder.render(matrixStack, buffer, packedLight, packedOverlay);
		Parachuterope.render(matrixStack, buffer, packedLight, packedOverlay);
		Blanket.render(matrixStack, buffer, packedLight, packedOverlay);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}