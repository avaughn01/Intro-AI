package org.deeplearning4j.examples.dataexamples;

import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestWildImage {

    private static Logger log = LoggerFactory.getLogger(TestWildImage.class);

    // launch pop-up window for user to choose file
    public static String fileChoose() {
        JFileChooser fc = new JFileChooser();
        int ret = fc.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String filename = file.getAbsolutePath();
            return filename;
        }
        else {
            return null;
        }
    }

    public static void main (String[] args) throws IOException {
        int height = 52;        //img height
        int width = 51;         //img width
        int channels = 1;       //greyscale

        //labels
        List<Integer> labelList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42);

        String filechooser = fileChoose().toString();

        // load model
        String location = "modelFinal.zip";
        File locationToLoad = new File(location);
        MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(locationToLoad);

        log.info("*********TEST YOUR IMAGE**********");

        // need the file
        File file = new File(filechooser);

        // convert user image to your network image standard
        NativeImageLoader loader = new NativeImageLoader(height, width, channels);

        // place image into the INDarray

        INDArray image = loader.asMatrix(file);

        // apply scaler to image
        DataNormalization scaler = new ImagePreProcessingScaler(0,1);
        scaler.transform(image);

        // pass image to neural network

        INDArray output = model.output(image);

        log.info("File Chosen: " + filechooser);
        log.info("Probability for each label: ");
        log.info("List of Labels: ");
        log.info(output.toString());
        //log.info(labelList.toString());
        System.out.print("                        [");
        for (int i = 0; i < labelList.size(); i++) {
            if (labelList.get(i) < 10) {
                System.out.print("000" + labelList.get(i) + ",  ");
            }
            else if(i < labelList.size() - 1) {
                System.out.print("00" + labelList.get(i) + ",  ");
            }
            else {
                System.out.print("00" + labelList.get(i) + "]");
            }
        }
    }
}
