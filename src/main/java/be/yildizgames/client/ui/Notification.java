package be.yildizgames.client.ui;

import be.yildizgames.common.util.StringUtil;
import be.yildizgames.module.graphic.gui.BaseContainerChild;
import be.yildizgames.module.graphic.gui.GuiFactory;
import be.yildizgames.module.graphic.gui.container.Container;
import be.yildizgames.module.graphic.gui.image.Image;
import be.yildizgames.module.graphic.gui.image.ImageTemplate;
import be.yildizgames.module.graphic.gui.image.animation.BlinkingImage;

/**
 * Simple animation with a number in it.
 * It is displayed only if the number is higher than 0.
 */
public class Notification extends BaseContainerChild {

    private static final String ANIMATION_NAME = "notification_image";

    private final Image image;

    public Notification(Container container, GuiFactory builder, ImageTemplate template) {
        super(StringUtil.buildRandomString("notification"), template.getCoordinates(), container);
        this.image = builder
                .image()
                .withBackground(template.betBackground())
                .withCoordinates(template.getCoordinates())
                .animate(new BlinkingImage(ANIMATION_NAME, 500))
                .build(container);
    }

    public final void setNumber(int number) {
        if(number == 0) {
            this.image.stopAnimation(ANIMATION_NAME);
            this.image.hide();
        } else {
            this.image.playAnimation(ANIMATION_NAME);
        }
    }

    @Override
    public void delete() {
        this.image.delete();
    }

    @Override
    protected void showImpl() {
        this.image.show();
    }

    @Override
    protected void hideImpl() {
        this.image.hide();
    }

    @Override
    protected void setSizeImpl(int newWidth, int newHeight) {
        this.image.setSize(newWidth, newHeight);
    }

    @Override
    protected Notification setPositionImpl(int newLeft, int newTop) {
        this.image.setPosition(newLeft, newTop);
        return this;
    }

    @Override
    protected void highlightImpl(boolean highlightState) {
        //Does nothing.
    }
}
