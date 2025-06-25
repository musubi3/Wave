package com.J2;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

public class Utils {
    private static Random r = new Random();
    private static int x_offset = 17;

    public static boolean mouse_over(int mx, int my, int x, int y, int width, int height) {
        return mx > x && mx < x + width ? my > y && my < y + height : false;
    }

    public static Map<String, Integer> get_text_dimensions(String text, Font font) {
        BufferedImage dummyImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dummyImage.createGraphics();

        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        return new HashMap<>() {
            {
                put("width", fm.stringWidth(text));
                put("height", fm.getHeight());
                put("ascent", fm.getAscent());
                put("descent", fm.getDescent());
            }
        };
    }

    public static int rand_int(int min, int max) {
        return r.nextInt(max - min - 1) + min;
    }

    public static float rand_float(float min, float max) {
        return min + r.nextFloat() * (max - min);
    }

    public static float clamp(float x, float min, float max) {
        return x = x >= max ? max : x <= min ? min : x;
    }

    public static BufferedImage scale_image(BufferedImage img, float scale) {
        int w = (int) (img.getWidth() * scale);
        int h = (int) (img.getHeight() * scale);
        BufferedImage dst_img = new BufferedImage(w, h, img.getType());
        AffineTransform scaler = new AffineTransform();
        scaler.scale(scale, scale);
        AffineTransformOp scaler_op = new AffineTransformOp(scaler, AffineTransformOp.TYPE_BILINEAR);
        return scaler_op.filter(img, dst_img);
    }

    public static void render_menu_particles(Handler handler) {
        if (Game.gameState == STATE.Menu)
            for (int i = 0; i < 30; i++)
                handler.add_object(
                        new MenuParticle(rand_int(50, Game.WIDTH - 50), rand_int(50, Game.HEIGHT - 50),
                                ID.MenuParticle, handler));
    }

