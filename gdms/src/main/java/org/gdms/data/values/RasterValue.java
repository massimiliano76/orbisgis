package org.gdms.data.values;

import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ShortProcessor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.gdms.data.types.Type;
import org.gdms.sql.strategies.IncompatibleTypesException;
import org.grap.io.FileReader;
import org.grap.model.GeoRaster;
import org.grap.model.GeoRasterFactory;
import org.grap.model.RasterMetadata;
import org.orbisgis.utils.ByteUtils;

public class RasterValue extends AbstractValue {

	public static int HEADER_SIZE = 0;

	private GeoRaster geoRaster;

	static {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		RasterMetadata rasterMetadata = new RasterMetadata(0, 0, 10, 10, 2, 2);
		try {
			writeMetadata(dos, rasterMetadata, 0, 1, ImagePlus.COLOR_256);
		} catch (IOException e) {
			throw new RuntimeException("bug! We deal with no file!");
		}

		HEADER_SIZE = bos.toByteArray().length;
	}

	RasterValue(GeoRaster geoRaster) {
		this.geoRaster = geoRaster;
	}

	public String getStringValue(ValueWriter writer) {
		return "Raster";
	}

	public int getType() {
		return Type.RASTER;
	}

	public int doHashCode() {
		return geoRaster.hashCode();
	}

	@Override
	public boolean doEquals(Object obj) {
		if (obj instanceof RasterValue) {
			return geoRaster.equals(((RasterValue) obj).geoRaster);
		} else {
			return false;
		}
	}

