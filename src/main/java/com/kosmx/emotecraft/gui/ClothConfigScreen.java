package com.kosmx.emotecraft.gui;

import com.kosmx.emotecraft.Main;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;

public class ClothConfigScreen {
    public static Screen getConfigScreen(Screen parent){
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(new TranslatableText("emotecraft.otherconfig"));
        builder.setSavingRunnable(()->{
            if(parent instanceof EmoteMenu){
                ((EmoteMenu) parent).save = true;    //It's parent is EmoteMenu, when you leave that and save == true -> it'll save
            }
        });
        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("emotecraft.otherconfig.category.general")); //configs what most people will use
        ConfigCategory expert = builder.getOrCreateCategory(new TranslatableText("emotecraft.otherconfig.category.expert")); //expert configs
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("emotecraft.otherconfig.dark"), Main.config.dark).setDefaultValue(false).setSaveConsumer(newValue->Main.config.dark = newValue).build());
        general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("emotecraft.otherconfig.perspective"), Main.config.enablePerspective).setDefaultValue(true).setSaveConsumer(newValue -> Main.config.enablePerspective = newValue).build());
        general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("emotecraft.otherconfig.loadbuiltin"), Main.config.loadBuiltinEmotes).setDefaultValue(true).setTooltip(new TranslatableText("emotecraft.otherconfig.loadbuiltin.tooltip")).setSaveConsumer(newValue->Main.config.loadBuiltinEmotes = newValue).build());
        general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("emotecraft.otherconfig.showicon"), Main.config.showIcons).setDefaultValue(true).setTooltip(new TranslatableText("emotecraft.otherconfig.showicon.tooltip")).setSaveConsumer(newValue->Main.config.showIcons = newValue).build());
        general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("emotecraft.otherconfig.quark"), Main.config.enableQuark).setDefaultValue(false).setSaveConsumer(newValue->{
            if(newValue && parent instanceof EmoteMenu && ! Main.config.enableQuark){
                ((EmoteMenu) parent).warn = true;
            }
            Main.config.enableQuark = newValue;
        }).build());

        //Expert options

        expert.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("emotecraft.otherconfig.debug"), Main.config.showDebug).setDefaultValue(false).setSaveConsumer(newValue->Main.config.showDebug = newValue).build());
        expert.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("emotecraft.otherconfig.playersafety"), Main.config.enablePlayerSafety).setDefaultValue(true).setTooltip(new TranslatableText("emotecraft.otherconfig.playersafety.tooltip")).setSaveConsumer(newValue->Main.config.enablePlayerSafety = newValue).build());
        expert.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("emotecraft.otherconfig.validate"), Main.config.validateEmote).setTooltip(new TranslatableText("emotecraft.otherconfig.validate.tooltip")).setDefaultValue(false).setSaveConsumer(newValue->Main.config.validateEmote = newValue).build());
        expert.addEntry(entryBuilder.startFloatField(new TranslatableText("emotecraft.otherconfig.validationThreshold"), Main.config.validThreshold).setDefaultValue(8f).setSaveConsumer(newValue->Main.config.validThreshold = newValue).setTooltip(new TranslatableText("emotecraft.otherconfig.validationThreshold.tooltip")).build());
        expert.addEntry(entryBuilder.startFloatField(new TranslatableText("emotecraft.otherconfig.stopthreshold"), Main.config.stopThreshold).setDefaultValue(0.04f).setTooltip(new TranslatableText("emotecraft.otherconfig.stopthreshold.tooltip")).setSaveConsumer(newValue->Main.config.stopThreshold = newValue).build());
        expert.addEntry(entryBuilder.startIntSlider(new TranslatableText("emotecraft.otherconfig.yratio"), (int) (Main.config.yRatio * 100), 0, 100).setDefaultValue(75).setTooltip(new TranslatableText("emotecraft.otherconfig.yratio.tooltip")).setSaveConsumer(newValue -> Main.config.yRatio = newValue / 100f).build());
        if(FabricLoader.getInstance().isModLoaded("perspectivemod")){
            expert.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("emotecraft.otherconfig.prespective_redux"), Main.config.perspectiveReduxIntegration).setDefaultValue(true).setSaveConsumer(newValue->Main.config.perspectiveReduxIntegration = newValue).build());
        }
        return builder.build();
    }
}