    public static void render_menu(Graphics2D g, int user, ArrayList<String> saves, boolean render_load) {
        Color menuColor = Settings.darkMode ? Color.lightGray : Color.darkGray;
        Composite original = g.getComposite();
        Map<String, Map<String, Integer>> dims = new HashMap<>() {
            {
                put("W A V E", Utils.get_text_dimensions("W A V E", Game.titleFont));
                put("MUSUBI", Utils.get_text_dimensions("MUSUBI", Game.titleFont2));
                put(Game.VERSION, Utils.get_text_dimensions(Game.VERSION, Game.titleFont2));
                put("Single Player", Utils.get_text_dimensions("Single Player", Game.menuFont5));
                put("Multiplayer", Utils.get_text_dimensions("Multiplayer", Game.menuFont5));
                put("Settings", Utils.get_text_dimensions("Settings", Game.menuFont));
                put("Quit", Utils.get_text_dimensions("Quit", Game.menuFont));
                put("Load", Utils.get_text_dimensions("Load Game", Game.menuFont6));
                put("Shop", Utils.get_text_dimensions("Shop", Game.menuFont6));
            }
        };

        // Single Player
        int button_width1 = 220;
        int button_height1 = 80;
        int button_X1 = (Game.WIDTH - x_offset) / 2 - button_width1 - 15;
        int button_Y1 = (int) (Game.HEIGHT * 0.27);

        // Multiplayer
        int button_width2 = button_width1;
        int button_height2 = button_height1;
        int button_X2 = button_X1 + button_width1 + 30;
        int button_Y2 = button_Y1;

        // Settings
        int button_width3 = button_width1 * 2 + 30;
        int button_height3 = button_height1;
        int button_X3 = button_X1;
        int button_Y3 = button_Y1 + button_height1 + 30;

        // Quit
        int button_width4 = button_width3;
        int button_height4 = button_height1;
        int button_X4 = button_X1;
        int button_Y4 = button_Y3 + button_height1 + 30;

        // Load Game
        int button_width5 = 110;
        int button_height5 = 35;
        int button_X5 = 60;
        int button_Y5 = (int) (Game.HEIGHT * 0.75);

        // Shop
        int button_width6 = button_width5;
        int button_height6 = button_height5;
        int button_X6 = Game.WIDTH - x_offset - button_width6 - button_X5;
        int button_Y6 = button_Y5;

        // W A V E
        g.setFont(Game.titleFont);
        g.setColor(Settings.darkMode ? Color.white : Color.black);
        Map<String, Integer> wave_dims = dims.get("W A V E");
        g.drawString("W A V E", (Game.WIDTH - x_offset - wave_dims.get("width")) / 2, (int) (Game.HEIGHT * 0.2));

        // About
        g.setFont(Game.titleFont2);
        g.setColor(menuColor);
        Map<String, Integer> version_dims = dims.get(Game.VERSION);
        g.drawString(Game.VERSION, (Game.WIDTH - x_offset - version_dims.get("width") - 30),
                (int) (Game.HEIGHT * 0.92));
        g.setColor(menuColor);
        g.drawString("MUSUBI", 15, (int) (Game.HEIGHT * 0.92));

        // Single Player
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
        g.setColor(Settings.darkMode ? Color.darkGray : new Color(235, 235, 235));
        g.fillRoundRect(button_X1, button_Y1, button_width1, button_height1, 20, 20);
        g.setComposite(original);
        g.setFont(Game.menuFont5);
        g.setColor(menuColor);
        g.drawRoundRect(button_X1, button_Y1, button_width1, button_height1, 20, 20);
        Map<String, Integer> single_dims = dims.get("Single Player");
        g.drawString("Single Player", button_X1 + (button_width1 - single_dims.get("width")) / 2,
                button_Y1 + (button_height1 - single_dims.get("height")) / 2 + single_dims.get("ascent"));

        // Multiplayer
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
        g.setColor(Settings.darkMode ? Color.darkGray : new Color(235, 235, 235));
        g.fillRoundRect(button_X2, button_Y2, button_width2, button_height2, 20, 20);
        g.setComposite(original);
        g.setFont(Game.menuFont5);
        g.setColor(menuColor);
        g.drawRoundRect(button_X2, button_Y2, button_width2, button_height2, 20, 20);
        Map<String, Integer> multi_dims = dims.get("Multiplayer");
        g.drawString("Multiplayer", button_X2 + (button_width2 - multi_dims.get("width")) / 2,
                button_Y2 + (button_height2 - multi_dims.get("height")) / 2 + multi_dims.get("ascent"));

        // Settings
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
        g.setColor(Settings.darkMode ? Color.darkGray : new Color(235, 235, 235));
        g.fillRoundRect(button_X3, button_Y3, button_width3, button_height3, 20, 20);
        g.setComposite(original);
        g.setFont(Game.menuFont);
        g.setColor(menuColor);
        g.drawRoundRect(button_X3, button_Y3, button_width3, button_height3, 20, 20);
        Map<String, Integer> settings_dims = dims.get("Settings");
        g.drawString("Settings", button_X3 + (button_width3 - settings_dims.get("width")) / 2,
                button_Y3 + (button_height3 - settings_dims.get("height")) / 2 + settings_dims.get("ascent"));

        // Quit
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
        g.setColor(Settings.darkMode ? Color.darkGray : new Color(235, 235, 235));
        g.fillRoundRect(button_X4, button_Y4, button_width4, button_height4, 20, 20);
        g.setComposite(original);
        g.setFont(Game.menuFont);
        g.setColor(menuColor);
        g.drawRoundRect(button_X4, button_Y4, button_width4, button_height4, 20, 20);
        Map<String, Integer> quit_dims = dims.get("Quit");
        g.drawString("Quit", button_X4 + (button_width4 - quit_dims.get("width")) / 2,
                button_Y4 + (button_height4 - quit_dims.get("height")) / 2 + quit_dims.get("ascent"));

        // Load Game
        if (render_load) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
            g.setColor(Settings.darkMode ? Color.darkGray : new Color(235, 235, 235));
            g.fillRoundRect(button_X5, button_Y5, button_width5, button_height5, 20, 20);
            g.setComposite(original);
            g.setFont(Game.menuFont6);
            g.setColor(menuColor);
            g.drawRoundRect(button_X5, button_Y5, button_width5, button_height5, 20, 20);
            Map<String, Integer> load_dims = dims.get("Load");
            g.drawString("Load Game", button_X5 + (button_width5 - load_dims.get("width")) / 2,
                    button_Y5 + (button_height5 - load_dims.get("height")) / 2 + load_dims.get("ascent"));
        }

        // Shop
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
        g.setColor(Settings.darkMode ? Color.darkGray : new Color(235, 235, 235));
        g.fillRoundRect(button_X6, button_Y6, button_width6, button_height6, 20, 20);
        g.setComposite(original);
        g.setFont(Game.menuFont6);
        g.setColor(menuColor);
        g.drawRoundRect(button_X6, button_Y6, button_width6, button_height6, 20, 20);
        Map<String, Integer> shop_dims = dims.get("Shop");
        g.drawString("Shop", button_X6 + (button_width6 - shop_dims.get("width")) / 2,
                button_Y6 + (button_height6 - shop_dims.get("height")) / 2 + shop_dims.get("ascent"));

        // Player
        if (user > 0) {
            g.setColor(Settings.darkMode ? new Color(210, 210, 210, (int) (255 * 0.1))
                    : new Color(70, 70, 70, (int) (255 * 0.2)));
            g.setFont(Game.menuFont5);

            int box_X = (int) ((Game.WIDTH - x_offset) * 0.794);
            int box_Y = (int) (Game.HEIGHT * 0.36);
            int box_width = 200;

            g.fillRoundRect(box_X, box_Y, box_width, box_width, 20, 20);
            g.setColor(Settings.darkMode ? Color.WHITE : Color.BLACK);
            g.drawRoundRect(box_X, box_Y, box_width, box_width, 20, 20);

            for (int i = 1; i <= saves.size(); i++) {
                if (user == i) {
                    Map<String, Integer> name_dims = Utils.get_text_dimensions(saves.get(i - 1), Game.menuFont5);
                    g.drawString(saves.get(i - 1), box_X + (box_width - name_dims.get("width")) / 2,
                            box_Y - 15);
                }
            }
        }
    }
}
