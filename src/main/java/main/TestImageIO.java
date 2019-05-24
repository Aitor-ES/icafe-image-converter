package main;

import com.icafe4j.image.ImageIO;
import com.icafe4j.image.ImageParam;
import com.icafe4j.image.ImageType;
import com.icafe4j.image.options.JPEGOptions;
import com.icafe4j.image.options.PNGOptions;
import com.icafe4j.image.options.TIFFOptions;
import com.icafe4j.image.png.Filter;
import com.icafe4j.image.tiff.TiffFieldEnum.Compression;
import com.icafe4j.image.tiff.TiffFieldEnum.PhotoMetric;
import com.icafe4j.image.writer.ImageWriter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TestImageIO {

	private TestImageIO() {
	}

	private void convertImages(File input, File output) throws Exception {
		if (input.isDirectory()) {
			File[] filesInInput = input.listFiles();

			if (filesInInput != null) {
				for (File f : filesInInput) {
					convertImages(f, output);
				}
			}
		} else {
			// OPTIONAL -> Only convert BMP files
			if (input.getName().split("\\.")[1].equals("bmp")) {
				convertImage(input, output, ImageType.PNG);
			}
		}
	}

	private void convertImage(File input, File output, ImageType imageType) throws Exception {
		long t1 = System.currentTimeMillis();
		BufferedImage img = ImageIO.read(input);
		long t2 = System.currentTimeMillis();
		System.out.println("Decoding time: " + (t2 - t1) + " ms");

		FileOutputStream fo = new FileOutputStream(
				output.getPath() + "/" + input.getName().split("\\.")[0] + "." + imageType.getExtension());
		ImageWriter writer = ImageIO.getWriter(imageType);
		ImageParam.ImageParamBuilder builder = ImageParam.getBuilder();

		switch (imageType) {
			case TIFF:// Set TIFF-specific options
				TIFFOptions tiffOptions = new TIFFOptions();
				tiffOptions.setApplyPredictor(true);
				tiffOptions.setTiffCompression(Compression.JPG);
				tiffOptions.setJPEGQuality(60);
				tiffOptions.setPhotoMetric(PhotoMetric.SEPARATED);
				tiffOptions.setWriteICCProfile(true);
				builder.imageOptions(tiffOptions);
				break;
			case PNG:
				PNGOptions pngOptions = new PNGOptions();
				pngOptions.setApplyAdaptiveFilter(true);
				pngOptions.setCompressionLevel(6);
				pngOptions.setFilterType(Filter.NONE);
				builder.imageOptions(pngOptions);
				break;
			case JPG:
				JPEGOptions jpegOptions = new JPEGOptions();
				jpegOptions.setQuality(60);
				jpegOptions.setColorSpace(JPEGOptions.COLOR_SPACE_YCCK);
				jpegOptions.setWriteICCProfile(true);
				builder.imageOptions(jpegOptions);
				break;
			default:
				break;
		}

		writer.setImageParam(builder.build());

		t1 = System.currentTimeMillis();
		writer.write(img, fo);
		t2 = System.currentTimeMillis();

		fo.close();

		System.out.println(imageType + " writer " + "(encoding time " + (t2 - t1) + "ms)");
	}

	public static void main(String[] args) throws Exception {
		TestImageIO testImageIO = new TestImageIO();

		File input = new File("D:\\Distant Worlds 2");  // Input folder
		File output = new File("D:\\Distant Worlds 2 PNG ICAFE"); // Output folder

		testImageIO.convertImages(input, output);
	}
}