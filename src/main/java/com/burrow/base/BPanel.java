package com.burrow.base;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import com.burrow.auxiliary.Graphics2DRasterizeData;
import com.burrow.auxiliary.ScreenData;
import com.burrow.widget.root.RootWidget;
import com.burrow.widget.single_child.canvas.stroke.BRenderFilter;

public class BPanel extends JPanel implements Runnable {
    public BFrame frame;
    public RootWidget root;

    protected Thread frameThread;
    protected long lastFrame;
    public int fps = 10;

    protected boolean paintLock = true;

    public BFrame getBFrame() {
        return frame;
    }
    public void initialize(BFrame frame, RootWidget root) {
        this.frame = frame;
        this.root = root;
        
        root.init(
            this,
            new ScreenData(
                GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration()
                    .getDefaultTransform()
                    .getScaleX(),
                GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth(),
                GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight()
            )
        );
        
        addComponentListener(new ComponentAdapter() {  
            public void componentResized(ComponentEvent e) {
                root.onResize(Math.max(1, getWidth()), Math.max(1, getHeight()));
            }
        });
        addMouseListener(root);
        addMouseWheelListener(root);

        frameThread = new Thread(this);
        frameThread.start();
        paintLock = false;
    }
    @Override
    public void run() {
        while(frameThread != null) {
            if(System.nanoTime() < lastFrame + 1_000_000_000/fps) {
                try {
                    Thread.sleep(Math.max(0, lastFrame/1_000_000 + 1_000/fps - System.nanoTime()/1_000_000));
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            
            
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        lastFrame = System.nanoTime();
        if(paintLock) {return;}
        long time = System.nanoTime();
        
        paintLock = true;

        root.layout(getWidth(), getHeight());
        root.paint();
        
        
        BRenderFilter filter = new BRenderFilter(
            this,
            new double[]{0, 0, getWidth(), getHeight()},
            new double[]{0, 0}
        );
        
        root.rasterizeToGraphics2D(new Graphics2DRasterizeData((Graphics2D)g, filter, 5));
        
        paintLock = false;
        System.out.println(((int)(System.nanoTime() - time) * 0.000001));
    }
}
