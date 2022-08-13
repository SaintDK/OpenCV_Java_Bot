import com.github.joonasvali.naturalmouse.api.MouseMotionFactory;
import com.github.joonavali.naturalmouse.MouseMotionTestBase;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.win32.StdCallLibrary;
import org.opencv.core.Point;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import java.awt.Robot;
import java.awt.event.KeyEvent;


public class MinerTest2 extends MouseMotionTestBase {
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("Version: " + Core.VERSION);}
    public static void main(String[] args) throws AWTException, IOException{

        Rectangle rect = null;

        int count = 0;
        int laser = 0;
        int Mining = 0;
        String WindowName = "enter window name";

        int hWnd = User32.instance.FindWindowA(null, WindowName);

        JFrame window = new JFrame("Window:");
        JLabel screen = new JLabel();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.getContentPane().add(screen);


        boolean cycle = true;
        while (cycle) {


            WindowInfo w = getWindowInfo(hWnd);
            User32.instance.SetForegroundWindow(w.hwnd);
            BufferedImage createScreenCapture = new Robot().createScreenCapture(new Rectangle(w.rect.left, w.rect.top, w.rect.right - w.rect.left, w.rect.bottom - w.rect.top));

            ///////////////////translate from BufferedImage to Mat
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(createScreenCapture, "bmp", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            Mat MatImg = Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.IMREAD_UNCHANGED);
            ////////uploading an image///////////
            //gbr = rgb the order changes from the position of the characters
            Mat ASTEROID_FILD = Imgcodecs.imread("src\\ASTEROID_FILD.bmp");             //gbr(255, 255, 255)    White
            Mat ASTEROID_VELDSPAR = Imgcodecs.imread("src\\ASTEROID_VELDSPAR.bmp");     //gbr(0, 0, 255)        Red
            Mat DOCK = Imgcodecs.imread("src\\DOCK.bmp");                               //gbr(0, 255, 0)        Blue
            Mat GET_CLOSER = Imgcodecs.imread("src\\GET_CLOSER.bmp");                   //gbr(0, 255, 255)      Pink
            Mat ITEM_HANGAR = Imgcodecs.imread("src\\ITEM_HANGAR.bmp");                 //gbr(0, 255, 150)      Violet
            Mat MINING_GUN_TEAR1 = Imgcodecs.imread("src\\MINING_GUN_TEAR1.bmp");       //gbr(255, 255, 0)      Azure
            Mat MINING_HOLD = Imgcodecs.imread("src\\MINING_HOLD.bmp");                 //gbr(255, 111, 0)      Light green
            Mat NO_OBJECT_SELECTED = Imgcodecs.imread("src\\NO_OBJECT_SELECTED.bmp");   //gbr(200, 30, 0)       Green
            Mat OVERVIEW_GENERAL = Imgcodecs.imread("src\\OVERVIEW_GENERAL.bmp");       //gbr(255, 0, 255)      Yellow
            Mat OVERVIEW_MINING = Imgcodecs.imread("src\\OVERVIEW_MINING.bmp");         //gbr(170, 0, 255)      Orange
            Mat STATION = Imgcodecs.imread("src\\STATION.bmp");                         //gbr(118, 118, 118)    Gray
            Mat TARGET_LOCK = Imgcodecs.imread("src\\TARGET_LOCK.bmp");                 //gbr(0, 160, 255)      Strawberry
            Mat UNDOCK = Imgcodecs.imread("src\\UNDOCK.bmp");                           //gbr(0, 0, 0)          Black
            Mat WARP = Imgcodecs.imread("src\\WARP.bmp");                               //gbr(40, 200, 0)       black and blue
            Mat FULL = Imgcodecs.imread("src\\FULL.bmp");                               //gbr(0, 0, 255)        Red
            Mat FULL2 = Imgcodecs.imread("src\\FULL2.bmp");                             //gbr(0, 0, 255)        Red
//            Imgcodecs.imwrite("src\\a_Image\\screen.png", imgEmpty);
            //////////////////////////////////////////////////////////////////////////////////////////////////////
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat outputImage=new Mat();
            int machMethod= Imgproc.TM_CCOEFF_NORMED;

            //Template matching method
            Imgproc.matchTemplate(MatImg, FULL, outputImage, machMethod);
            Core.MinMaxLocResult FULLmmr = Core.minMaxLoc(outputImage);
            Point FULLMatchLoc=FULLmmr.maxLoc;

            Imgproc.matchTemplate(MatImg, UNDOCK, outputImage, machMethod);
            Core.MinMaxLocResult UNDOCKmmr = Core.minMaxLoc(outputImage);
            Point UNDOCKmatchLoc=UNDOCKmmr.maxLoc;

            Imgproc.matchTemplate(MatImg, OVERVIEW_MINING, outputImage, machMethod);
            Core.MinMaxLocResult OVERVIEW_MININGmmr = Core.minMaxLoc(outputImage);
            Point OVERVIEW_MININGMatchLoc=OVERVIEW_MININGmmr.maxLoc;

            Imgproc.matchTemplate(MatImg, ASTEROID_FILD, outputImage, machMethod);
            Core.MinMaxLocResult ASTEROID_FILDmmr = Core.minMaxLoc(outputImage);
            Point ASTEROID_FILDMatchLoc=ASTEROID_FILDmmr.maxLoc;

            Imgproc.matchTemplate(MatImg, ASTEROID_VELDSPAR, outputImage, machMethod);
            Core.MinMaxLocResult ASTEROID_VELDSPARmmr = Core.minMaxLoc(outputImage);
            Point ASTEROID_VELDSPARMatchLoc=ASTEROID_VELDSPARmmr.maxLoc;

            Imgproc.matchTemplate(MatImg, DOCK, outputImage, machMethod);
            Core.MinMaxLocResult DOCKmmr = Core.minMaxLoc(outputImage);
            Point DOCKMatchLoc=DOCKmmr.maxLoc;

            Imgproc.matchTemplate(MatImg, GET_CLOSER, outputImage, machMethod);
            Core.MinMaxLocResult GET_CLOSERmmr = Core.minMaxLoc(outputImage);
            Point GET_CLOSERMatchLoc=GET_CLOSERmmr.maxLoc;

            Imgproc.matchTemplate(MatImg, ITEM_HANGAR, outputImage, machMethod);
            Core.MinMaxLocResult ITEM_HANGARmmr = Core.minMaxLoc(outputImage);
            Point ITEM_HANGARMatchLoc=ITEM_HANGARmmr.maxLoc;


            Imgproc.matchTemplate(MatImg, MINING_HOLD, outputImage, machMethod);
            Core.MinMaxLocResult MINING_HOLDmmr = Core.minMaxLoc(outputImage);
            Point MINING_HOLDMatchLoc=MINING_HOLDmmr.maxLoc;

            Imgproc.matchTemplate(MatImg, NO_OBJECT_SELECTED, outputImage, machMethod);
            Core.MinMaxLocResult NO_OBJECT_SELECTEDmmr = Core.minMaxLoc(outputImage);
            Point NO_OBJECT_SELECTEDMatchLoc=NO_OBJECT_SELECTEDmmr.maxLoc;

            Imgproc.matchTemplate(MatImg, OVERVIEW_GENERAL, outputImage, machMethod);
            Core.MinMaxLocResult OVERVIEW_GENERALmmr = Core.minMaxLoc(outputImage);
            Point OVERVIEW_GENERALMatchLoc=OVERVIEW_GENERALmmr.maxLoc;

            Imgproc.matchTemplate(MatImg, STATION, outputImage, machMethod);
            Core.MinMaxLocResult STATIONmmr = Core.minMaxLoc(outputImage);
            Point STATIONMatchLoc=STATIONmmr.maxLoc;

            Imgproc.matchTemplate(MatImg, TARGET_LOCK, outputImage, machMethod);
            Core.MinMaxLocResult TARGET_LOCKmmr = Core.minMaxLoc(outputImage);
            Point TARGET_LOCKMatchLoc=TARGET_LOCKmmr.maxLoc;

            Imgproc.matchTemplate(MatImg, WARP, outputImage, machMethod);
            Core.MinMaxLocResult WARPmmr = Core.minMaxLoc(outputImage);
            Point WARPMatchLoc=WARPmmr.maxLoc;

            Imgproc.matchTemplate(MatImg, FULL2, outputImage, machMethod);
            Core.MinMaxLocResult FULL2mmr = Core.minMaxLoc(outputImage);
            Point FULL2MatchLoc=WARPmmr.maxLoc;



//            System.out.println(mmr.maxVal); /*shows how much the picture matches the input*/

            /*the code below (before catch) marks with a frame and manipulates what is in it, right click, left click, delay, etc
             * if the similarity level is higher than 0.950855073928833 = the probability that this is our needle is about 95%
             * play with this value to find the golden mean*/
            if (FULLmmr.maxVal > 0.950855073928833 && UNDOCKmmr.maxVal > 0.900855073928833|| FULL2mmr.maxVal > 0.940855073928833) { // значение mmr.maxVal показывает уровень сходства
                Imgproc.rectangle(MatImg, FULLMatchLoc, new Point(FULLMatchLoc.x + FULL.cols(), FULLMatchLoc.y + FULL.rows()), new Scalar(0, 0, 255));
                try {
                    for (DesktopWindow desktopWindow : WindowUtils.getAllWindows(true)) {
                        if (desktopWindow.getTitle().contains(WindowName)) {
                            rect = desktopWindow.getLocAndSize();
                        }
                    }
                    Robot robot = new Robot();
                    int X = (int) (rect.x + FULLMatchLoc.x + 7);
                    int Y = (int) (rect.y + FULLMatchLoc.y + 7);
                    MouseMotionFactory factory = new MouseMotionFactory();

                    factory.move(X,Y+95);
                    robot.delay(1000);

                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_A);

                    factory.move(X-100,Y+95);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);

                    robot.keyRelease(KeyEvent.VK_A);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    Thread.sleep(3000);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (UNDOCKmmr.maxVal > 0.900855073928833) { // значение mmr.maxVal показывает уровень сходства
                Imgproc.rectangle(MatImg, UNDOCKmatchLoc, new Point(UNDOCKmatchLoc.x + UNDOCK.cols(), UNDOCKmatchLoc.y + UNDOCK.rows()), new Scalar(0, 0, 0));
                try {
                    for (DesktopWindow desktopWindow : WindowUtils.getAllWindows(true)) {
                        if (desktopWindow.getTitle().contains(WindowName)) {
                            rect = desktopWindow.getLocAndSize();
                        }
                    }
                    Robot robot = new Robot();
                    int X = (int) (rect.x + UNDOCKmatchLoc.x + 7);
                    int Y = (int) (rect.y + UNDOCKmatchLoc.y + 7);
                    MouseMotionFactory factory = new MouseMotionFactory();

                    factory.move(X,Y);
                    Thread.sleep(1000);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(15000);

                    Mining = 0;
                    laser = 0;

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println(OVERVIEW_MININGmmr.maxVal +" OVERVIEW_MINING");
            if (OVERVIEW_MININGmmr.maxVal > 0.800855073928833 && Mining == 0) { // значение mmr.maxVal показывает уровень сходства
                Imgproc.rectangle(MatImg, OVERVIEW_MININGMatchLoc, new Point(OVERVIEW_MININGMatchLoc.x + OVERVIEW_MINING.cols(), OVERVIEW_MININGMatchLoc.y + OVERVIEW_MINING.rows()), new Scalar(170, 0, 255));
                try {
                    for (DesktopWindow desktopWindow : WindowUtils.getAllWindows(true)) {
                        if (desktopWindow.getTitle().contains(WindowName)) {
                            rect = desktopWindow.getLocAndSize();
                        }
                    }
                    Robot robot = new Robot();
                    int X = (int) (rect.x + OVERVIEW_MININGMatchLoc.x + 7);
                    int Y = (int) (rect.y + OVERVIEW_MININGMatchLoc.y + 7);
                    MouseMotionFactory factory = new MouseMotionFactory();
                    factory.move(X,Y);
                    Thread.sleep(1000);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(1000);

                    Mining++;

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            System.out.println(ASTEROID_FILDmmr.maxVal + " ASTEROID_FILD");
            if (ASTEROID_FILDmmr.maxVal > 0.900855073928833 && (FULLmmr.maxVal < 0.900855073928833 || FULL2mmr.maxVal < 0.940855073928833)) { // значение mmr.maxVal показывает уровень сходства
                Imgproc.rectangle(MatImg, ASTEROID_FILDMatchLoc, new Point(ASTEROID_FILDMatchLoc.x + ASTEROID_FILD.cols(), ASTEROID_FILDMatchLoc.y + ASTEROID_FILD.rows()), new Scalar(255, 255, 255));
                try {
                    for (DesktopWindow desktopWindow : WindowUtils.getAllWindows(true)) {
                        if (desktopWindow.getTitle().contains(WindowName)) {
                            rect = desktopWindow.getLocAndSize();
                        }
                    }
                    Robot robot = new Robot();
                    int X = (int) (rect.x + ASTEROID_FILDMatchLoc.x + 5);
                    int Y = (int) (rect.y + ASTEROID_FILDMatchLoc.y + 5);
                    MouseMotionFactory factory = new MouseMotionFactory();
                    factory.move(X,Y);
                    Thread.sleep(1000);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(1000);

                    robot.keyPress(KeyEvent.VK_S);
                    robot.keyRelease(KeyEvent.VK_S);
                    Thread.sleep(30000);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if(laser == 1 && NO_OBJECT_SELECTEDmmr.maxVal > 0.900855073928833){laser = 0;}
            System.out.println(ASTEROID_VELDSPARmmr.maxVal + "  ASTEROID_VELDSPAR");
            if (ASTEROID_VELDSPARmmr.maxVal > 0.900855073928833 && FULLmmr.maxVal < 0.950855073928833 && laser < 1) { // значение mmr.maxVal показывает уровень сходства
                Imgproc.rectangle(MatImg, ASTEROID_VELDSPARMatchLoc, new Point(ASTEROID_VELDSPARMatchLoc.x + ASTEROID_VELDSPAR.cols(), ASTEROID_VELDSPARMatchLoc.y + ASTEROID_VELDSPAR.rows()), new Scalar(0, 0, 255));
                try {
                    for (DesktopWindow desktopWindow : WindowUtils.getAllWindows(true)) {
                        if (desktopWindow.getTitle().contains(WindowName)) {
                            rect = desktopWindow.getLocAndSize();
                        }
                    }
                    Robot robot = new Robot();
                    int X = (int) (rect.x + ASTEROID_VELDSPARMatchLoc.x + 5);
                    int Y = (int) (rect.y + ASTEROID_VELDSPARMatchLoc.y + 5);
                    MouseMotionFactory factory = new MouseMotionFactory();
                    Thread.sleep(20000);
                    factory.move(X,Y);
                    Thread.sleep(1000);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(10);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                    Thread.sleep(1000);


                    Thread.sleep(62000);

                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyRelease(KeyEvent.VK_CONTROL);

                    robot.keyPress(KeyEvent.VK_F1);
                    robot.keyRelease(KeyEvent.VK_F1);
                    Thread.sleep(300);
                    robot.keyPress(KeyEvent.VK_F2);
                    robot.keyRelease(KeyEvent.VK_F2);

                    laser = 1;

//                    Thread.sleep(276000);//at 4000, it will finish in 276 seconds
//                    Thread.sleep(1396000);//at 0, it will finish in 1396 seconds


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (STATIONmmr.maxVal > 0.900855073928833) { // значение mmr.maxVal показывает уровень сходства
                Imgproc.rectangle(MatImg, STATIONMatchLoc, new Point(STATIONMatchLoc.x + STATION.cols(), STATIONMatchLoc.y + STATION.rows()), new Scalar(118, 118, 118));
                try {
                    for (DesktopWindow desktopWindow : WindowUtils.getAllWindows(true)) {
                        if (desktopWindow.getTitle().contains(WindowName)) {
                            rect = desktopWindow.getLocAndSize();
                        }
                    }
                    Robot robot = new Robot();
                    int X = (int) (rect.x + STATIONMatchLoc.x + 7);
                    int Y = (int) (rect.y + STATIONMatchLoc.y + 7);
                    MouseMotionFactory factory = new MouseMotionFactory();
                    factory.move(X,Y);
                    Thread.sleep(1000);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(1000);

                    robot.keyPress(KeyEvent.VK_D);
                    robot.keyRelease(KeyEvent.VK_D);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println(FULL2mmr.maxVal + " FULL");
            if (OVERVIEW_GENERALmmr.maxVal > 0.940855073928833 && FULLmmr.maxVal > 0.950855073928833 || FULL2mmr.maxVal > 0.940855073928833) { // значение mmr.maxVal показывает уровень сходства
                Imgproc.rectangle(MatImg, OVERVIEW_GENERALMatchLoc, new Point(OVERVIEW_GENERALMatchLoc.x + OVERVIEW_GENERAL.cols(), OVERVIEW_GENERALMatchLoc.y + OVERVIEW_GENERAL.rows()), new Scalar(255, 0, 255));
                try {
                    for (DesktopWindow desktopWindow : WindowUtils.getAllWindows(true)) {
                        if (desktopWindow.getTitle().contains(WindowName)) {
                            rect = desktopWindow.getLocAndSize();
                        }
                    }
                    Robot robot = new Robot();
                    int X = (int) (rect.x + OVERVIEW_GENERALMatchLoc.x + 7);
                    int Y = (int) (rect.y + OVERVIEW_GENERALMatchLoc.y + 7);
                    MouseMotionFactory factory = new MouseMotionFactory();
                    factory.move(X,Y);
                    Thread.sleep(1000);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(1000);



                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            try {

                count++;
                System.out.println("CYCLE IS OVER " + count);

            }catch (Exception ex) {
                ex.printStackTrace();
            }

            ///////////////////////////////////////////////////////////////////
            MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".png", MatImg, buf);
            ImageIcon ic = new ImageIcon(buf.toArray());
            screen.setIcon(ic);


            window.revalidate();
            window.repaint();
            window.pack();

        }

        // listAllWindows();
    }

    private static void listAllWindows() throws AWTException, IOException {
        final List<WindowInfo> inflList = new ArrayList<WindowInfo>();
        final List<Integer> order = new ArrayList<Integer>();
        int top = User32.instance.GetTopWindow(0);
        while (top != 0) {
            order.add(top);
            top = User32.instance.GetWindow(top, User32.GW_HWNDNEXT);
        }

        User32.instance.EnumWindows(new WndEnumProc() {
            public boolean callback(int hWnd, int lParam) {
                WindowInfo info = getWindowInfo(hWnd);
                inflList.add(info);
                return true;
            }

        }, 0);
        Collections.sort(inflList, new Comparator<WindowInfo>() {
            public int compare(WindowInfo o1, WindowInfo o2) {
                return order.indexOf(o1.hwnd) - order.indexOf(o2.hwnd);
            }
        });
        for (WindowInfo w : inflList) {
            System.out.println(w);
        }
    }

    public static  WindowInfo getWindowInfo(int hWnd) {
        RECT r = new RECT();
        User32.instance.GetWindowRect(hWnd, r);
        byte[] buffer = new byte[1024];
        User32.instance.GetWindowTextA(hWnd, buffer, buffer.length);
        String title = Native.toString(buffer);
        WindowInfo info = new WindowInfo(hWnd, r, title);
        return info;
    }

    public static interface WndEnumProc extends StdCallLibrary.StdCallCallback {
        boolean callback(int hWnd, int lParam);
    }

    public static interface User32 extends StdCallLibrary {
        public static final String SHELL_TRAY_WND = "Shell_TrayWnd";
        public static final int WM_COMMAND = 0x111;
        public static final int MIN_ALL = 0x1a3;
        public static final int MIN_ALL_UNDO = 0x1a0;

        final User32 instance = (User32) Native.loadLibrary("user32", User32.class);

        boolean EnumWindows(WndEnumProc wndenumproc, int lParam);

        boolean IsWindowVisible(int hWnd);

        int GetWindowRect(int hWnd, RECT r);

        void GetWindowTextA(int hWnd, byte[] buffer, int buflen);

        int GetTopWindow(int hWnd);

        int GetWindow(int hWnd, int flag);

        boolean ShowWindow(int hWnd);

        boolean BringWindowToTop(int hWnd);

        int GetActiveWindow();

        boolean SetForegroundWindow(int hWnd);

        int FindWindowA(String winClass, String title);

        long SendMessageA(int hWnd, int msg, int num1, int num2);
        final int GW_HWNDNEXT = 2;
    }

    public static class RECT extends Structure {
        public int left, top, right, bottom;

        @Override
        protected List<String> getFieldOrder() {
            List<String> order = new ArrayList<>();
            order.add("left");
            order.add("top");
            order.add("right");
            order.add("bottom");
            return order;
        }
    }

    public static class WindowInfo {
        int hwnd;
        RECT rect;
        String title;

        public WindowInfo(int hwnd, RECT rect, String title) {
            this.hwnd = hwnd;
            this.rect = rect;
            this.title = title;
        }

        public String toString() {
            return String.format("(%d,%d)-(%d,%d) : \"%s\"", rect.left, rect.top, rect.right, rect.bottom, title);
        }
    }
}
