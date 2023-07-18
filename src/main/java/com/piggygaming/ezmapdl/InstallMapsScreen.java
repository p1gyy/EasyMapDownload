package com.piggygaming.ezmapdl;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.io.File;
import java.io.IOException;

import static com.piggygaming.ezmapdl.FileUtils.*;

@Environment(EnvType.CLIENT)
public class InstallMapsScreen extends Screen {

    private final Screen parent;
    private final MinecraftClient client;
    private final File savesDirectory;
    private File lastModified;

    public InstallMapsScreen(Screen parent) throws IOException {
        super(Text.literal("Is this file correct?"));
        this.parent = parent;
        this.client = MinecraftClient.getInstance();
        this.savesDirectory = new File(this.client.runDirectory.getPath() + "\\saves");
    }

    private void errorScreen(String errorMSG) {
        EasyMapDownload.LOGGER.warn(errorMSG);
        this.client.setScreen(new ErrorScreen(errorMSG, this.parent));
    }
    private void errorScreen(Exception exception) {
        exception.printStackTrace();
        this.client.setScreen(new ErrorScreen(exception.getStackTrace().toString(), this.parent));
    }

    @Override
    protected void init() {

        try {
            this.lastModified = getLastModified(System.getProperty("user.home") + "\\Downloads");
        } catch (Exception e) {
            errorScreen(e);
        }

        if (this.lastModified == null) {
            errorScreen("Cannot find a valid zip file");
        }

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Confirm"), (button) -> {
            File newFile = new File(savesDirectory.getPath() + "\\" + lastModified.getName());
            if (lastModified.renameTo(newFile)) {
                try {
                    if (fileNotInRootDir(newFile, "level.dat")) {
                        unzipFile(newFile.getPath(), savesDirectory);
                    } else {
                        File dir = new File(savesDirectory.getPath() + "\\" + newFile.getName().replaceFirst("[.][^.]+$", "")); dir.mkdirs();
                        unzipFile(newFile.getPath(), dir);
                    }
                    newFile.delete();
                } catch (Exception e) {
                    errorScreen(e);
                }
            }
            this.client.setScreen(new SelectWorldScreen(new TitleScreen()));
        }).dimensions(this.width / 2 - 105, this.height /2 + 40, 100, 20).build());
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, (button) -> {
            this.client.setScreen(this.parent);
        }).dimensions(this.width / 2 + 5, this.height /2 + 40, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 40, 16777215);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(lastModified.getName()), this.width / 2, this.height / 2, 16777215);

    }

}
