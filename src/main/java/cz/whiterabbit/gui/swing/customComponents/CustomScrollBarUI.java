package cz.whiterabbit.gui.swing.customComponents;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollBarUI extends BasicScrollBarUI {
    private final Dimension d = new Dimension();

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return d;
            }
        };
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return d;
            }
        };
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Color color = null;
        JScrollBar sb = (JScrollBar) c;
        if (!sb.isEnabled() || r.width > r.height) {
            return;
        } else if (isDragging) {
            color = new Color(66, 70, 112);
        } else if (isThumbRollover()) {
            color = new Color(66, 70, 112);
        } else {
            color = new Color(66, 70, 112);
        }
        g2.setPaint(color);
        g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
        g2.dispose();
    }

    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {
        super.setThumbBounds(x, y, width, height);
        scrollbar.repaint();
    }

    public enum ScrollBarOrientation {
        HORIZONTAL {
            int getThickness(Dimension size) {
                return size.height;
            }
            int getLength(Dimension size) {
                return size.width;
            }
            int getPosition(Point point) {
                return point.x;
            }
            Rectangle updateBoundsPosition(Rectangle bounds, int newPosition) {
                bounds.setLocation(newPosition, bounds.y);
                return bounds;
            }
            Rectangle createBounds(Component container, int position, int length) {
                return new Rectangle(position, 0, length, container.getHeight());
            }
            Rectangle createCenteredBounds(Component container, int position, int thickness, int length) {
                int y = container.getHeight() / 2 - thickness / 2;
                return new Rectangle(position, y, length, thickness);
            }
        },

        VERTICAL {
            int getThickness(Dimension size) {
                return size.width;
            }
            int getLength(Dimension size) {
                return size.height;
            }
            int getPosition(Point point) {
                return point.y;
            }
            Rectangle updateBoundsPosition(Rectangle bounds, int newPosition) {
                bounds.setLocation(bounds.x, newPosition);
                return bounds;
            }
            Rectangle createBounds(Component container, int position, int length) {
                return new Rectangle(0, position, container.getWidth(), length);
            }
            Rectangle createCenteredBounds(Component container, int position, int thickness, int length) {
                int x = container.getWidth() / 2 - thickness / 2;
                return new Rectangle(x, position, thickness, length);
            }
        };

        public static ScrollBarOrientation getOrientation(int orientation) {
            return VERTICAL;
        }

        /**
         * Get's the thickness of the given size. Thickness corresponds to the dimension that does not
         * vary in size. That is, a horizontal scroll bar's thickness corresponds to the y dimension,
         * while a vertical scroll bar's thickness corresponds to the x dimension.
         *
         * @param size the 2-dimensional size to extract the thickness from.
         * @return the thickness of the given size.
         */
        abstract int getThickness(Dimension size);

        /**
         * Get's the length of the given size. Length corresponds to the dimension that varies in size.
         * That is, a horizontal scroll bar's length corresponds to the x dimension, while a vertical
         * scroll bar's length corresponds to the y dimension.
         *
         * @param size the 2-dimensional size to extract the length from.
         * @return the length of the given size.
         */
        abstract int getLength(Dimension size);

        /**
         * Get's the position from the given {@link Point}. Position refers to the dimension of a point
         * on which the scroll bar scrolls. That is, a horiztonal scroll bar's position corresponds to
         * the x dimension, while a vertical scroll bar's position corresponds to the y dimension.
         *
         * @param point the {@code Point} from which to extrac the position from.
         * @return the position value of the given {@code Point}.
         */
        abstract int getPosition(Point point);

        /**
         * Moves the given bounds to the given position. For a horiztonal scroll bar this translates
         * into {@code bounds.x = newPosition}, while for a vertical scroll bar this translates into
         * {@code bounds.y = newPosition}.
         *
         * @param bounds      the bounds to update with the new position.
         * @param newPosition the new position to set the bounds to.
         * @return the updated bounds.
         */
        abstract Rectangle updateBoundsPosition(Rectangle bounds, int newPosition);

        /**
         * Creates bounds based on the given {@link Component}, position and length. The supplied
         * component will be used to determine the thickness of the bounds. The position will be used
         * to locate the bounds along the scrollable axis. The length will be used to determine the
         * length of the bounds along the scrollable axis.
         * Horizontal scroll bars, the bounds will be derived like this:
         *     new Rectangle(position, 0, length, container.getHeigth())
         * Vertical scroll bar bounds will be derived like this:
         *     new Rectangle(0, container.getWidth(), position, length)
         *
         * @param container the {@code Component} to use to determine the thickness of the bounds.
         * @param position  the position of the bounds.
         * @param length    the length of the bounds.
         * @return the created bounds.
         */
        abstract Rectangle createBounds(Component container, int position, int length);

        /**
         * Creates bounds centered in the given {@link Component} located at the given position, with
         * the given thickness and length.
         * Horizontal scroll bars, the bounds will be derived like this:
         *       new Rectangle(position, container.getHeight()/2 - thickness/2, length, thickness)
         * Vertical scroll bars, the bounds will be derived like this:
         *     new Rectangle(container.getWidth()/2 - thickness/2, position, thickness, length)
         *
         * @param container the {@code Component} to use to determine the thickness of the bounds.
         * @param position  the position of the bounds.
         * @param thickness the thickness of the given bounds.
         * @param length    the length of the bounds.
         * @return the created bounds.
         */
        abstract Rectangle createCenteredBounds(Component container, int position, int thickness, int length);
    }
}