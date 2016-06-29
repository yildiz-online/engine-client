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

package be.yildiz.client.game.helper.view;

import be.yildiz.common.client.gui.listener.ArrowKey;
import be.yildiz.common.client.gui.listener.KeyboardListener;
import be.yildiz.common.collections.Lists;
import be.yildiz.common.log.Logger;
import be.yildiz.common.resource.ResourceUtil;
import be.yildiz.common.util.StringUtil;
import be.yildiz.module.graphic.Font;
import be.yildiz.module.graphic.Material;
import be.yildiz.module.graphic.gui.*;
import be.yildiz.module.script.ScriptException;
import be.yildiz.module.script.ScriptInterpreter;
import be.yildiz.module.script.ScriptInterpreterFactory;
import be.yildiz.module.script.ScriptInterpreterFactory.ScriptLanguage;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Console to input script commands and to display the result.
 *
 * @author Grégory Van den Borre
 */
public final class ScriptConsole extends Window {

    /**
     * Command to save a script to a file.
     */
    private static final String SAVE = "save";

    /**
     * Command to load a script from a file.
     */
    private static final String LOAD = "load";

    /**
     * To start a line.
     */
    private static final String LINE = ">>> ";

    /**
     * Widget for the user to enter commands.
     */
    private final InputBox input;

    /**
     * Display entered commands and results.
     */
    private final TextArea console;

    /**
     * Script engine.
     */
    private final ScriptInterpreter interpreter;

    /**
     * Keep a record of all valid command entered, use it to save the script.
     */
    private final List<String> validCommandHistory = Lists.newList();

    /**
     * Build a new script console.
     *
     * @param language Script language to use.
     * @param builder  Builder to create the visual console.
     * @param data     Visual data to build the console.
     */
    public ScriptConsole(final ScriptLanguage language, final GuiBuilder builder, final ScriptConsoleData data) {
        super(data.container, builder, new Zorder(645));
        this.interpreter = ScriptInterpreterFactory.getInstance().getInterpreter(language);
        this.input = data.input;
        this.console = data.screen;
        this.interpreter.setOutput(new TextAreaWriter(this.console));
        this.input.addKeyboardListener(new ConsoleKeyboardListener());
    }

    /**
     * Set a font to use in the view.
     *
     * @param font Font to use.
     */
    public void setFont(final Font font) {
        this.input.setFont(font);
        this.console.setFont(font);
    }

    /**
     * Set the background material for the script input box.
     *
     * @param material Material to set.
     */
    public void setInputBackground(final Material material) {
        this.input.setMaterial(material);
    }

    /**
     * Set the background material for the text area.
     *
     * @param material Material to set.
     */
    public void setConsoleBackground(final Material material) {
        this.console.setMaterial(material);
    }

    /**
     * Does nothing.
     *
     * @param show Unused.
     */
    @Override
    protected void setVisibleImpl(final boolean show) {
    }

    /**
     * Execute command like clear console, help,...
     *
     * @param command Entered command.
     * @return <code>true</code> if the command is recognized as a special command, <code>false</code> otherwise.
     * @throws ScriptException If an exception occurs while parsing or executing the command.
     * @Requires command != null.
     */
    private boolean executeSpecialCommand(final String command) throws ScriptException {
        switch (command) {
            case "clear":
                this.console.deleteText();
                return true;
            default:
                return this.runCommand(command);
        }
    }

    /**
     * Run the different possible commands.
     *
     * @param command Command to run.
     * @return <code>true</code> if the command was run successfully.
     * @throws ScriptException If the interpreter failed to run the script.
     */
    private boolean runCommand(final String command) throws ScriptException {
        String methods = ".methods";
        if (command.startsWith(SAVE)) {
            return saveCommand(command);
        } else if (command.startsWith(LOAD)) {
            return loadCommand(command);
        } else if (command.contains(methods)) {
            String object = command.replace(methods, "");
            this.interpreter.runCommand("java_class(" + object + ")");
            return true;
        }
        return false;
    }

