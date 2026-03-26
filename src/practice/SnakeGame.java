package practice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class SnakeGame extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeGame game = new SnakeGame();
            game.setVisible(true);
        });
    }

    public SnakeGame() {
        setTitle("SNAKE // NEON");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel panel = new GamePanel();
        add(panel);
        pack();
        setLocationRelativeTo(null);
    }
}

class GamePanel extends JPanel implements ActionListener, KeyListener {

    // Grid & sizing
    static final int CELL = 22;
    static final int COLS = 28;
    static final int ROWS = 22;
    static final int WIDTH  = COLS * CELL;
    static final int HEIGHT = ROWS * CELL;
    static final int HUD    = 70;

    // Colors - Cyberpunk neon palette
    static final Color BG        = new Color(5, 5, 18);
    static final Color GRID_CLR  = new Color(18, 18, 45);
    static final Color HEAD_CLR  = new Color(0, 255, 200);
    static final Color BODY_CLR  = new Color(0, 180, 140);
    static final Color FOOD_CLR  = new Color(255, 60, 120);
    static final Color FOOD_GLW  = new Color(255, 60, 120, 80);
    static final Color SCORE_CLR = new Color(0, 255, 200);
    static final Color ACCENT    = new Color(180, 0, 255);
    static final Color DIM_WHITE = new Color(200, 200, 220, 180);

    // Game state
    enum State { MENU, PLAYING, PAUSED, DEAD }

    State state = State.MENU;
    Deque<Point> snake = new ArrayDeque<>();
    Point food;
    int dx = 1, dy = 0;
    int pendingDx = 1, pendingDy = 0;
    int score = 0, highScore = 0;
    int speed = 130;
    javax.swing.Timer gameTimer;
    Random rng = new Random();

    // Particles
    List<Particle> particles = new ArrayList<>();

    // Animation
    float foodPulse = 0f;
    float bgPulse = 0f;
    float deathAnim = 0f;
    javax.swing.Timer animTimer;

    // Fonts
    Font fontTitle, fontHUD, fontSub, fontMono;

