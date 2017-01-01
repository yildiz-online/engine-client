/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2017 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

package be.yildiz.client.game.engine.parser;

import be.yildiz.common.Coordinates;
import be.yildiz.common.Size;
import be.yildiz.common.collections.Maps;
import lombok.Getter;

import java.util.Map;

/**
 * Base definition for the GUI definition, contains the coordinates data and the
 * widget name.
 *
 * @author Grégory Van den Borre
 */
class GuiCommonDefinition {

    /**
     * Keep a record of all GUI definition.
     */
    private static final Map<String, GuiCommonDefinition> DEFINITION_LIST = Maps.newMap();
    /**
     * Size of the screen.
     */
    private final Size screenSize;
    /**
     * Widget left coordinate.
     */
    private int left;
    /**
     * Widget width.
     */
    private int width;
    /**
     * Widget top coordinate.
     */
    private int top;
    /**
     * Widget height.
     */
    private int height;
    /**
     * Widget name.
     */
    @Getter
    private String name = "";
    /**
     * When using relative values for positions(right) instead of absolute in
     * pixel, the width is needed to compute the position. If the width has not
     * yet been parsed in the script, the left position must be recomputed when
     * width will be set.
     */
    private boolean leftNeedReposition;
    /**
     * If leftNeedReposition is set to true, this keep the relative positions
     * parameters data.
     */
    private String relativeLeftPos;
    /**
     * When using relative values for positions(bottom) instead of absolute in
     * pixel, the height is needed to compute the position. If the height has
     * not yet been parsed in the script, the top position must be recomputed
     * when height will be set.
     */
    private boolean topNeedReposition;
    /**
     * If topNeedReposition is set to true, this keep the relative positions
     * parameters data.
     */
    private String relativeTopPos;

    /**
     * Simple constructor, initialize with empty values.
     *
     * @param screen Screen size.
     */
    GuiCommonDefinition(final Size screen) {
        super();
        this.screenSize = screen;
    }


    /**
     * @return The widget coordinates data.
     */
    public final Coordinates getCoordinates() {
        return new Coordinates(this.width, this.height, this.left, this.top);
    }

    /**
     * Set the widget width and recompute the position if needed.
     *
     * @param widgetWidth Widget width.
     * @throws ParserException <li>If the parameter is not a valid number.</li> <li>If the
     *                         parameter is equal to 0</li>
     */
    final void setWidth(final String widgetWidth) throws ParserException {
        if (widgetWidth.contains("full")) {
            final String[] args = widgetWidth.split("-");
            if (args.length == 1) {
                this.width = this.screenSize.width;
            } else {
                try {
                    this.width = this.screenSize.width - Integer.parseInt(args[1]);
                } catch (final NumberFormatException nfe) {
                    throw new ParserException("Invalid width value:", nfe);
                }
            }
        } else {
            try {
                this.width = Integer.parseInt(widgetWidth);
            } catch (final NumberFormatException nfe) {
                throw new ParserException("Invalid width value:", nfe);
            }
        }
        if (this.width == 0) {
            throw new ParserException("Width of zero is now allowed.");
        }
        if (this.leftNeedReposition) {
            this.setLeft(this.relativeLeftPos);
            this.leftNeedReposition = false;
        }
    }

    /**
     * Set the widget height and recompute the position if needed.
     *
     * @param widgetHeight Widget height.
     * @throws ParserException <li>If the parameter is not a valid number.</li> <li>If the
     *                         parameter is equal to 0</li>
     */
    final void setHeight(final String widgetHeight) throws ParserException {
        if (widgetHeight.contains("full")) {
            final String[] args = widgetHeight.split("-");
            if (args.length == 1) {
                this.height = this.screenSize.height;
            } else {
                try {
                    this.height = this.screenSize.height - Integer.parseInt(args[1]);
                } catch (final NumberFormatException nfe) {
                    throw new ParserException("Invalid height value:", nfe);
                }
            }
        } else {
            try {
                this.height = Integer.parseInt(widgetHeight);
            } catch (final NumberFormatException nfe) {
                throw new ParserException("Invalid height value:", nfe);
            }
        }
        if (this.height == 0) {
            throw new ParserException("Height of zero is now allowed.");
        }
        if (this.topNeedReposition) {
            this.setTop(this.relativeTopPos);
            this.topNeedReposition = false;
        }
    }

    /**
     * Set the left position value.
     *
     * @param leftValue Possible values are <li> a float to set the absolute position
     *                  in the container.</li> <li>left#other widget to set the
     *                  element at a relative position left from an other widget.</li>
     *                  <li>right#other widget to set the element at a relative
     *                  position right from an other widget.</li>
     * @throws ParserException If the parameter does not match the expected values.
     */
    final void setLeft(final String leftValue) throws ParserException {
        final String[] values = leftValue.split("#");
        switch (values[0]) {
            case "left":
                if (values.length > 1) {
                    GuiCommonDefinition other = GuiCommonDefinition.DEFINITION_LIST.get(values[1]);
                    this.left = other.left - other.width;
                } else {
                    this.left = 0;
                }
                break;
            case "right":
                // to compute right position, the width is needed
                if (this.width != 0) {
                    if (values.length == 1) {
                        this.left = this.screenSize.width - this.width;
                    } else {
                        GuiCommonDefinition other = GuiCommonDefinition.DEFINITION_LIST.get(values[1]);
                        this.left = other.left + other.width;
                    }
                } else {
                    this.leftNeedReposition = true;
                    this.relativeLeftPos = leftValue;
                }
                break;
            case "center":
                // to compute right position, the width is needed
                if (this.width != 0) {
                    if (values.length == 1) {
                        this.left = (this.screenSize.width >> 1) - (this.width >> 1);
                    }
                } else {
                    this.leftNeedReposition = true;
                    this.relativeLeftPos = leftValue;
                }
                break;
            default:
                try {
                    this.left = Integer.parseInt(values[0]);
                } catch (final NumberFormatException nfe) {
                    throw new ParserException("Invalid left value:", nfe);
                }
                break;
        }

    }

    /**
     * Set the top position value.
     *
     * @param topValue Possible values are <li> a float to set the absolute position
     *                 in the container.</li> <li>top to set the element at the top
     *                 of the container.</li> <li>bottom to set the element at the
     *                 bottom of the container.</li>
     * @throws ParserException If the parameter does not match the expected values.
     */
    final void setTop(final String topValue) throws ParserException {
        switch (topValue) {
            case "top":
                this.top = 0;
                break;
            case "bottom":
                if (this.height != 0) {
                    this.top = this.screenSize.height - this.height;
                } else {
                    this.topNeedReposition = true;
                    this.relativeTopPos = topValue;
                }
                break;
            default:
                try {
                    this.top = Integer.parseInt(topValue);
                } catch (final NumberFormatException nfe) {
                    throw new ParserException("Invalid top value:", nfe);
                }
                break;
        }
    }

    /**
     * @param widgetName New widget name.
     */
    final void setName(final String widgetName) {
        this.name = widgetName;
        GuiCommonDefinition.DEFINITION_LIST.put(this.name, this);
    }

}
