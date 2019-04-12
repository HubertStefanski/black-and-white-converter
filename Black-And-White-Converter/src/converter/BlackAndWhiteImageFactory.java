package converter;

import java.awt.image.BufferedImage;

import converter.MainMenuController;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class BlackAndWhiteImageFactory {
	static int bwPixel = 0;
	static WritableImage bwImage;
	static BufferedImage bufferedBwImage;

	static Image processToBlackAndWhite(Image image, int threshold) {
		Image localImage;
		localImage = SwingFXUtils.toFXImage(MainMenuController.bufferedImage, null);
		System.out.println(">image reading to pixel reader");
		PixelReader pixelReader = image.getPixelReader();

		System.out.println(">image reading COMPLETED");

		int imageHeightInt = (int) localImage.getHeight();
		int imageWidthInt = (int) localImage.getWidth();

		bwImage = new WritableImage(imageWidthInt, imageHeightInt);

		@SuppressWarnings("unused")
		PixelWriter pixelWriter = bwImage.getPixelWriter();
		for (int x = 0; x < imageWidthInt; x++) {
			for (int y = 0; y < imageHeightInt; y++) {
				int pixel = pixelReader.getArgb(x, y);

				int red = ((pixel >> 16) & 0xff);
				int green = ((pixel >> 8) & 0xff);
				int blue = (pixel & 0xff);

				int newPixel = (red + green + blue) / 3;

				int black = 0xff000000;
				int white = 0xffffffff;

				if (newPixel < threshold) {
					bwPixel = black;
				} else {
					bwPixel = white;
				}
				bwImage.getPixelWriter().setArgb(x, y, bwPixel);

			}
			bufferedBwImage = SwingFXUtils.fromFXImage(bwImage, null);
		}
		return bwImage;

	}

}