    // Offscreen buffer
    BufferedImage buffer;
    Graphics2D bg2;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT + HUD));
        setBackground(BG);
        setFocusable(true);
        addKeyListener(this);

        // Load fonts (fallback to system)
        fontTitle = new Font("Courier New", Font.BOLD, 48);
        fontHUD   = new Font("Courier New", Font.BOLD, 22);
        fontSub   = new Font("Courier New", Font.PLAIN, 14);
        fontMono  = new Font("Courier New", Font.PLAIN, 12);

        buffer = new BufferedImage(WIDTH, HEIGHT + HUD, BufferedImage.TYPE_INT_ARGB);
        bg2 = buffer.createGraphics();
        bg2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bg2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        gameTimer = new javax.swing.Timer(speed, this);

        animTimer = new javax.swing.Timer(16, e -> {
            foodPulse = (foodPulse + 0.07f) % ((float) Math.PI * 2);
            bgPulse   = (bgPulse   + 0.02f) % ((float) Math.PI * 2);
            if (state == State.DEAD) deathAnim = Math.min(deathAnim + 0.03f, 1f);
            updateParticles();
            repaint();
        });
        animTimer.start();
    }

    // ───────────────── Game logic ─────────────────

    void startGame() {
        snake.clear();
        particles.clear();
        score = 0;
        dx = 1; dy = 0;
        pendingDx = 1; pendingDy = 0;
        deathAnim = 0f;
        int cx = COLS / 2, cy = ROWS / 2;
        snake.addFirst(new Point(cx, cy));
        snake.addFirst(new Point(cx + 1, cy));
        snake.addFirst(new Point(cx + 2, cy));
        placeFood();
        state = State.PLAYING;
        gameTimer.setDelay(speed);
        gameTimer.restart();
    }

    void placeFood() {
        Set<String> occupied = new HashSet<>();
        for (Point p : snake) occupied.add(p.x + "," + p.y);
        int fx, fy;
        do {
            fx = rng.nextInt(COLS);
            fy = rng.nextInt(ROWS);
        } while (occupied.contains(fx + "," + fy));
        food = new Point(fx, fy);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (state != State.PLAYING) return;
        dx = pendingDx;
        dy = pendingDy;

        Point head = snake.peekFirst();
        int nx = head.x + dx;
        int ny = head.y + dy;

        // Wall collision
        if (nx < 0 || nx >= COLS || ny < 0 || ny >= ROWS) { die(); return; }
        Point next = new Point(nx, ny);

        // Self collision (skip tail since it moves)
        int i = 0;
        for (Point p : snake) {
            if (i == snake.size() - 1) break;
            if (p.equals(next)) { die(); return; }
            i++;
        }

        snake.addFirst(next);

        if (next.equals(food)) {
            score += 10;
            if (score > highScore) highScore = score;
            spawnFoodParticles();
            placeFood();
            if (score % 50 == 0) {
                speed = Math.max(50, speed - 10);
                gameTimer.setDelay(speed);
            }
        } else {
            snake.removeLast();
        }
    }

    void die() {
        state = State.DEAD;
        gameTimer.stop();
        deathAnim = 0f;
        spawnDeathParticles();
    }

    // ───────────────── Particles ─────────────────

    static class Particle {
        float x, y, vx, vy, life, maxLife, size;
        Color color;
        Particle(float x, float y, float vx, float vy, float life, float size, Color color) {
            this.x = x; this.y = y; this.vx = vx; this.vy = vy;
            this.life = this.maxLife = life; this.size = size; this.color = color;
        }
    }

    void spawnFoodParticles() {
        float cx = food.x * CELL + CELL / 2f + HUD;
        float cy = food.y * CELL + CELL / 2f;
        for (int i = 0; i < 20; i++) {
            float angle = rng.nextFloat() * (float)(Math.PI * 2);
            float speed  = 1.5f + rng.nextFloat() * 3f;
            particles.add(new Particle(cx, cy,
                    (float)Math.cos(angle) * speed,
                    (float)Math.sin(angle) * speed,
                    30 + rng.nextInt(20), 3 + rng.nextFloat() * 3,
                    new Color(255, 60 + rng.nextInt(80), 120)));
        }
    }

    void spawnDeathParticles() {
        for (Point p : snake) {
            float cx = p.x * CELL + CELL / 2f;
            float cy = p.y * CELL + CELL / 2f + HUD;
            for (int i = 0; i < 4; i++) {
                float angle = rng.nextFloat() * (float)(Math.PI * 2);
                float spd   = 1f + rng.nextFloat() * 2f;
                particles.add(new Particle(cx, cy,
                        (float)Math.cos(angle) * spd,
                        (float)Math.sin(angle) * spd,
                        40 + rng.nextInt(30), 2 + rng.nextFloat() * 2,
                        new Color(0, 200 + rng.nextInt(55), 180)));
            }
        }
    }

    void updateParticles() {
        particles.removeIf(p -> p.life <= 0);
        for (Particle p : particles) {
            p.x += p.vx; p.y += p.vy;
            p.vy += 0.05f;
            p.vx *= 0.97f;
            p.life--;
        }
    }

    // ───────────────── Rendering ─────────────────

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = bg2;

        // Clear
        g2.setColor(BG);
        g2.fillRect(0, 0, WIDTH, HEIGHT + HUD);

        drawHUD(g2);
        drawGrid(g2);
        drawFood(g2);
        drawSnake(g2);
        drawParticles(g2);

        switch (state) {
            case MENU  -> drawMenu(g2);
            case DEAD  -> drawDeathScreen(g2);
            case PAUSED -> drawPaused(g2);
        }

        g.drawImage(buffer, 0, 0, null);
    }

    void drawHUD(Graphics2D g) {
        // HUD background
        g.setColor(new Color(8, 8, 25));
        g.fillRect(0, 0, WIDTH, HUD);

        // Separator line with glow
        drawGlowLine(g, 0, HUD - 1, WIDTH, HUD - 1, HEAD_CLR, 2, 8);

        // Score
        g.setFont(fontHUD);
        g.setColor(DIM_WHITE);
        g.drawString("SCORE", 24, 30);
        g.setFont(new Font("Courier New", Font.BOLD, 28));
        drawGlowText(g, String.format("%05d", score), 24, 58, SCORE_CLR, 10);

        // High score
        g.setFont(fontHUD);
        g.setColor(DIM_WHITE);
        g.drawString("BEST", WIDTH / 2 - 30, 30);
        g.setFont(new Font("Courier New", Font.BOLD, 28));
        drawGlowText(g, String.format("%05d", highScore), WIDTH / 2 - 30, 58, ACCENT, 10);

        // Speed
        g.setFont(fontSub);
        g.setColor(DIM_WHITE);
        int lvl = (130 - speed) / 10 + 1;
        g.drawString("LVL " + Math.max(1, lvl), WIDTH - 80, 30);
        // Speed bar
        int barW = 60, barH = 6;
        int bx = WIDTH - 80, by = 40;
        g.setColor(new Color(30, 30, 60));
        g.fillRoundRect(bx, by, barW, barH, 3, 3);
        g.setColor(HEAD_CLR);
        int filled = (int)(barW * Math.min(1f, (130f - speed) / 80f));
        g.fillRoundRect(bx, by, Math.max(4, filled), barH, 3, 3);

        // Snake length
        g.setFont(fontSub);
        g.setColor(new Color(120, 120, 160));
        g.drawString("LEN:" + snake.size(), WIDTH - 80, 62);
    }

    void drawGrid(Graphics2D g) {
        g.setColor(GRID_CLR);
        for (int x = 0; x <= COLS; x++)
            g.drawLine(x * CELL, HUD, x * CELL, HUD + HEIGHT);
        for (int y = 0; y <= ROWS; y++)
            g.drawLine(0, HUD + y * CELL, WIDTH, HUD + y * CELL);

        // Corner glow effect
        float intensity = 0.5f + 0.5f * (float)Math.sin(bgPulse);
        int alpha = (int)(30 * intensity);
        RadialGradientPaint rp = new RadialGradientPaint(
                WIDTH / 2f, HUD + HEIGHT / 2f,
                WIDTH * 0.7f,
                new float[]{0f, 1f},
                new Color[]{new Color(0, 40, 80, alpha), new Color(0, 0, 0, 0)}
        );
        g.setPaint(rp);
        g.fillRect(0, HUD, WIDTH, HEIGHT);
        g.setPaint(null);
    }

    void drawFood(Graphics2D g) {
        if (food == null) return;
        float pulse = 0.6f + 0.4f * (float)Math.sin(foodPulse);
        int fx = food.x * CELL, fy = HUD + food.y * CELL;
        int margin = 3;

        // Outer glow
        for (int r = 3; r >= 1; r--) {
            g.setColor(new Color(255, 60, 120, 30 * r));
            g.fillOval(fx - r * 3, fy - r * 3, CELL + r * 6, CELL + r * 6);
        }

        // Inner glow circle
        g.setColor(FOOD_GLW);
        g.fillOval(fx + margin - 2, fy + margin - 2, CELL - margin * 2 + 4, CELL - margin * 2 + 4);

        // Food body
        int sz = (int)((CELL - margin * 2) * pulse);
        int off = (CELL - margin * 2 - sz) / 2;
        GradientPaint gp = new GradientPaint(
                fx + margin + off, fy + margin + off, new Color(255, 120, 160),
                fx + margin + off + sz, fy + margin + off + sz, FOOD_CLR
        );
        g.setPaint(gp);
        g.fillRoundRect(fx + margin + off, fy + margin + off, sz, sz, 6, 6);
        g.setPaint(null);

        // Cross/plus on food
        g.setColor(new Color(255, 255, 255, 120));
        int cx2 = fx + CELL / 2, cy2 = fy + CELL / 2;
        g.drawLine(cx2 - 3, cy2, cx2 + 3, cy2);
        g.drawLine(cx2, cy2 - 3, cx2, cy2 + 3);
    }

    void drawSnake(Graphics2D g) {
        if (snake.isEmpty()) return;
        List<Point> parts = new ArrayList<>(snake);

        // Draw body (reverse so head is on top)
        for (int i = parts.size() - 1; i >= 0; i--) {
            Point p = parts.get(i);
            int px = p.x * CELL, py = HUD + p.y * CELL;
            int margin = 2;
            float t = 1f - (float)i / parts.size();

            if (i == 0) {
                // Head
                // Glow
                g.setColor(new Color(0, 255, 200, 40));
                g.fillRoundRect(px - 3, py - 3, CELL + 6, CELL + 6, 10, 10);
                // Head body
                GradientPaint gp = new GradientPaint(
                        px + margin, py + margin, new Color(100, 255, 230),
                        px + CELL - margin, py + CELL - margin, HEAD_CLR
                );
                g.setPaint(gp);
                g.fillRoundRect(px + margin, py + margin, CELL - margin * 2, CELL - margin * 2, 8, 8);
                g.setPaint(null);

                // Eyes
                Point next = i + 1 < parts.size() ? parts.get(1) : null;
                drawEyes(g, p, next, px, py);
            } else {
                // Body segment
                int alpha = (int)(80 + 120 * t);
                Color c = new Color(
                        (int)(BODY_CLR.getRed() * t),
                        (int)(BODY_CLR.getGreen() * (0.5f + 0.5f * t)),
                        (int)(BODY_CLR.getBlue() * (0.5f + 0.5f * t)),
                        alpha
                );
                g.setColor(c);
                g.fillRoundRect(px + margin, py + margin,
                        CELL - margin * 2, CELL - margin * 2, 6, 6);

                // Inner lighter stripe
                if (t > 0.4f) {
                    g.setColor(new Color(0, 255, 200, (int)(30 * t)));
                    g.fillRoundRect(px + margin + 2, py + margin + 2,
                            CELL - margin * 2 - 4, CELL - margin * 2 - 4, 4, 4);
                }
            }
        }
    }

    void drawEyes(Graphics2D g, Point head, Point neck, int px, int py) {
        int ex1, ey1, ex2, ey2;
        int es = 4;
        if (dx == 1) { // right
            ex1 = px + CELL - 6; ey1 = py + 5;
            ex2 = px + CELL - 6; ey2 = py + CELL - 9;
        } else if (dx == -1) { // left
            ex1 = px + 4; ey1 = py + 5;
            ex2 = px + 4; ey2 = py + CELL - 9;
        } else if (dy == -1) { // up
            ex1 = px + 5; ey1 = py + 4;
            ex2 = px + CELL - 9; ey2 = py + 4;
        } else { // down
            ex1 = px + 5; ey1 = py + CELL - 6;
            ex2 = px + CELL - 9; ey2 = py + CELL - 6;
        }
        g.setColor(Color.WHITE);
        g.fillOval(ex1, ey1, es, es);
        g.fillOval(ex2, ey2, es, es);
        g.setColor(new Color(0, 50, 40));
        g.fillOval(ex1 + 1, ey1 + 1, es - 2, es - 2);
        g.fillOval(ex2 + 1, ey2 + 1, es - 2, es - 2);
    }

    void drawParticles(Graphics2D g) {
        for (Particle p : particles) {
            float alpha = p.life / p.maxLife;
            g.setColor(new Color(
                    p.color.getRed(), p.color.getGreen(), p.color.getBlue(),
                    (int)(255 * alpha)
            ));
            int s = (int)(p.size * alpha);
            g.fillOval((int)p.x - s/2, (int)p.y - s/2, s, s);
        }
    }

    void drawMenu(Graphics2D g) {
        // Dim overlay
        g.setColor(new Color(0, 0, 10, 200));
        g.fillRect(0, HUD, WIDTH, HEIGHT);

        // Title
        drawGlowText(g, "SNAKE", WIDTH / 2 - 100, HUD + HEIGHT / 2 - 80, HEAD_CLR, 20);
        g.setFont(fontTitle);

        // Subtitle
        g.setFont(fontSub);
        g.setColor(ACCENT);
        String sub = "// NEON EDITION";
        g.drawString(sub, WIDTH / 2 - g.getFontMetrics().stringWidth(sub) / 2, HUD + HEIGHT / 2 - 40);

        // Instructions
        String[] lines = {"[SPACE] or [ENTER]  START", "[↑↓←→]  MOVE", "[P]  PAUSE"};
        g.setFont(fontMono);
        for (int i = 0; i < lines.length; i++) {
            float blink = 0.5f + 0.5f * (float)Math.sin(bgPulse * 2 + i);
            g.setColor(new Color(200, 200, 220, (int)(160 + 80 * blink)));
            int w = g.getFontMetrics().stringWidth(lines[i]);
            g.drawString(lines[i], WIDTH / 2 - w / 2, HUD + HEIGHT / 2 + 20 + i * 24);
        }

        // Decorative corners
        drawCornerDecor(g);
    }

    void drawDeathScreen(Graphics2D g) {
        // Fade overlay
        int alpha = (int)(180 * deathAnim);
        g.setColor(new Color(30, 0, 10, alpha));
        g.fillRect(0, HUD, WIDTH, HEIGHT);

        if (deathAnim > 0.5f) {
            float a2 = (deathAnim - 0.5f) * 2f;

            // GAME OVER
            g.setFont(new Font("Courier New", Font.BOLD, 40));
            String go = "GAME OVER";
            int gw = g.getFontMetrics().stringWidth(go);
            drawGlowText(g, go, (WIDTH - gw) / 2, HUD + HEIGHT / 2 - 50, FOOD_CLR, (int)(15 * a2));

            // Score
            g.setFont(fontHUD);
            g.setColor(new Color(255, 255, 255, (int)(200 * a2)));
            String sc = "SCORE: " + score;
            int sw = g.getFontMetrics().stringWidth(sc);
            g.drawString(sc, (WIDTH - sw) / 2, HUD + HEIGHT / 2);

            // Restart
            float blink = 0.5f + 0.5f * (float)Math.sin(bgPulse * 3);
            g.setFont(fontMono);
            g.setColor(new Color(200, 200, 220, (int)(a2 * (160 + 80 * blink))));
            String rs = "[SPACE]  PLAY AGAIN    [M]  MENU";
            int rw = g.getFontMetrics().stringWidth(rs);
            g.drawString(rs, (WIDTH - rw) / 2, HUD + HEIGHT / 2 + 44);
        }
    }

    void drawPaused(Graphics2D g) {
        g.setColor(new Color(0, 0, 20, 160));
        g.fillRect(0, HUD, WIDTH, HEIGHT);
        g.setFont(new Font("Courier New", Font.BOLD, 38));
        String p = "PAUSED";
        drawGlowText(g, p, WIDTH / 2 - g.getFontMetrics().stringWidth(p) / 2,
                HUD + HEIGHT / 2 - 10, HEAD_CLR, 14);
        g.setFont(fontSub);
        g.setColor(DIM_WHITE);
        String res = "[P]  RESUME";
        g.drawString(res, WIDTH / 2 - g.getFontMetrics().stringWidth(res) / 2,
                HUD + HEIGHT / 2 + 30);
    }

    void drawCornerDecor(Graphics2D g) {
        int len = 30;
        g.setColor(HEAD_CLR);
        g.setStroke(new BasicStroke(2));
        int x0 = 20, y0 = HUD + 20, x1 = WIDTH - 20, y1 = HUD + HEIGHT - 20;
        g.drawLine(x0, y0, x0 + len, y0); g.drawLine(x0, y0, x0, y0 + len);
        g.drawLine(x1, y0, x1 - len, y0); g.drawLine(x1, y0, x1, y0 + len);
        g.drawLine(x0, y1, x0 + len, y1); g.drawLine(x0, y1, x0, y1 - len);
        g.drawLine(x1, y1, x1 - len, y1); g.drawLine(x1, y1, x1, y1 - len);
        g.setStroke(new BasicStroke(1));
    }

    // ───────────────── Helpers ─────────────────

    void drawGlowText(Graphics2D g, String text, int x, int y, Color color, int glowRadius) {
        g.setFont(g.getFont());
        for (int r = glowRadius; r > 0; r -= 2) {
            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(),
                    Math.max(0, 30 - r * 2)));
            g.drawString(text, x - r/2, y);
            g.drawString(text, x + r/2, y);
            g.drawString(text, x, y - r/2);
            g.drawString(text, x, y + r/2);
        }
        g.setColor(color);
        g.drawString(text, x, y);
    }

    void drawGlowLine(Graphics2D g, int x1, int y1, int x2, int y2, Color color, int w, int blur) {
        for (int i = blur; i > 0; i -= 2) {
            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20));
            g.setStroke(new BasicStroke(w + i));
            g.drawLine(x1, y1, x2, y2);
        }
        g.setColor(color);
        g.setStroke(new BasicStroke(w));
        g.drawLine(x1, y1, x2, y2);
        g.setStroke(new BasicStroke(1));
    }

    // ───────────────── Input ─────────────────

    @Override public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        switch (state) {
            case MENU -> {
                if (k == KeyEvent.VK_SPACE || k == KeyEvent.VK_ENTER) startGame();
            }
            case PLAYING -> {
                if (k == KeyEvent.VK_UP    && dy == 0) { pendingDx = 0; pendingDy = -1; }
                if (k == KeyEvent.VK_DOWN  && dy == 0) { pendingDx = 0; pendingDy = 1; }
                if (k == KeyEvent.VK_LEFT  && dx == 0) { pendingDx = -1; pendingDy = 0; }
                if (k == KeyEvent.VK_RIGHT && dx == 0) { pendingDx = 1; pendingDy = 0; }
                // WASD
                if (k == KeyEvent.VK_W && dy == 0) { pendingDx = 0; pendingDy = -1; }
                if (k == KeyEvent.VK_S && dy == 0) { pendingDx = 0; pendingDy = 1; }
                if (k == KeyEvent.VK_A && dx == 0) { pendingDx = -1; pendingDy = 0; }
                if (k == KeyEvent.VK_D && dx == 0) { pendingDx = 1; pendingDy = 0; }
                if (k == KeyEvent.VK_P) { state = State.PAUSED; gameTimer.stop(); }
            }
            case PAUSED -> {
                if (k == KeyEvent.VK_P) { state = State.PLAYING; gameTimer.start(); }
            }
            case DEAD -> {
                if (k == KeyEvent.VK_SPACE || k == KeyEvent.VK_ENTER) startGame();
                if (k == KeyEvent.VK_M) { state = State.MENU; speed = 130; }
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
