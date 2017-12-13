package org.deeplearning4j.examples.dataexamples;

import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.records.listener.impl.LogRecordListener;
import org.datavec.api.split.FileSplit;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static org.deeplearning4j.nn.conf.layers.DenseLayer.*;

public class ModelLoadFirstTest {
    private static Logger log = LoggerFactory.getLogger((ModelLoadFirstTest.class));

    public static void main(String[] args) throws IOException {
        int height = 28;        //img height
        int width = 28;         //img width
        int channels = 1;       //greyscale
        int rngseed =123;
        Random randNumGen = new Random(rngseed);
        int batchSize = 128;
        String location = "firsttest.zip";
        int outputNum = 43;     //possible labels


        // file paths
        File trainData = new File ("C:\\Users\\Gamecube\\Desktop\\AIproject\\DataSet\\Training");
        File testData = new File ("C:\\Users\\Gamecube\\Desktop\\AIproject\\DataSet\\Testing");


        FileSplit train = new FileSplit(trainData, NativeImageLoader.ALLOWED_FORMATS, randNumGen);
        FileSplit test = new FileSplit(testData, NativeImageLoader.ALLOWED_FORMATS, randNumGen);

        // Parent path is the label
        ParentPathLabelGenerator labelMaker = new ParentPathLabelGenerator();

        // will conver img to height,width and greyscale if not already there
        ImageRecordReader recordReader = new ImageRecordReader(height, width, channels, labelMaker);

        //Initialize record reader and listen (must throw or try/catch exception
        recordReader.initialize(train);
        //recordReader.setListeners(new LogRecordListener());

        // dataset iterator
        DataSetIterator dataIter = new RecordReaderDataSetIterator(recordReader, batchSize, 1, outputNum);

        // scale pixel values to between 0..1


        DataNormalization scaler = new ImagePreProcessingScaler(0,1);
        scaler.fit(dataIter);
        dataIter.setPreProcessor(scaler);

        log.info("******LOAD TRAINED MODEL*******");

        File locationToLoad = new File(location);

        //use the model built
        MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(locationToLoad);

        log.info("******Evaluate Model******");

        recordReader.reset();

        recordReader.initialize(test);
        // pass to network to validate
        DataSetIterator testIter = new RecordReaderDataSetIterator(recordReader, batchSize, 1, outputNum);
        scaler.fit(testIter);
        testIter.setPreProcessor(scaler);


        // create eval object
        Evaluation eval = new Evaluation(outputNum);

        while(testIter.hasNext()) {
            DataSet next = testIter.next();
            //compares model prediction to label for data
            INDArray output = model.output(next.getFeatures());
            eval.eval(next.getLabels(), output);
        }
        log.info(eval.stats());

    }
}
