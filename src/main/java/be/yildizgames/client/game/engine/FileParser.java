/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2018 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE  SOFTWARE.
 */

package be.yildizgames.client.game.engine;

import be.yildizgames.client.game.engine.parser.*;
import be.yildizgames.common.file.ResourcePath;
import be.yildizgames.common.logging.LogFactory;
import be.yildizgames.module.audio.AudioEngine;
import be.yildizgames.module.audio.Music;
import be.yildizgames.module.audio.Playlist;
import be.yildizgames.module.graphic.Font;
import be.yildizgames.module.graphic.GraphicEngine;
import be.yildizgames.module.graphic.gui.button.ButtonMaterial;
import be.yildizgames.module.graphic.gui.container.Container;
import be.yildizgames.module.graphic.material.Material;
import be.yildizgames.module.graphic.material.TextureUnit;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Helper class to parse script file to create widgets, views, materials, playlist...
 *
 * @author Grégory Van den Borre
 */
public final class FileParser {

    private static final Logger LOGGER = LogFactory.getInstance().getLogger(FileParser.class);

    /**
     * Create the parser used to read the definition scripts.
     */
    private final ParserFactory parserFactory = new ParserFactory(ParserFactory.ParserType.XML);

    /**
     * Create the lights,...
     */
    private final GraphicEngine graphicEngine;

    /**
     * Create the music playlist.
     */
    private final AudioEngine soundEngine;

    FileParser(GraphicEngine graphicEngine, AudioEngine soundEngine) {
        this.graphicEngine = graphicEngine;
        this.soundEngine = soundEngine;
    }

    /**
     * Set the path to get graphic resources and parse the scripts in this folder.
     *
     * @param resource Resources for this resource group.
     */
    void addResourcePath(ResourcePath resource) throws IOException {
        final Path folder = Paths.get(resource.getPath());
        if (!Files.exists(folder) || !Files.isDirectory(folder)) {
            throw new IllegalArgumentException(folder.toAbsolutePath().toString() + " is not a valid resource path.");
        }
        MusicParser musicParser = this.parserFactory.createMusicParser();
        MaterialParser materialParser = this.parserFactory.createMaterialParser(this.graphicEngine.getScreenSize());
        FontParser fontParser = this.parserFactory.createFontParser();
        GuiParser guiParser = this.parserFactory.createGuiParser(this.graphicEngine.getScreenSize());
        try(Stream<Path> files = Files.walk(folder)) {
            files.filter(s -> s.toString().endsWith(".mat")).forEach(s -> {
                LOGGER.info("Parsing material script {}", s);
                final List<SimpleMaterialDefinition> matDef = materialParser.parse(s);
                for (final SimpleMaterialDefinition def : matDef) {
                    final Material m = this.graphicEngine.getMaterialManager().loadSimpleTexture(def.getName(), def.getPath(), def.getTransparency());
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
        }
        try(Stream<Path> files = Files.walk(folder)) {
            files.filter(s -> s.toString().endsWith(".pll")).forEach(s -> {
                LOGGER.info("Parsing playlist script {}", s);
                final List<PlayListDefinition> playListDef = musicParser.parse(s);
                for (final PlayListDefinition def : playListDef) {
                    final Playlist p = this.soundEngine.createPlaylist(def.getName());
                    for (final MusicDefinition musicDef : def.getMusicList()) {
                        final Music m = Music.withName(musicDef.getFile(), musicDef.getName());
                        p.addMusic(m);
                    }
                }
            });
        }
        try(Stream<Path> files = Files.walk(folder)) {
            files
                    .filter(s -> s.toString().endsWith(".fnt"))
                    .map(fontParser::parse)
                    .forEach(l -> l.forEach(
                            def ->
                                    this.graphicEngine.createFont(def.getName(), def.getPath(), def.getSize()).load()));
        }

        try(Stream<Path> files = Files.walk(folder)) {
            files.filter(s -> s.toString().endsWith(".vew")).forEach(s -> {
                LOGGER.info("Parsing view script {}", s);
                try {
                    guiParser.parse(s).forEach(this::buildView);
                } catch (final ParserException pe) {
                    LOGGER.error("Error parsing", pe);
                }
            });
        }
    }

    /**
     * Build a view from a given definition.
     *
     * @param def Data to build the view.
     */
    private void buildView(final ContainerDefinition def) {
        final Container container = graphicEngine
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
                        .getGuiBuilder()
                        .inputBox()
                        .withName(ibd.getName())
                        .withCoordinates(ibd.getCoordinates())
                        .withFont(ibd.getFont())
                        .withCaptionFont(ibd.getFont())
                        .withBackground(ibd.getMaterial())
                        .withBackgroundHighlight(ibd.getMaterialHighlight())
                        .withCursor(ibd.getMaterialCursor())
                        .build(container));

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
