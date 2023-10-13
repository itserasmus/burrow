package com.burrow.base;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.burrow.auxiliary.ScreenData;
import com.burrow.widget.root.RootWidget;
import com.burrow.widget.single_child.canvas.stroke.BRenderFilter;

public class BPanel extends JPanel implements Runnable {
    public BFrame frame;
    public RootWidget root;

    protected Thread frameThread;
    protected long lastFrame;
    public int fps = 10;
    
    BufferedImage image;

    protected boolean paintLock = true;
    protected int[][] cArr;

    public BFrame getBFrame() {
        return frame;
    }
    public void initialize(BFrame frame, RootWidget root) {
        this.frame = frame;
        this.root = root;
        image = new BufferedImage(Math.max(1, getWidth()), Math.max(1, getHeight()), BufferedImage.TYPE_INT_ARGB);
        cArr = new int[getWidth()][getHeight()];

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
                image = new BufferedImage(Math.max(1, getWidth()), Math.max(1, getHeight()), BufferedImage.TYPE_INT_ARGB);
                cArr = new int[getWidth()][getHeight()];
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
            lastFrame = System.nanoTime();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if(paintLock) {return;}
        long time = System.nanoTime();
        
        paintLock = true;

        root.layout(getWidth(), getHeight());
        root.paint();
        
        // int height = image.getHeight();
        BRenderFilter filter = new BRenderFilter(this, new double[]{0, 0, image.getWidth(), image.getHeight()});
        
        root.rasterizeToGraphics2D((Graphics2D)g, filter);
        // root.rasterizeToCArr(cArr, filter);
        
        // for(int i = 0; i < image.getWidth(); i++) {
        //     for(int j = 0; j < height; j++) {
        //         // image.setRGB(i, j, root.pixelColor(i, j, filter.reset()));
        //         image.setRGB(i, j, cArr[i][j]);
        //     }
        // }

        g.drawImage(image, 0, 0, null);
        paintLock = false;
        System.out.println(((int)(System.nanoTime() - time) * 0.000001));
    }
}
