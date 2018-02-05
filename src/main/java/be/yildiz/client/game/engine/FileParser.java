/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildiz.client.game.engine;

import be.yildiz.client.game.engine.parser.ContainerDefinition;
import be.yildiz.client.game.engine.parser.FontParser;
import be.yildiz.client.game.engine.parser.GuiParser;
import be.yildiz.client.game.engine.parser.MaterialParser;
import be.yildiz.client.game.engine.parser.MusicDefinition;
import be.yildiz.client.game.engine.parser.MusicParser;
import be.yildiz.client.game.engine.parser.ParserException;
import be.yildiz.client.game.engine.parser.ParserFactory;
import be.yildiz.client.game.engine.parser.ParserFactory.ParserType;
import be.yildiz.client.game.engine.parser.PlayListDefinition;
import be.yildiz.client.game.engine.parser.SimpleMaterialDefinition;
import be.yildiz.module.graphic.Font;
import be.yildiz.module.graphic.GraphicEngine;
import be.yildiz.module.graphic.Material;
import be.yildiz.module.graphic.MaterialManager;
import be.yildiz.module.graphic.TextureUnit;
import be.yildiz.module.graphic.gui.ButtonMaterial;
import be.yildiz.module.graphic.gui.GuiContainer;
import be.yildiz.module.sound.AudioEngine;
import be.yildiz.module.sound.Music;
import be.yildiz.module.sound.Playlist;
import be.yildizgames.common.file.ResourcePath;
import be.yildizgames.common.file.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Helper class to parse script file to create widgets, views, materials, playlist...
 *
 * @author Grégory Van den Borre
 */
public final class FileParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileParser.class);

    /**
     * Create the parser used to read the definition scripts.
     */
    private final ParserFactory parserFactory = new ParserFactory(ParserType.XML);

    /**
     * Create the materials.
     */
    private final MaterialManager materialManager;

    /**
     * Create the lights,...
     */
    private final GraphicEngine graphicEngine;

    /**
     * Create the music playlist.
     */
    private final AudioEngine soundEngine;

    FileParser(MaterialManager materialManager, GraphicEngine graphicEngine, AudioEngine soundEngine) {
        this.materialManager = materialManager;
        this.graphicEngine = graphicEngine;
        this.soundEngine = soundEngine;
    }

    /**
     * Set the path to get graphic resources and parse the scripts in this folder.
     *
     * @param resource Resources for this resource group.
     */
    void addResourcePath(ResourcePath resource) {
        final File folder = new File(resource.getPath());
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException(folder.getAbsolutePath() + " is not a valid resource path.");
        }
        final List<File> files = ResourceUtil.listFile(folder);
        MusicParser musicParser = this.parserFactory.createMusicParser();
        MaterialParser materialParser = this.parserFactory.createMaterialParser(this.graphicEngine.getScreenSize());
        FontParser fontParser = this.parserFactory.createFontParser();
        GuiParser guiParser = this.parserFactory.createGuiParser(this.graphicEngine.getScreenSize());
        files.stream().filter(s -> s.getName().endsWith(".mat")).forEach(s -> {
            LOGGER.info("Parsing material script " + s);
            final List<SimpleMaterialDefinition> matDef = materialParser.parse(s);
            for (final SimpleMaterialDefinition def : matDef) {
                final Material m = this.materialManager.loadSimpleTexture(def.getName(), def.getPath(), def.getTransparency());
                if (!def.getPath2().isEmpty()) {
                    TextureUnit unit = m.getTechnique(0).createTexturePass().getUnit(0);
                    unit.setTexture(def.getPath2());
                    m.getTechnique(0).getPass(1).setTransparency(def.getTransparency());
                }
                if (!def.getGlowFile().isEmpty()) {
                    m.addGlowTechnique(def.getGlowFile());
                }
                if (!def.isAffectedByLight()) {
                    m.disableLight();
                }
                m.setBlendMode(def.getBlend());
                m.setSceneBlend(def.getSceneBlend1(), def.getSceneBlend2());
            }
        });
        files.stream().filter(s -> s.getName().endsWith(".pll")).forEach(s -> {
            LOGGER.info("Parsing playlist script " + s);
            final List<PlayListDefinition> playListDef = musicParser.parse(s);
            for (final PlayListDefinition def : playListDef) {
                final Playlist p = this.soundEngine.createPlaylist(def.getName());
                for (final MusicDefinition musicDef : def.getMusicList()) {
                    final Music m = new Music(musicDef.getFile(), musicDef.getName());
                    p.addMusic(m);
                }
            }
        });
        files
                .stream()
                .filter(s -> s.getName().endsWith(".fnt"))
                .map(fontParser::parse)
                .forEach(l -> l.forEach(
                        def ->
                                this.graphicEngine.createFont(def.getName(), def.getPath(), def.getSize()).load()));

        files.stream().filter(s -> s.getName().endsWith(".vew")).forEach(s -> {
            LOGGER.info("Parsing view script " + s);
            try {
                guiParser.parse(s).forEach(this::buildView);
            } catch (final ParserException pe) {
                LOGGER.error("Error parsing", pe);
            }
        });
    }

    /**
     * Build a view from a given definition.
     *
     * @param def Data to build the view.
     */
    private void buildView(final ContainerDefinition def) {
        final GuiContainer container = graphicEngine
                .getGuiBuilder()
                .container()
                .withName(def.getName())
                .withBackground(def.getMaterial())
                .withCoordinates(def.getCoordinates())
                .build();

        def.getImageList().forEach(id -> graphicEngine
                .getGuiBuilder()
                .image()
                .withName(id.getName())
                .withBackground(id.getMaterial())
                .withCoordinates(id.getCoordinates())
                .build(container));

        def.getTextLineList().forEach(td -> graphicEngine
                .getGuiBuilder()
                .textLine()
                .withName(td.getName())
                .withCoordinates(td.getCoordinates())
                .withFont(Font.get(td.getFont()))
                .build(container));

        def.getButtonList().forEach(bd -> graphicEngine
                .getGuiBuilder()
                .button()
                .withName(bd.getName())
                .withCoordinates(bd.getCoordinates())
                .withButtonMaterial(new ButtonMaterial(
                        Material.get(bd.getMaterial()),
                        Material.get(bd.getMaterialHighlight()),
                        Font.get(bd.getFont())
                ))
                .build(container));

        def.getInputBoxList().forEach(ibd ->
                graphicEngine
                        .getGuiBuilder().buildInputBox(
                    ibd.getName(),
                    ibd.getCoordinates(),
                    ibd.getFont(),
                    ibd.getMaterial(),
                    ibd.getMaterialHighlight(),
                    ibd.getMaterialCursor(),
                    container));

        def.getTextAreaList().forEach(tad -> graphicEngine
                .getGuiBuilder()
                .textArea()
                .withName(tad.getName())
                .withCoordinates(tad.getCoordinates())
                .withFont(Font.get(tad.getFont()))
                .withBackground(Material.get(tad.getMaterial()))
                .build(container));
    }

}
