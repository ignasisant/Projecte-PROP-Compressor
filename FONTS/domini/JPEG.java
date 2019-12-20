
/**
 * Class: Color
 * Author: Joaquim Ferrer Sagarra
 */
package domini;

import java.io.*;
import java.util.*;

import domini.Imatge;

abstract class JPEG extends Algorithm {
	protected Vector<Integer> Y; //Aquests vectors són per fer les huffman encodings
	protected Vector<Integer> Cr;
	protected Vector<Integer> Cb;
	protected String path;
	protected Imatge imatge;


	protected static final int[][] QUANTY = new int[][] {
		{ 16, 11, 10, 16, 24, 40, 51, 61 },
		{ 12, 12, 14, 19, 26, 58, 60, 55 },
		{ 14, 13, 16, 24, 40, 57, 69, 56 },
		{ 14, 17, 22, 29, 51, 87, 80, 62 },
		{ 18, 22, 37, 56, 68, 109, 103, 77 },
		{ 24, 35, 55, 64, 81, 104, 113, 92 },
		{ 49, 64, 78, 87, 103, 121, 120, 120, 101 },
		{ 72, 92, 95, 98, 112, 100, 103, 99 }
	};

	protected static final int[][] QUANTC = new int[][] {
		{ 17, 18, 24, 47, 99, 99, 99, 99},
		{ 18, 21, 26, 66, 99, 99, 99, 99},
		{ 24, 26, 56, 99, 99, 99, 99, 99},
		{ 47, 66, 99, 99, 99, 99, 99, 99},
		{ 99, 99, 99, 99, 99, 99, 99, 99},
		{ 99, 99, 99, 99, 99, 99, 99, 99},
		{ 99, 99, 99, 99, 99, 99, 99, 99},
		{ 99, 99, 99, 99, 99, 99, 99, 99}
	};
	//Vectors de matrius
	protected int[][][] DCTilu;
	protected int[][][] DCTcr;
	protected int[][][] DCTcb;
	protected int[][][] DCTiluTrans;
	protected int[][][] DCTcrTrans;
	protected int[][][] DCTcbTrans;

	//funcions d'imatge
//	abstract protected void creaImatge(String path) throws IOException; //O crea la matriu o crea la imatge ppm des de la matriu


	//Funcions de compressió/descompressió
//	abstract public String compress();//Comprimeix o descoprimeix.
//	abstract public String decompress();//Comprimeix o descoprimeix.

	//abstract protected void operaYCbCr(); //De YCbCr a JPEG o viceversa
	abstract protected void transformBlocks(); //crea els blocs de 8x8 i aplica la transformació o desfà la transformació fins a tornar a crear la imatge
	//abstract protected void huffman(); //Fa el huffman encoding o decoding.

	@Override
	public int getId(){
		return 2;
	}

	@Override
	public String getExtension() {
		return "jpeg";
	}


}
