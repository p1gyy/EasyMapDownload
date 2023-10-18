package com.piggygaming.ezmapdl;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class LoadingScreen extends Screen {

    protected final Screen parent;
    public MinecraftClient client;

    protected LoadingScreen(Screen parent) {
        super(Text.literal("Installing map..."));
        this.parent = parent;
        this.client = MinecraftClient.getInstance();
    }

    @Override
    protected void init() {

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2, 16777215);
    }

}
