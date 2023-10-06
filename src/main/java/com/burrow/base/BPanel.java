package com.burrow.base;

import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.burrow.auxiliary.ScreenData;
import com.burrow.widget.root.RootWidget;

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
                    .getScaleX()
            )
        );
        
        addComponentListener(new ComponentAdapter() {  
            public void componentResized(ComponentEvent evt) {
                root.onResize(getWidth(), getHeight());
            }
        });

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
        
        paintLock = true;

        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        int[][] cArr = new int[getWidth()][getHeight()];

        root.layout(getWidth(), getHeight());
        root.paint();
        root.rasterizeToCArr(cArr);

        for(int i = 0; i < cArr.length; i++) {
            for(int j = 0; j < cArr[i].length; j++) {
                image.setRGB(i, j, cArr[i][j]);
            }
        }

        g.drawImage(image, 0, 0, null);

        paintLock = false;
    }
}
