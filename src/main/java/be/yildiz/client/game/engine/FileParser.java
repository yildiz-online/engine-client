//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package be.yildiz.client.game.engine;

import be.yildiz.client.game.engine.gui.TranslatedGuiBuilder;
import be.yildiz.client.game.engine.parser.*;
import be.yildiz.client.game.engine.parser.ParserFactory.ParserType;
import be.yildiz.common.Position;
import be.yildiz.common.log.Logger;
import be.yildiz.module.graphic.*;
import be.yildiz.module.graphic.gui.ButtonMaterial;
import be.yildiz.module.graphic.gui.GuiContainer;
import be.yildiz.module.sound.Music;
import be.yildiz.module.sound.Playlist;
import be.yildiz.module.sound.SoundEngine;
import lombok.AllArgsConstructor;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * Helper class to parse script file to create widgets, views, materials, playlist...
 *
 * @author Grégory Van den Borre
 */
@AllArgsConstructor
public final class FileParser {

    /**
     * Create the parser used to read the definition scripts.
     */
    private final ParserFactory parserFactory = new ParserFactory(ParserType.XML);

    /**
     * Create the widget.
     */
    private final TranslatedGuiBuilder guiManager;

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
    private final SoundEngine soundEngine;

    /**
     * Set the path to get graphic resources and parse the scripts in this folder.
     *
     * @param name Name for this resource group.
     * @param path Path used for the graphic resources.
     */
    void addResourcePath(final String name, final String path) {
        final File folder = new File(path);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new InvalidParameterException(folder.getAbsolutePath() + " is not a valid resource path.");
        }
        final File[] files = folder.listFiles();
        MusicParser musicParser = this.parserFactory.createMusicParser();
        MaterialParser materialParser = this.parserFactory.createMaterialParser(this.graphicEngine.getScreenSize());
        FontParser fontParser = this.parserFactory.createFontParser();
        GuiParser guiParser = this.parserFactory.createGuiParser(this.graphicEngine.getScreenSize());
        for (final File s : files) {
            if (s.getName().endsWith(".mat")) {
                Logger.info("Parsing material script " + s);
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
            }
        }
        for (final File s : files) {
            if (s.getName().endsWith(".pll")) {
                Logger.info("Parsing playlist script " + s);
                final List<PlayListDefinition> playListDef = musicParser.parse(s);
                for (final PlayListDefinition def : playListDef) {
                    final Playlist p = this.soundEngine.createPlaylist(def.getName());
                    // FIXME stream
                    for (final MusicDefinition musicDef : def.getMusicList()) {
                        final Music m = new Music(musicDef.getFile(), musicDef.getName());
                        p.addMusic(m);
                    }
                }
            }
        }
        for (final File s : files) {
            if (s.getName().endsWith(".fnt")) {
                Logger.info("Parsing font script " + s);
                final List<FontDefinition> fontDef = fontParser.parse(s);
                for (final FontDefinition def : fontDef) {
                    this.graphicEngine.createFont(def.getName(), def.getPath(), def.getSize()).load();
                }
            }
        }
        for (final File s : files) {
            if (s.getName().endsWith(".vew")) {
                Logger.info("Parsing view script " + s);
                try {
                    guiParser.parse(s).forEach(this::buildView);
                } catch (final ParserException pe) {
                    Logger.error(pe);
                }
            }
        }
    }

    /**
     * Build a view from a given definition.
     *
     * @param def Data to build the view.
     */
    private void buildView(final ContainerDefinition def) {
        final GuiContainer container = this.guiManager.buildOverlayContainer(def.getName(), def.getMaterial(), def.getCoordinates());
        // FIXME use stream
        for (final ImageDefinition id : def.getImageList()) {
            this.guiManager.buildImage(id.getName(), id.getCoordinates(), id.getMaterial(), container);
        }

        for (final TextLineDefinition td : def.getTextLineList()) {
            this.guiManager.buildTextLine(td.getName(), td.getCoordinates(), Font.get(td.getFont()), container);
        }

        for (final ButtonDefinition bd : def.getButtonList()) {
            this.guiManager.buildButton(bd.getName(), bd.getCoordinates(), new ButtonMaterial(Material.get(bd.getMaterial()), Material.get(bd.getMaterialHighlight()), Font.get(bd.getFont())), container);
        }

        for (final InputBoxDefinition ibd : def.getInputBoxList()) {
            this.guiManager.buildInputBox(ibd.getName(), ibd.getCoordinates(), ibd.getFont(), ibd.getMaterial(), ibd.getMaterialHighlight(), ibd.getMaterialCursor(), container);
        }

        for (final TextAreaDefinition tad : def.getTextAreaList()) {
            this.guiManager.buildTextArea(tad.getName(), new Position(tad.getCoordinates()), Font.get(tad.getFont()), Material.get(tad.getMaterial()), 0, container);
        }
    }

}
