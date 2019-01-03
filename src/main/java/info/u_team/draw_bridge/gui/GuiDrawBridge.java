package info.u_team.draw_bridge.gui;

import info.u_team.draw_bridge.DrawBridgeConstants;
import info.u_team.draw_bridge.container.ContainerDrawBridge;
import info.u_team.draw_bridge.tileentity.TileEntityDrawBridge;
import info.u_team.u_team_core.gui.UGuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiDrawBridge extends UGuiContainer {
	
	public static final ResourceLocation BACKGROUND = new ResourceLocation(DrawBridgeConstants.MODID, "textures/gui/drawbridge.png");
	
	private final TileEntityDrawBridge drawbridge;
	
	private GuiSlider speedSlider;
	
	private int fixValueTry;
	
	public GuiDrawBridge(TileEntityDrawBridge tileentity, EntityPlayer entityplayer) {
		super(new ContainerDrawBridge(tileentity, entityplayer), BACKGROUND);
		drawbridge = tileentity;
		ySize = 180;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		fixValueTry = 0;
		speedSlider = new GuiSlider(0, guiLeft + 7, guiTop + 60, 90, 20, I18n.format(DrawBridgeConstants.MODID + ":container.drawbridge.speed"), I18n.format(DrawBridgeConstants.MODID + ":container.drawbridge.ticks"), 0, 100, drawbridge.getSpeed(), false, true, (slider) -> {
			drawbridge.setSpeed(slider.getValueInt());
			drawbridge.syncClientToServer(drawbridge.getPos());
		});
		buttonList.add(speedSlider);
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString(I18n.format(DrawBridgeConstants.MODID + ":container.drawbridge"), 8, 6, 4210752);
		fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
		if (fixValueTry < 10 && speedSlider.getValueInt() == 0) {
			speedSlider.setValue(drawbridge.getSpeed());
			if (speedSlider.getValueInt() > 0) {
				speedSlider.updateSlider();
			}
			fixValueTry++;
		}
	}
}