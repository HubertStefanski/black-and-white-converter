package converter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import converter.BlackAndWhiteImageFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/*
 * @author Hubert Stefanski
 * main controller for the application
 */
public class MainMenuController {
	@FXML
	Button convertToBlackAndWhiteButton;
	@FXML
	Menu systemMenu, fileMenu;
	@FXML
	MenuItem exitSystemMenuItem, openFileChooserMenuItem;
	@FXML
	ImageView classicImageView, blackAndWhiteImageView;
	@FXML
	Slider setThresholdSlider;
	@FXML
	Text thresholdLevelText, imageWidthText, imageHeightText;

	File selectedFile;
	Image image;
	static BufferedImage bufferedImage;
	public int threshold = 127;

	@FXML
	public void initialize() {
		System.out.println("init");
		setThresholdSlider.setValue(threshold);
		setThresholdSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (image != null) {
					threshold = newValue.intValue();
					thresholdLevelText.setText(Integer.toString(threshold));
					processAndSetImageToBlackAndWhite();

				}
			}
		});
	}

	@FXML
	public void exitSystem(ActionEvent e) {
		System.out.println("TBI exitSystem");
	}

	@FXML
	public void openFileChooser(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			try {
				bufferedImage = ImageIO.read(selectedFile);
				image = SwingFXUtils.toFXImage(bufferedImage, null);

				double imageWidthDouble = image.getWidth();
				double imageHeigthDouble = image.getHeight();
				String imageWidthString = Double.toString(imageHeigthDouble);
				String imageHeightString = Double.toString(imageWidthDouble);

				classicImageView.setImage(image);

				imageWidthText.setText(imageWidthString);
				imageHeightText.setText(imageHeightString);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else {
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("-----------------------------INVALID FILE INPUT----------------------------");
			System.out.println("---------------------------------------------------------------------------");

		}
	}

	@FXML
	public void convertToBlackAndWhite(ActionEvent e) {
		processAndSetImageToBlackAndWhite();
	}

	public void processAndSetImageToBlackAndWhite() {
		System.out.println(">Image sent to conversion");
		Image processedBWImage = BlackAndWhiteImageFactory.processToBlackAndWhite(image, threshold);
		System.out.println(">Image converted");
		System.out.println(">Setting converted image to image view");
		blackAndWhiteImageView.setFitHeight(300);
		blackAndWhiteImageView.setFitWidth(300);
		blackAndWhiteImageView.setImage(processedBWImage);
		System.out.println(">converted Image set to image view");

	}

	@FXML
	public void exportToFile(ActionEvent e) throws FileNotFoundException {
		try {
			FileOutputStream fout = new FileOutputStream(new File("OutputImage.png"));
			fout.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