    /**
     * Load the script from a file.
     *
     * @param command The command is expected to be 'LOAD fileName'.
     * @return true.
     * @throws ScriptException If the interpreter could not play the script.
     * @Requires command != null.
     */
    private boolean loadCommand(final String command) throws ScriptException {
        String fileName = command.replace(LOAD, "");
        fileName = fileName.trim();
        this.interpreter.runScript("script/" + fileName + "." + interpreter.getFileExtension());
        this.console.addLine(LINE + fileName + " loaded.");
        return true;
    }

    /**
     * Save the script in a file.
     *
     * @param command The command is expected to be 'SAVE fileName'.
     * @return true.
     * @Requires command != null
     */
    private boolean saveCommand(final String command) {
        String fileName = command.replace(SAVE, "");
        fileName = fileName.trim();
        File file = new File("script/" + fileName + "." + this.interpreter.getFileExtension());
        try {
            file.createNewFile();
        } catch (IOException e) {
            Logger.error(e);
        }
        try (Writer writer = ResourceUtil.getFileWriter(file)) {
            writer.write(this.interpreter.getFileHeader());
            for (String line : this.validCommandHistory) {
                writer.write(line + "\n");
            }
            this.console.addLine(LINE + fileName + " saved.");
        } catch (IOException e) {
            Logger.error(e);

        }
        return true;
    }

    @Override
    protected void setActiveImpl(final boolean active) {
    }

    /**
     * Data to display a script console.
     *
     * @author Van den Borre Grégory
     */
    public static class ScriptConsoleData {

        /**
         * Container for the console.
         */
        private final GuiContainer container;

        /**
         * Screen to display the output.
         */
        private final TextArea screen;

        /**
         * Input to enter console commands.
         */
        private final InputBox input;

        /**
         * Build the data to display a console.
         *
         * @param container Container for the console.
         * @param screen    Screen to display the output.
         * @param input     Input to enter console commands.
         */
        public ScriptConsoleData(final GuiContainer container, final TextArea screen, final InputBox input) {
            super();
            this.container = container;
            this.screen = screen;
            this.input = input;
        }
    }

    private final class ConsoleKeyboardListener implements KeyboardListener {
        /**
         * History of all commands entered.
         */
        private final List<String> history = Lists.newList();

        /**
         * History current index.
         */
        private int current;

        /**
         * Send the written line to the script engine.
         */
        @Override
        public void enterKeyPressed() {
            final String command = ScriptConsole.this.input.getText();
            this.history.add(command);
            this.current = this.history.size() - 1;
            try {
                if (!ScriptConsole.this.executeSpecialCommand(command)) {
                    ScriptConsole.this.console.addLine(command);
                    ScriptConsole.this.console.addLine(LINE + StringUtil.toString(ScriptConsole.this.interpreter.runCommand(command)));
                    ScriptConsole.this.validCommandHistory.add(command);
                }
            } catch (ScriptException e) {
                ScriptConsole.this.console.addLine("ERROR:" + e.getMessage());
            }
            ScriptConsole.this.input.deleteText();
        }

        @Override
        public void deleteKeyPressed() {
            ScriptConsole.this.input.removeChar();
        }

        @Override
        public void arrowKeyPressed(final ArrowKey arrow) {
            if (arrow == ArrowKey.UP) {
                ScriptConsole.this.input.deleteText();
                ScriptConsole.this.input.setText(this.history.get(this.current));
                if (this.current > 0) {
                    this.current--;
                }
            }
            if (arrow == ArrowKey.DOWN) {
                ScriptConsole.this.input.deleteText();
                ScriptConsole.this.input.setText(this.history.get(this.current));
                if (this.current < this.history.size() - 1) {
                    this.current++;
                }
            }
        }

        @Override
        public void keyPressed(final char key) {
            //Not used, input box does the job.
        }

    }
}