	public byte[] getBytes() {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			geoRaster.open();
			// Write metadata
			RasterMetadata metadata = geoRaster.getMetadata();
			int imageType = geoRaster.getType();
			writeMetadata(dos, metadata, geoRaster.getMin(),
					geoRaster.getMax(), imageType);
			// Write data
			switch (imageType) {
			case ImagePlus.COLOR_256:
			case ImagePlus.GRAY8:
				byte[] bytePixels = geoRaster.getBytePixels();
				dos.write(bytePixels);
				break;
			case ImagePlus.GRAY16:
				short[] shortPixels = geoRaster.getShortPixels();
				dos.write(ByteUtils.shortsToBytes(shortPixels));
				break;
			case ImagePlus.GRAY32:
				float[] floatPixels = geoRaster.getFloatPixels();
				dos.write(ByteUtils.floatsToBytes(floatPixels));
				break;
			case ImagePlus.COLOR_RGB:
				int[] intPixels = geoRaster.getIntPixels();
				dos.write(ByteUtils.intsToBytes(intPixels));
				break;
			}

			return bos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block. What happens if the raster
			// doesn't exist
			throw new RuntimeException(e);
		}
	}

	private static void writeMetadata(DataOutputStream dos,
			RasterMetadata metadata, double min, double max, int imageType)
			throws IOException {
		// Write metadata
		dos.writeDouble(metadata.getXulcorner());
		dos.writeDouble(metadata.getYulcorner());
		dos.writeInt(metadata.getNRows());
		dos.writeInt(metadata.getNCols());
		dos.writeFloat(metadata.getPixelSize_X());
		dos.writeFloat(metadata.getPixelSize_Y());
		dos.writeDouble(metadata.getRotation_X());
		dos.writeDouble(metadata.getRotation_Y());
		dos.writeFloat(metadata.getNoDataValue());
		// Write cached values
		dos.writeDouble(min);
		dos.writeDouble(max);
		dos.writeInt(imageType);
	}

	@Override
	public GeoRaster getAsRaster() throws IncompatibleTypesException {
		return geoRaster;
	}

	public static Value readBytes(byte[] buffer) {
		ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
		DataInputStream dis = new DataInputStream(bis);
		try {
			// Read metadata
			RasterMetadata metadata = readMetadata(dis);
			int nRows = metadata.getNRows();
			int nCols = metadata.getNCols();
			// Read cached values
			double min = dis.readDouble();
			double max = dis.readDouble();
			// Read image
			int imageType = dis.readInt();
			GeoRaster gr;
			switch (imageType) {
			case ImagePlus.COLOR_256:
			case ImagePlus.GRAY8:
				byte[] bytePixels = new byte[nRows * nCols];
				dis.read(bytePixels);
				gr = GeoRasterFactory.createGeoRaster(bytePixels, metadata,
						imageType, min, max);
				return new RasterValue(gr);
			case ImagePlus.GRAY16:
				byte[] shortPixels = new byte[2 * nRows * nCols];
				dis.read(shortPixels);
				gr = GeoRasterFactory.createGeoRaster(ByteUtils
						.bytesToShorts(shortPixels), metadata, imageType, min,
						max);
				return new RasterValue(gr);
			case ImagePlus.GRAY32:
				byte[] floatPixels = new byte[4 * nRows * nCols];
				dis.read(floatPixels);
				gr = GeoRasterFactory.createGeoRaster(ByteUtils
						.bytesToFloats(floatPixels), metadata, imageType, min,
						max);
				return new RasterValue(gr);
			case ImagePlus.COLOR_RGB:
				byte[] intPixels = new byte[4 * nRows * nCols];
				dis.read(intPixels);
				gr = GeoRasterFactory.createGeoRaster(ByteUtils
						.bytesToInts(intPixels), metadata, imageType, min, max);
				return new RasterValue(gr);
			default:
				throw new RuntimeException("Unrecognized image type: "
						+ imageType);
			}

		} catch (IOException e) {
			throw new RuntimeException("Bug! We access no file!!", e);
		}
	}

	private static RasterMetadata readMetadata(DataInputStream dis)
			throws IOException {
		double upperLeftX = dis.readDouble();
		double upperLeftY = dis.readDouble();
		int nRows = dis.readInt();
		int nCols = dis.readInt();
		float pixelWidth = dis.readFloat();
		float pixelHeight = dis.readFloat();
		double rotationX = dis.readDouble();
		double rotationY = dis.readDouble();
		float noDataValue = dis.readFloat();
		return new RasterMetadata(upperLeftX, upperLeftY, pixelWidth,
				pixelHeight, nCols, nRows, rotationX, rotationY, noDataValue);
	}

	/**
	 * Builds a lazy RasterValue. The metadata and cached values are recovered
	 * immediately and the pixel data will be read through the specified
	 * {@link ByteProvider}
	 *
	 * @param buffer
	 *            Buffer containing the metadata
	 * @param byteProvider
	 *            Interface to access the pixel information
	 * @return
	 */
	public static Value readBytes(byte[] buffer, ByteProvider byteProvider) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
			DataInputStream dis = new DataInputStream(bis);
			RasterMetadata metadata = readMetadata(dis);
			// read cached data
			double min = dis.readDouble();
			double max = dis.readDouble();
			// Read image type
			int imageType = dis.readInt();
			GeoRaster geoRaster = GeoRasterFactory.createGeoRaster(
					new ByteArrayFileReader(byteProvider, metadata), imageType,
					min, max);
			return ValueFactory.createValue(geoRaster);
		} catch (IOException e) {
			throw new RuntimeException("Bug! We access no file!!", e);
		}
	}

	private static class ByteArrayFileReader implements FileReader {

		private RasterMetadata metadata;
		private ByteProvider byteProvider;

		public ByteArrayFileReader(ByteProvider byteProvider,
				RasterMetadata metadata) {
			this.byteProvider = byteProvider;
			this.metadata = metadata;
		}

		public ImagePlus readImagePlus() throws IOException {
			byte[] bytes = byteProvider.getBytes();
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			DataInputStream dis = new DataInputStream(bis);
			// Read metadata
			RasterMetadata metadata = readMetadata(dis);
			int nRows = metadata.getNRows();
			int nCols = metadata.getNCols();
			// Read cached values
			dis.readDouble();
			dis.readDouble();
			// Read image
			int imageType = dis.readInt();
			switch (imageType) {
			case ImagePlus.COLOR_256:
			case ImagePlus.GRAY8:
				byte[] bytePixels = new byte[nRows * nCols];
				dis.read(bytePixels);
				return new ImagePlus("", new ByteProcessor(nCols, nRows,
						bytePixels, null));
			case ImagePlus.GRAY16:
				byte[] shortBytes = new byte[2 * nRows * nCols];
				dis.read(shortBytes);
				short[] shortPixels = ByteUtils.bytesToShorts(shortBytes);
				return new ImagePlus("", new ShortProcessor(nCols, nRows,
						shortPixels, null));
			case ImagePlus.GRAY32:
				byte[] floatBytes = new byte[4 * nRows * nCols];
				dis.read(floatBytes);
				float[] floatPixels = ByteUtils.bytesToFloats(floatBytes);
				return new ImagePlus("", new FloatProcessor(nCols, nRows,
						floatPixels, null));
			case ImagePlus.COLOR_RGB:
				byte[] intBytes = new byte[4 * nRows * nCols];
				dis.read(intBytes);
				int[] intPixels = ByteUtils.bytesToInts(intBytes);
				return new ImagePlus("", new ColorProcessor(nCols, nRows,
						intPixels));
			default:
				throw new RuntimeException("Unrecognized image type: "
						+ imageType);
			}
		}

		public RasterMetadata readRasterMetadata() throws IOException {
			return metadata;
		}

	}
}
