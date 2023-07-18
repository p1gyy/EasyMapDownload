package com.piggygaming.ezmapdl;

import com.mojang.logging.LogUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.slf4j.Logger;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class WorldDetailsWidget {

    private int x;
    private int y;

    private final Logger LOGGER = LogUtils.getLogger();

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d/M/yy");
    private final DrawContext CONTEXT;
    private final String WORLD_NAME;
    private final String FILE_NAME;
    private final String VERSION;
    private final boolean CHEATS_ENABLED;
    private final String GAMEMODE;

    public WorldDetailsWidget(int x, int y, DrawContext context, TextRenderer renderer, File levelDat) throws IOException {
        this.CONTEXT = context;
        this.WORLD_NAME = levelDat.getName();
        this.FILE_NAME = "";
        this.VERSION = "1.20.1";
        this.CHEATS_ENABLED = false;
        this.GAMEMODE = "Survival";
        this.x = x;
        this.y = y;

        context.drawCenteredTextWithShadow(renderer, Text.literal(WORLD_NAME), x, y, 16777215);
    }

}
